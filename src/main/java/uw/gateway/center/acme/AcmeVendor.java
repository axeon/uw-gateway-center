package uw.gateway.center.acme;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.util.EnumUtils;

/**
 * ACME供应商枚举。
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Schema(title = "acme供应商", description = "acme供应商")
public enum AcmeVendor {

    DEFAULT("手工托管", ""),
    ZERO_SSL("ZeroSSL.com", "https://acme.zerossl.com/v2/DV90"),
    LETS_ENCRYPT("Let's Encrypt", "https://acme-v02.api.letsencrypt.org/directory"),
    SSL_COM("SSL.com", "https://acme.ssl.com/sslcom-dv-ecc"),
    GOOGLE("Google", "https://dv.acme-v02.api.pki.goog/directory"),
    BUY_PASS("BuyPass", "https://api.buypass.com/acme/directory");

    private final String code;

    private final String label;

    private final String uri;

    AcmeVendor(String label, String uri) {
        this.code = EnumUtils.enumNameToDotCase(name());
        this.label = label;
        this.uri = uri;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public String getUri() {
        return uri;
    }

    public static AcmeVendor valueOfCode(String code) {
        for (AcmeVendor vendor : values()) {
            if (vendor.code.equals(code)) {
                return vendor;
            }
        }
        throw new IllegalArgumentException("code: " + code);
    }
}
