package ru.config;

import javax.swing.ImageIcon;
import java.io.File;

public final class Registry {
    public static ImageIcon messageIcon;
    public static File photoDir = new File(".\\data\\photo");

    public static Boolean isAdminModeAllow = false;
    public static Boolean isAdminModeOn = false;
    public static Boolean isResizeAllow = false;
    public static Boolean isRenderOn = false;

    public static int frameWidth = 1440;
    public static int frameHeight = 900;
}
