package com.karic.sdk;

class Utils {
    static final String TEST_ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713";
    static final String TEST_ADMOB_BANNER_ID = "ca-app-pub-3940256099942544/6300978111";
    static final String TEST_ADMOB_SPLASH_ID = "ca-app-pub-3940256099942544/1033173712";
    static final String TEST_FB_BANNER_ID = "1423813157761799_1423814991094949";
    static final String TEST_FB_SPLASH_ID = "1423813157761799_1423813467761768";

    public static boolean isValidAid(String aid) {
        return aid != null && aid.length() > 8;
    }

    public static String formatAdmobError(int code) {
        switch (code) {
            case 0:
                return "admob: INTERNAL_ERROR";

            case 1:
                return "admob: INVALID_REQUEST";

            case 2:
                return "admob: NETWORK_ERROR";

            case 3:
                return "admob: NO_FILL";

            default:
                return "admob:Unknown";
        }
    }
}
