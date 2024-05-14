package ru.iaygi.ui.data;

public class TestData {
    public static final String selenoid = "http://---------:4444/wd/hub";
    public static boolean enableVNC = setVnc();
    public static boolean enableVideo = setVideo();

    private static boolean setVnc() {
        String enableVnc = System.getProperty("vnc_enabled", "true");
        if (enableVnc.equals("false")) {
            return false;
        }
        return true;
    }

    private static boolean setVideo() {
        String enableVideo = System.getProperty("video_enabled", "false");
        if (enableVideo.equals("true")) {
            return true;
        }
        return false;
    }
}
