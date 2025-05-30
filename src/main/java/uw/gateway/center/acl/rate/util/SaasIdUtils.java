package uw.gateway.center.acl.rate.util;


import java.util.ArrayList;

/**
 * 为了解决超大客户单量过大导致SEQ不够用的问题，通过SaasID特殊设计来补充。
 * SaasId通过不同的后缀来表示不同的等级。
 * X    VIP0 7位 899万容量 SEQ7位 月订单量限千万
 * 0    VIP1 6位 89910容量 SEQ8位 月订单量限1亿
 * 00   VIP2 5位 891容量 SEQ9位 月订单量限10亿
 * 000  VIP3 4位 9个容量 SEQ10位 月订单量限100亿。
 * 0000=0 系统自身。
 */
public class SaasIdUtils {

    /**
     * VIP1的ID(89910)列表。
     */
    public static final long[] VIP1IDS = getSaasIds( 1 );

    /**
     * VIP2的ID(891)列表。
     */
    public static final long[] VIP2IDS = getSaasIds( 2 );

    /**
     * VIP3的ID(9)列表。
     */
    public static final long[] VIP3IDS = getSaasIds( 3 );

    /**
     * 获取Vip级别。
     * 级别9 系统自身
     * 级别3 VIP3 KVIP
     * 级别2 VIP2 SVIP
     * 级别1 VIP1 VIP
     * 级别0 VIP0
     *
     * @param saasId
     * @return
     */
    public static int getVipLevel(long saasId) {
        if (saasId == 0) {
            return 9;
        } else if (saasId % 1_000L == 0) {
            return 3;
        } else if (saasId % 100L == 0) {
            return 2;
        } else if (saasId % 10L == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 获取SaasId长度。
     *
     * @param vipLevel
     * @return
     */
    public static int SaasIdLen(int vipLevel) {
        return switch (vipLevel) {
            case 9, 3 -> 4;
            case 2 -> 5;
            case 1 -> 6;
            default -> 7;
        };
    }

    /**
     * 按照vipLevel列出所有的SaasIds。
     *
     * @param vipLevel
     * @return
     */
    public static long[] getSaasIds(int vipLevel) {
        switch (vipLevel) {
            case 9:
                return new long[]{0};
            case 3:
                return new long[]{1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000};
            case 2:
                ArrayList<Long> id2s = new ArrayList<>( 10000 );
                for (int i = 10; i <= 999; i++) {
                    long id = i * 100;
                    if (id % 1000 > 0) {
                        id2s.add( id );
                    }
                }
                return id2s.stream().mapToLong( Long::valueOf ).toArray();
            case 1:
                ArrayList<Long> id1s = new ArrayList<>( 100000 );
                for (int i = 100; i <= 99999; i++) {
                    long id = i * 10;
                    if (id % 100 > 0) {
                        id1s.add( id );
                    }
                }
                return id1s.stream().mapToLong( Long::valueOf ).toArray();
            default:
                return new long[0];
        }
    }

}

