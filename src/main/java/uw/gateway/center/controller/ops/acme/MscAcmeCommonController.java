package uw.gateway.center.controller.ops.acme;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uw.auth.service.AuthServiceHelper;
import uw.auth.service.annotation.MscPermDeclare;
import uw.auth.service.constant.ActionLog;
import uw.auth.service.constant.AuthType;
import uw.auth.service.constant.UserType;
import uw.common.dto.ResponseData;
import uw.gateway.center.acme.*;
import uw.gateway.center.entity.MscAcmeAccount;

import java.util.Arrays;
import java.util.List;


/**
 * ACME通用功能。
 */
@RestController
@RequestMapping("/ops/acme/common")
@Tag(name = "ACME通用功能", description = "ACME通用功能")
@MscPermDeclare(user = UserType.OPS)
public class MscAcmeCommonController {

    /**
     * 列表acme供应商。
     *
     * @return
     */
    @GetMapping("/listAcmeVendor")
    @Operation(summary = "列表acme供应商", description = "列表acme供应商")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.USER, log = ActionLog.NONE)
    public List<AcmeVendor> listAcmeVendor() {
        return Arrays.stream(AcmeHelper.getAcmeVendors()).toList();
    }


    /**
     * 列表dns供应商。
     *
     * @return
     */
    @GetMapping("/listDnsVendor")
    @Operation(summary = "列表dns供应商", description = "列表dns供应商")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.USER, log = ActionLog.NONE)
    public List<DnsVendor> listDnsVendor() {
        return AcmeHelper.getDnsVendorMap().values().stream().toList();
    }


    /**
     * 列表发布供应商。
     *
     * @return
     */
    @GetMapping("/listDeployVendor")
    @Operation(summary = "列表发布供应商", description = "列表发布供应商")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.USER, log = ActionLog.NONE)
    public List<DeployVendor> listDeployVendor() {
        return AcmeHelper.getDeployVendorMap().values().stream().toList();
    }

    /**
     * 列表证书算法。
     *
     * @return
     */
    @GetMapping("/listCertAlgorithm")
    @Operation(summary = "列表证书算法", description = "列表证书算法")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.USER, log = ActionLog.NONE)
    public List<CertAlgorithm> listCertAlgorithm() {
        return Arrays.stream(AcmeHelper.getCertAlgorithms()).toList();
    }

    /**
     * 生成密钥证书。
     *
     * @param
     * @return
     */
    @GetMapping("/genKeyPem")
    @Operation(summary = "生成密钥证书", description = "生成密钥证书")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.USER, log = ActionLog.REQUEST)
    public ResponseData<String> genKeyPem(String certAlg) {
        AuthServiceHelper.logRef(MscAcmeAccount.class);
        return ResponseData.success(AcmeHelper.keyPairToPem(AcmeHelper.initKeyPair(CertAlgorithm.valueOf(certAlg))));
    }

}