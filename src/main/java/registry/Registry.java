package registry;

import java.io.File;
import javax.swing.ImageIcon;

import engine.DataBase;


public class Registry {
	public static ImageIcon messageIcon;
	
	public static String progName = "AniHealator";
	public static String verse = "0.2.4.6";
	public static String Autor = "KiraLis39, Multiverse_39, 2020";
	
	public static File photoDir = new File(".\\data\\photo");
	
	public static Boolean isAdminModeAllow = false;
	public static Boolean isAdminModeOn = false;
	public static Boolean isResizeAllow = false;
	public static Boolean isRenderOn = false;
	
	public static int frameWidth;
	public static int frameHeight;
	
	public static DataBase db;
}
