package uw.gateway.center.acme;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.shredzone.acme4j.*;
import org.shredzone.acme4j.challenge.Dns01Challenge;
import org.shredzone.acme4j.exception.AcmeException;
import org.shredzone.acme4j.util.KeyPairUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uw.common.app.constant.CommonState;
import uw.common.app.vo.JsonConfigBox;
import uw.common.dto.ResponseData;
import uw.common.util.DateUtils;
import uw.common.util.JsonUtils;
import uw.common.util.SystemClock;
import uw.dao.DaoManager;
import uw.gateway.center.acme.deploy.*;
import uw.gateway.center.acme.dns.*;
import uw.gateway.center.entity.*;
import uw.gateway.center.runner.AcmeDeployCertRunner;
import uw.gateway.center.runner.AcmeOrderCertRunner;
import uw.task.TaskData;
import uw.task.TaskFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.Security;
import java.time.Duration;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * acme帮助类。
 */
public class AcmeHelper {

    private static final Logger log = LoggerFactory.getLogger(AcmeHelper.class);

    /**
     * 数据访问对象。
     */
    private static final DaoManager dao = DaoManager.getInstance();

    /**
     * 超时时间。
     */
    private static final Duration TIMEOUT = Duration.ofSeconds(180L);

    /**
     * DNS供应商列表。
     */
    private final static Map<String, DnsVendor> DNS_VENDOR_MAP = new LinkedHashMap<>() {{
        put(DefaultDnsVendor.class.getName(), new DefaultDnsVendor());
        put(AliDnsVendor.class.getName(), new AliDnsVendor());
        put(AwsDnsVendor.class.getName(), new AwsDnsVendor());
        put(TencentDnsVendor.class.getName(), new TencentDnsVendor());
        put(CloudflareDnsVendor.class.getName(), new CloudflareDnsVendor());
    }};

    /**
     * 部署供应商列表。
     */
    private final static Map<String, DeployVendor> DEPLOY_VENDOR_MAP = new LinkedHashMap<>() {{
        put(QiniuCdnVendor.class.getName(), new QiniuCdnVendor());
        put(AliCdnVendor.class.getName(), new AliCdnVendor());
        put(AwsCdnVendor.class.getName(), new AwsCdnVendor());
        put(TencentCdnVendor.class.getName(), new TencentCdnVendor());
        put(CloudflareCdnVendor.class.getName(), new CloudflareCdnVendor());
    }};

