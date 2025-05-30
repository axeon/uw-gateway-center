package uw.gateway.center.util;


import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 根据UserAgent判断请求设备
 */
public class RequestDeviceUtils {

    // internal helpers
    private static final String[] KNOWN_MOBILE_USER_AGENT_PREFIXES = new String[]{"w3c ", "w3c-", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "htc_", "inno", "ipaq", "ipod", "jigs", "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "lg/u", "maui", "maxo", "midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-", "newt", "noki", "palm", "pana", "pant", "phil", "play", "port", "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem", "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v", "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda ", "xda-"};
    private static final String[] KNOWN_MOBILE_USER_AGENT_KEYWORDS = new String[]{"blackberry", "webos", "ipod", "lge vx", "midp", "maemo", "mmp", "mobile", "netfront", "hiptop", "nintendo DS", "novarra", "openweb", "opera mobi", "opera mini", "palm", "psp", "phone", "smartphone", "symbian", "up.browser", "up.link", "wap", "windows ce"};
    private static final String[] KNOWN_TABLET_USER_AGENT_KEYWORDS = new String[]{"ipad", "playbook", "hp-tablet", "kindle"};
    private static final List<String> mobileUserAgentPrefixes = new ArrayList<String>();
    private static final List<String> mobileUserAgentKeywords = new ArrayList<String>();
    private static final List<String> tabletUserAgentKeywords = new ArrayList<String>();

    static {
        mobileUserAgentPrefixes.addAll(Arrays.asList(KNOWN_MOBILE_USER_AGENT_PREFIXES));
        mobileUserAgentKeywords.addAll(Arrays.asList(KNOWN_MOBILE_USER_AGENT_KEYWORDS));
        tabletUserAgentKeywords.addAll(Arrays.asList(KNOWN_TABLET_USER_AGENT_KEYWORDS));
    }

    /**
     * 判断设备类型
     *
     * @param request
     * @return
     */
    public static DeviceType resolveDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        // UserAgent keyword detection for Mobile devices
        return resolveDevice(userAgent);
    }

    /**
     * 判断设备类型
     *
     * @param userAgent
     * @return
     */
    public static DeviceType resolveDevice(String userAgent) {
        // UserAgent keyword detection for Mobile devices
        if (userAgent != null) {
            // Android special case
            if (userAgent.contains("android")) {
                if (userAgent.contains("mobile")) {
                    return DeviceType.MOBILE_ANDROID;
                } else {
                    return DeviceType.TABLET_ANDROID;
                }
            }
            // Apple special case
            if (userAgent.contains("iphone")) {
                return DeviceType.MOBILE_IOS;
            }
            if (userAgent.contains("ipad") || userAgent.contains("ipod")) {
                return DeviceType.TABLET_IOS;
            }
            for (String keyword : mobileUserAgentKeywords) {
                if (userAgent.contains(keyword)) {
                    return DeviceType.MOBILE;
                }
            }
            for (String keyword : tabletUserAgentKeywords) {
                if (userAgent.startsWith(keyword)) {
                    return DeviceType.TABLET;
                }
            }
        }
        // User-Agent prefix detection
        if (userAgent != null && userAgent.length() >= 4) {
            String prefix = userAgent.substring(0, 4).toLowerCase();
            if (mobileUserAgentPrefixes.contains(prefix)) {
                return DeviceType.MOBILE;
            }
        }
        return DeviceType.NORMAL;
    }


    public enum DeviceType {
        /**
         * Unkown device
         */
        NONE(-1),

        /**
         * Represents a normal device. i.e. a browser on a desktop or laptop computer
         */
        NORMAL(0),

        /**
         * Represents a mobile device, such as an iPhone
         */
        MOBILE(11),

        /**
         * Represents a mobile device, such as an iPhone
         */
        MOBILE_IOS(12),

        /**
         * Represents a mobile device, such as an iPhone
         */
        MOBILE_ANDROID(13),

        /**
         * Represents a mobile app
         */
        MOBILE_APP(14),

        /**
         * Represents a mobile WEIXIN micro-app
         */
        MOBILE_WEIXIN(15),

        /**
         * Represents a tablet device, such as an iPad
         */
        TABLET(21),

        /**
         * Represents a mobile device, such as an iPhone
         */
        TABLET_IOS(22),

        /**
         * Represents a mobile device, such as an iPhone
         */
        TABLET_ANDROID(23);

        private final int type;

        DeviceType(int type) {
            this.type = type;
        }

        public int type() {
            return type;
        }
    }


}
