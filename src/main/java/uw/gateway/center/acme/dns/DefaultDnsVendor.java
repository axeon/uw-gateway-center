package uw.gateway.center.acme.dns;

import uw.common.app.vo.JsonConfigBox;
import uw.common.app.vo.JsonConfigParam;
import uw.common.dto.ResponseData;
import uw.gateway.center.acme.DnsVendor;

import java.util.List;

/**
 * 手工托管默认供应商。
 */
public class DefaultDnsVendor implements DnsVendor {

    /**
     * 供应商名称
     */
    @Override
    public String vendorName() {
        return "手工托管";
    }

    /**
     * 供应商描述
     */
    @Override
    public String vendorDesc() {
        return "手工托管";
    }

    /**
     * 供应商版本
     */
    @Override
    public String vendorVersion() {
        return "1.0.0";
    }

    /**
     * 供应商图标
     */
    @Override
    public String vendorIcon() {
        return "";
    }

    /**
     * Vendor参数信息集合，管理员可见。
     */
    @Override
    public List<JsonConfigParam> vendorParam() {
        return List.of();
    }

    /**
     * 初始化API凭证
     *
     * @param config 特定的DNS服务商凭证
     * @return
     */
    @Override
    public DnsVendor build(JsonConfigBox config) {
        return new DefaultDnsVendor();
    }

    @Override
    public ResponseData<String> addDnsRecord(String domain, String recordType, String recordName, String recordValue) {
        return ResponseData.success();
    }

    /**
     * 删除DNS记录
     *
     * @param domain   域名
     * @param recordId 记录ID
     * @return
     */
    @Override
    public ResponseData<String> removeDnsRecord(String domain, String recordId) {
        return ResponseData.success();
    }
}