    static {
        // 添加BouncyCastle加密算法
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 获取ACME供应商列表。
     *
     * @return
     */
    public static AcmeVendor[] getAcmeVendors() {
        return AcmeVendor.values();
    }

    /**
     * 获取证书算法列表。
     *
     * @return
     */
    public static CertAlgorithm[] getCertAlgorithms() {
        return CertAlgorithm.values();
    }

    /**
     * 获取DNS供应商列表。
     *
     * @return
     */
    public static Map<String, DnsVendor> getDnsVendorMap() {
        return DNS_VENDOR_MAP;
    }

    /**
     * 获取部署供应商列表。
     *
     * @return
     */
    public static Map<String, DeployVendor> getDeployVendorMap() {
        return DEPLOY_VENDOR_MAP;
    }

    /**
     * @param vendorClass
     * @param vendorParam
     * @return
     */
    private static DnsVendor getDnsVendor(String vendorClass, String vendorParam) {
        DnsVendor dnsVendor = DNS_VENDOR_MAP.get(vendorClass);
        return dnsVendor.build(new JsonConfigBox(JsonUtils.parse(vendorParam, new TypeReference<Map<String, String>>() {
        })));
    }

    /**
     * @param vendorClass
     * @param vendorParam
     * @return
     */
    private static DeployVendor getDeployVendor(String vendorClass, String vendorParam) {
        DeployVendor deployVendor = DEPLOY_VENDOR_MAP.get(vendorClass);
        return deployVendor.build(new JsonConfigBox(JsonUtils.parse(vendorParam, new TypeReference<Map<String, String>>() {
        })));
    }

    /**
     * 将PEM格式转换为KeyPair。
     *
     * @param pem
     * @return
     */
    private static KeyPair pemToKeyPair(String pem) {
        StringReader reader = new StringReader(pem);
        try {
            return KeyPairUtils.readKeyPair(reader);
        } catch (IOException ignored) {

        }
        return null;
    }

    /**
     * 将KeyPair转换为PEM格式。
     *
     * @param keyPair
     * @return
     */
    public static String keyPairToPem(KeyPair keyPair) {
        StringWriter sw = new StringWriter(512);
        try {
            KeyPairUtils.writeKeyPair(keyPair, sw);
        } catch (IOException ignored) {
        }
        return sw.toString();
    }

    /**
     * 初始化密钥对。
     *
     * @param algorithm
     * @return
     */
    public static KeyPair initKeyPair(CertAlgorithm algorithm) {
        return switch (algorithm) {
            case ECC256 -> KeyPairUtils.createECKeyPair("secp256r1");
            case ECC384 -> KeyPairUtils.createECKeyPair("secp384r1");
            case ECC512 -> KeyPairUtils.createECKeyPair("secp512r1");
            case RSA4096 -> KeyPairUtils.createKeyPair(4096);
            case RSA3072 -> KeyPairUtils.createKeyPair(3072);
            default -> KeyPairUtils.createKeyPair(2048);
        };
    }

    /**
     * 执行获取证书。
     *
     * @return 整数数据。
     */
    private static ResponseData<Certificate> performFetchCert(MscAcmeDomain acmeDomain, StringLogger slog) {
        // 获取域名密钥对。
        KeyPair domainKeyPair = pemToKeyPair(acmeDomain.getDomainCertKey());
        // 获取账号和域名。
        var accountResponse = dao.load(MscAcmeAccount.class, acmeDomain.getAccountId());
        if (accountResponse.isNotSuccess()) {
            slog.log("获取账号信息失败！" + accountResponse.getMsg());
            return accountResponse.raw();
        }
        // 获取账号。
        var acmeAccount = accountResponse.getData();
        slog.log("获取账号信息成功，ID：" + acmeAccount.getAccountName());
        // 获取密钥对。
        KeyPair accountKeyPair = pemToKeyPair(acmeAccount.getAccountCertKey());
        //手工托管无法自动申请证书！
        if (StringUtils.isBlank(acmeDomain.getAcmeVendor()) || StringUtils.equals(acmeDomain.getAcmeVendor(), AcmeVendor.DEFAULT.getCode())) {
            slog.log("手工托管无法自动申请证书！");
            return ResponseData.errorMsg("手工托管无法自动申请证书！");
        }
        //  获取DNS供应商。
        if (StringUtils.isBlank(acmeDomain.getDnsVendor()) || StringUtils.equals(acmeDomain.getDnsVendor(), DefaultDnsVendor.class.getName())) {
            slog.log("手工托管DNS无法自动申请证书！");
            return ResponseData.errorMsg("手工托管DNS无法自动申请证书！");
        }
        DnsVendor dnsVendor = getDnsVendor(acmeDomain.getDnsVendor(), acmeDomain.getDnsParam());
        if (dnsVendor == null) {
            slog.log("获取DNS供应商失败！");
            return ResponseData.errorMsg("获取DNS供应商失败！");
        }
        slog.log("获取DNS供应商成功，Vendor：" + dnsVendor.vendorName());
        // 获取CAUri。
        String caUri = AcmeVendor.valueOfCode(acmeDomain.getAcmeVendor()).getUri();
        slog.log("获取CA[" + acmeDomain.getAcmeVendor() + "]，Uri:" + caUri);
        //  创建Session。
        Session session = new Session(caUri);
        //  创建AccountBuilder。
        AccountBuilder accountBuilder = new AccountBuilder().agreeToTermsOfService().useKeyPair(accountKeyPair);
        if (acmeAccount.getAccountCertKey() != null) {
            accountBuilder.addEmail(acmeAccount.getAccountName());
        }
        accountBuilder.withKeyIdentifier(acmeAccount.getEabId(), acmeAccount.getEabKey());
        Account account = null;
        try {
            account = accountBuilder.create(session);
        } catch (AcmeException e) {
            slog.log("账号创建失败！" + e.getMessage());
            return ResponseData.errorMsg("账号创建失败！" + e.getMessage());
        }
        slog.log("账号创建成功！");
        Order order = null;
        try {
            order = account.newOrder().domains(acmeDomain.getDomainName()).create();
        } catch (AcmeException e) {
            slog.log("订单创建失败！" + e.getMessage());
            return ResponseData.errorMsg("订单创建失败！" + e.getMessage());
        }
        slog.log("订单创建成功！");
        // Perform all required authorizations
        for (Authorization auth : order.getAuthorizations()) {
            // The authorization is already valid. No need to process a challenge.
            if (auth.getStatus() == Status.VALID) {
                continue;
            }

            // Find the desired challenge and prepare it.
            // Find a single dns-01 challenge
            Dns01Challenge challenge = auth.findChallenge(Dns01Challenge.TYPE).map(Dns01Challenge.class::cast).orElse(null);

            if (challenge == null) {
                slog.log("没有匹配到dns-01 challenge！");
                return ResponseData.errorMsg("没有匹配到dns-01 challenge！");
            }

            // If the challenge is already verified, there's no need to execute it again.
            if (challenge.getStatus() == Status.VALID) {
                continue;
            }

            // 域名
            String domainName = auth.getIdentifier().getDomain();
            // 记录名称
            String recordName = Dns01Challenge.RECORD_NAME_PREFIX;
            // 记录值
            String recordValue = challenge.getDigest();
            // 添加DNS记录
            ResponseData<String> dnsResponse = dnsVendor.addDnsRecord(domainName, "TXT", recordName, recordValue);
            if (!dnsResponse.isSuccess()) {
                slog.log("添加DNS记录失败！" + dnsResponse.getMsg());
                return ResponseData.errorMsg("添加DNS记录失败！" + dnsResponse.getMsg());
            }
            //  添加DNS记录成功后获得记录ID
            String recordId = dnsResponse.getData();
            slog.log("添加DNS记录成功！记录ID：" + recordId);
            // Now trigger the challenge.
            try {
                challenge.trigger();
                Status status = challenge.waitForCompletion(TIMEOUT);
                if (status != Status.VALID) {
                    String reason = challenge.getError().map(Problem::toString).orElse("unknown");
                    slog.log("DNS记录检查失败！" + reason);
                    throw new RuntimeException("DNS记录检查失败！" + reason);
                }
            } catch (Exception e) {
                slog.log("DNS记录检查异常！" + e.getMessage());
                return ResponseData.errorMsg("DNS记录检查异常！" + e.getMessage());
            } finally {
                if (StringUtils.isNotBlank(recordId)) {
                    //  删除DNS记录
                    ResponseData removeDnsResponse = dnsVendor.removeDnsRecord(domainName, recordId);
                    if (!removeDnsResponse.isSuccess()) {
                        slog.log("删除DNS记录失败！" + removeDnsResponse.getMsg());
                    } else {
                        slog.log("删除DNS记录成功！记录ID：" + recordId);
                    }
                }
            }
        }

        // Wait for the order to become READY
        try {
            order.waitUntilReady(TIMEOUT);
        } catch (Exception e) {
            slog.log("订单准备等待异常！" + e.getMessage());
            return ResponseData.errorMsg("订单准备等待异常！" + e.getMessage());
        }

        // Order the certificate
        try {
            order.execute(domainKeyPair);
        } catch (AcmeException e) {
            slog.log("订单执行异常！" + e.getMessage());
            return ResponseData.errorMsg("订单执行异常！" + e.getMessage());
        }
        slog.log("订单执行成功！");

        // Wait for the order to complete
        Status status = null;
        try {
            status = order.waitForCompletion(TIMEOUT);
        } catch (Exception e) {
            return ResponseData.errorMsg("订单完成等待异常！" + e.getMessage());
        }
        if (status != Status.VALID) {
            String reason = order.getError().map(Problem::toString).orElse("unknown");
            slog.log("订单失败！" + reason);
            return ResponseData.errorMsg("订单失败！" + reason);
        }
        slog.log("订单执行完成！");
        // Get the certificate
        Certificate certificate = order.getCertificate();
        return ResponseData.success(certificate);
    }

    /**
     * 申请证书。
     * 异步队列执行。
     *
     * @param domainId
     * @return
     */
    public static ResponseData<?> asyncApplyCert(long domainId) {
        TaskData<Long, ResponseData<MscAcmeCert>> taskData = TaskData.builder(AcmeOrderCertRunner.class, domainId).build();
        TaskFactory.getInstance().sendToQueue(taskData);
        return ResponseData.success();
    }

    /**
     * 获取域名信息。
     *
     * @param domainId
     * @return
     */
    public static ResponseData<MscAcmeDomain> getDomainById(long domainId) {
        return dao.load(MscAcmeDomain.class, domainId);
    }

    /**
     * 判断是否支持ACME订单。
     *
     * @param domainId
     * @return
     */
    public static boolean isSupportAcmeOrder(long domainId) {
        var domainResponse = dao.load(MscAcmeDomain.class, domainId);
        if (domainResponse.isNotSuccess()) {
            return false;
        }
        MscAcmeDomain acmeDomain = domainResponse.getData();
        //手工托管无法自动申请证书！
        if (StringUtils.isBlank(acmeDomain.getAcmeVendor()) || StringUtils.equals(acmeDomain.getAcmeVendor(), AcmeVendor.DEFAULT.getCode())) {
            return false;
        }
        //  获取DNS供应商。
        if (StringUtils.isBlank(acmeDomain.getDnsVendor()) || StringUtils.equals(acmeDomain.getDnsVendor(), DefaultDnsVendor.class.getName())) {
            return false;
        }
        return true;
    }

    /**
     * 获取证书。
     *
     * @param domainId
     * @return
     */
    public static ResponseData<MscAcmeCert> fetchCert(long domainId) {
        StringLogger slog = new StringLogger(2560);
        slog.log("获取证书开始。domainId=" + domainId);
        //  获取域名。
        var domainResponse = dao.load(MscAcmeDomain.class, domainId);
        if (domainResponse.isNotSuccess()) {
            slog.log("获取域名信息失败！" + domainResponse.getMsg());
            return domainResponse.raw();
        }

        // 获取域名。
        MscAcmeDomain acmeDomain = domainResponse.getData();
        slog.log("获取域名[" + acmeDomain.getDomainName() + "]信息成功！");

        //构造返回结果
        MscAcmeCert mscAcmeCert = new MscAcmeCert();
        mscAcmeCert.setId(dao.getSequenceId(MscAcmeCert.class));
        mscAcmeCert.setSaasId(acmeDomain.getSaasId());
        mscAcmeCert.setAccountId(acmeDomain.getAccountId());
        mscAcmeCert.setDomainId(domainId);
        mscAcmeCert.setDomainName(acmeDomain.getDomainName());
        mscAcmeCert.setDomainAlias(acmeDomain.getDomainAlias());
        mscAcmeCert.setCertAlg(acmeDomain.getDomainCertAlg());
        mscAcmeCert.setCertKey(acmeDomain.getDomainCertKey());
        mscAcmeCert.setCreateDate(SystemClock.nowDate());
        mscAcmeCert.setModifyDate(null);
        // 证书写入器
        StringWriter certWriter = new StringWriter();
        // 证书生效时间
        Date activeDate = null;
        // 证书失效时间
        Date expireDate = null;
        // 获取证书。
        ResponseData<Certificate> certResponse = performFetchCert(acmeDomain, slog);
        if (certResponse.isNotSuccess()) {
            mscAcmeCert.setState(CommonState.DISABLED.getValue());
            slog.log("获取域名证书失败！" + certResponse.getMsg());
        } else {
            mscAcmeCert.setState(CommonState.ENABLED.getValue());
            Certificate certificate = certResponse.getData();
            slog.log("获取域名[" + acmeDomain.getDomainName() + "]证书成功！");
            activeDate = certificate.getCertificate().getNotBefore();
            expireDate = certificate.getCertificate().getNotAfter();
            slog.log("证书生效时间：" + DateUtils.dateToString(activeDate, DateUtils.DATE_TIME));
            slog.log("证书失效时间：" + DateUtils.dateToString(expireDate, DateUtils.DATE_TIME));
            // 保存证书。
            try {
                certificate.writeCertificate(certWriter);
            } catch (IOException e) {
                slog.log("证书写入异常！" + e.getMessage());
            }
        }
        mscAcmeCert.setCertData(certWriter.toString());
        mscAcmeCert.setCertLog(slog.toString());
        mscAcmeCert.setActiveDate(activeDate);
        mscAcmeCert.setExpireDate(expireDate);
        var savedResponse = dao.save(mscAcmeCert);
        if (savedResponse.isNotSuccess()) {
            return savedResponse.raw();
        } else {
            if (mscAcmeCert.getState() == CommonState.ENABLED.getValue()) {
                //更新domain信息。
                acmeDomain.setLastUpdate(mscAcmeCert.getCreateDate());
                acmeDomain.setLastActiveDate(activeDate);
                acmeDomain.setLastExpireDate(expireDate);
                dao.update(acmeDomain);
                // 更新当前域名下的其他合法证书状态为禁用。
                dao.executeCommand("update msc_acme_cert set state=? where state=? and domain_id=? and id<?", new Object[]{CommonState.DISABLED.getValue(), CommonState.ENABLED.getValue(), mscAcmeCert.getDomainId(), mscAcmeCert.getId()});
                // 执行域名部署。
                deployDomainCert(domainId, mscAcmeCert.getId());
                return ResponseData.success(mscAcmeCert);
            } else {
                return ResponseData.error(mscAcmeCert);
            }
        }
    }

    /**
     * 获取证书部署ID列表。
     *
     * @param domainId
     * @return
     */
    public static ResponseData<List<Long>> deployDomainCert(long domainId, long certId) {
        return dao.queryForSingleList(Long.class, "select deploy_id from msc_acme_deploy where domain_id=? and state=?", new Object[]{domainId, CommonState.ENABLED.getValue()}).onSuccess(x -> {
            x.forEach(deployId -> {
                asyncDeployCert(deployId, certId);
            });
        });
    }

    /**
     * 执行部署证书。
     *
     * @param mscAcmeDeploy
     * @param mscAcmeCert
     * @param slog
     * @return
     */
    private static ResponseData<String> performDeployCert(MscAcmeDeploy mscAcmeDeploy, MscAcmeCert mscAcmeCert, StringLogger slog) {
        // 获取部署厂商。
        DeployVendor deployVendor = getDeployVendor(mscAcmeDeploy.getDeployVendor(), mscAcmeDeploy.getDeployParam());
        if (deployVendor == null) {
            slog.log("获取部署供应商失败！Vendor：" + mscAcmeDeploy.getDeployVendor());
            return ResponseData.errorMsg("获取部署供应商失败！");
        }
        slog.log("获取部署供应商成功！Vendor：" + deployVendor.vendorName());
        // 添加DNS记录
        ResponseData<String> deployResponse = deployVendor.deployCert(mscAcmeCert.getDomainName(), mscAcmeCert.getCertKey(), mscAcmeCert.getCertData());
        if (deployResponse.isNotSuccess()) {
            slog.log("部署证书失败！" + deployResponse.getMsg());
        } else {
            String recordId = deployResponse.getData();
            slog.log("部署证书成功！记录ID：" + recordId);
        }
        return deployResponse;
    }

    /**
     * 异步部署证书。
     *
     * @param deployId
     * @param certId
     * @return
     */
    public static ResponseData asyncDeployCert(long deployId, long certId) {
        TaskData<AcmeDeployCertRunner.DeployParam, ResponseData<MscAcmeDeployLog>> taskData = TaskData.builder(AcmeDeployCertRunner.class, new AcmeDeployCertRunner.DeployParam(certId, deployId)).build();
        TaskFactory.getInstance().sendToQueue(taskData);
        return ResponseData.success();
    }

    /**
     * 执行部署证书。
     *
     * @param deployId
     * @param certId
     * @return
     */
    public static ResponseData<MscAcmeDeployLog> deployCert(long deployId, long certId) {
        StringLogger slog = new StringLogger(2560);
        slog.log("获取证书开始。certId=" + certId);
        //  获取证书。
        var mscAcmeCertResponse = dao.load(MscAcmeCert.class, certId);
        if (mscAcmeCertResponse.isNotSuccess()) {
            slog.log("获取域名证书失败！" + mscAcmeCertResponse.getMsg());
        }
        MscAcmeCert mscAcmeCert = mscAcmeCertResponse.getData();
        slog.log("获取域名[" + mscAcmeCert.getDomainName() + "]证书信息成功！");
        if (mscAcmeCert.getActiveDate() != null) {
            slog.log("证书开始时间：" + DateUtils.dateToString(mscAcmeCert.getActiveDate(), DateUtils.DATE_TIME) + "\n");
        }
        if (mscAcmeCert.getExpireDate() != null) {
            slog.log("证书过期时间：" + DateUtils.dateToString(mscAcmeCert.getExpireDate(), DateUtils.DATE_TIME) + "\n");
        }
        // 获取部署数据。
        slog.log("获取部署配置开始。deployId=" + certId);
        var mscAcmeDeployResponse = dao.load(MscAcmeDeploy.class, deployId);
        if (mscAcmeDeployResponse.isNotSuccess()) {
            slog.log("获取部署配置失败！" + mscAcmeDeployResponse.getMsg());
        }
        MscAcmeDeploy mscAcmeDeploy = mscAcmeDeployResponse.getData();
        slog.log("获取部署配置[" + mscAcmeDeploy.getDeployName() + "]成功！");
        // 构造返回结果。
        MscAcmeDeployLog mscAcmeDeployLog = new MscAcmeDeployLog();
        mscAcmeDeployLog.setId(dao.getSequenceId(MscAcmeDeployLog.class));
        mscAcmeDeployLog.setSaasId(mscAcmeCert.getSaasId());
        mscAcmeDeployLog.setDomainId(mscAcmeCert.getDomainId());
        mscAcmeDeployLog.setCertId(mscAcmeCert.getId());
        mscAcmeDeployLog.setDeployId(mscAcmeDeploy.getId());
        mscAcmeDeployLog.setState(CommonState.ENABLED.getValue());
        mscAcmeDeployLog.setDeployDate(SystemClock.nowDate());
        //  执行部署。
        ResponseData<String> deployResponse = performDeployCert(mscAcmeDeploy, mscAcmeCert, slog);
        if (deployResponse.isNotSuccess()) {
            mscAcmeDeployLog.setState(CommonState.DISABLED.getValue());
            mscAcmeDeployLog.setDeployInfo("域名[" + mscAcmeCert.getDomainName() + "]证书部署失败！");
        } else {
            mscAcmeDeployLog.setState(CommonState.ENABLED.getValue());
            mscAcmeDeployLog.setDeployInfo("域名[" + mscAcmeCert.getDomainName() + "]证书部署成功！有效期[" + DateUtils.dateToString(mscAcmeCert.getActiveDate(), DateUtils.DATE) + "]-[" + DateUtils.dateToString(mscAcmeCert.getExpireDate(), DateUtils.DATE) + "]");
        }
        mscAcmeDeployLog.setDeployLog(slog.toString());
        var savedResponse = dao.save(mscAcmeDeployLog);
        if (savedResponse.isNotSuccess()) {
            return savedResponse.raw();
        } else {
            if (mscAcmeDeployLog.getState() == CommonState.ENABLED.getValue()) {
                //更新deploy信息。
                mscAcmeDeploy.setLastUpdate(mscAcmeDeployLog.getDeployDate());
                mscAcmeDeploy.setLastActiveDate(mscAcmeCert.getActiveDate());
                mscAcmeDeploy.setLastExpireDate(mscAcmeCert.getExpireDate());
                dao.update(mscAcmeDeploy);
                return ResponseData.success(mscAcmeDeployLog);
            } else {
                return ResponseData.error(mscAcmeDeployLog);
            }
        }
    }

    /**
     * 更新域名证书信息。
     *
     * @param domainId
     * @return
     */
    public static ResponseData<MscAcmeDomain> updateDomainCertInfo(long domainId) {
        Date now = SystemClock.nowDate();
        return getValidCert(domainId, now).onSuccess(mscAcmeCert -> {
            return dao.load(MscAcmeDomain.class, domainId).onSuccess(mscAcmeDomain -> {
                mscAcmeDomain.setLastUpdate(now);
                mscAcmeDomain.setLastActiveDate(mscAcmeCert.getActiveDate());
                mscAcmeDomain.setLastExpireDate(mscAcmeCert.getExpireDate());
                return dao.update(mscAcmeDomain);
            });
        });
    }

    /**
     * 获取当前有效的证书。
     *
     * @param domainId
     * @return
     */
    public static ResponseData<MscAcmeCert> getValidCert(long domainId, Date date) {
        String sql = "SELECT * from msc_acme_cert where domain_id=? and state=? and expire_date>=? and active_date<=? order by id desc";
        return dao.queryForSingleObject(MscAcmeCert.class, sql, new Object[]{domainId, CommonState.ENABLED.getValue(), date, date});
    }


//    public static void main(String[] args) {
//        DaoConfigManager.setConfig(new DaoConfig());
//        ConnectionManager.initConnectionPool("$ROOT_CONN$", "", "com.mysql.cj.jdbc.Driver", "jdbc:mysql://192.168.88.21:3308/uw_gateway?characterEncoding=utf-8&allowPublicKeyRetrieval=true&useSSL=false&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true", "root", "mysqlRootPassword", null, 1, 10, 600, 1800, 3600);
//        System.out.println(fetchCert(0));
//        System.out.println(JsonUtils.toString(getAcmeVendors()));
//        System.out.println(JsonUtils.toString(getDnsVendorMap()));
//        System.out.println(JsonUtils.toString(getDeployVendorMap()));
//        System.out.println(keyPairToPem(AcmeHelper.initKeyPair(CertAlgorithm.ECC256)));
//        System.out.println(keyPairToPem(AcmeHelper.initKeyPair(CertAlgorithm.ECC384)));
//        DnsVendor dnsVendor = getDnsVendor("uw.gateway.center.acme.dns.AliDnsVendor", "{\"api.key\":\"***\",\"api.secret\":\"***\"}");
//        ResponseData<String> result = dnsVendor.addDnsRecord("xili.pub", "TXT", "_acme-challenge.xili.pub", "123456");
//        System.out.println(result);
//        if (result.isSuccess()) {
//            ResponseData removeResult = dnsVendor.removeDnsRecord("xili.pub", result.getData());
//            System.out.println(removeResult);
//        }
//        System.out.println(deployCert(5, 3));
//    }

    /**
     * 日志记录器。
     */
    private static class StringLogger {
        private final StringBuilder log;

        /**
         * 构造函数。
         *
         * @param capacity
         */
        public StringLogger(int capacity) {
            this.log = new StringBuilder(capacity);
        }

        /**
         * 记录日志。
         *
         * @param message
         */
        public void log(String message) {
            log.append(DateUtils.dateToString(SystemClock.nowDate(), DateUtils.DATE_MILLIS));
            log.append("\t");
            log.append(message).append("\n");
        }

        /**
         * 获取日志。
         *
         * @return
         */
        public String toString() {
            return log.toString();
        }

    }


}
