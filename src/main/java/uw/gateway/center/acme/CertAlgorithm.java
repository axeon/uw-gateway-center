package uw.gateway.center.acme;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证书算法枚举。
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Schema(title = "证书算法", description = "证书算法")
public enum CertAlgorithm {

    ECC256("ECC256", "ECC256"),
    ECC384("ECC384", "ECC384"),
    ECC512("ECC512", "ECC512"),
    RSA2048("RSA2048", "RSA2048"),
    RSA3072("RSA3072", "RSA3072"),
    RSA4096("RSA4096", "RSA4096"),
    ;

    private final String code;

    private final String label;

    CertAlgorithm(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}
