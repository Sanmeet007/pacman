package pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

public class Settings {

    public static class Preferences {

        public static Image getPacImage(int width, int height) {
            ImageIcon icon = new ImageIcon("res/favicon.png");
            Image image = icon.getImage().getScaledInstance(width, height, 0);
            return image;
        }

        public static ImageIcon getPacImageIcon(int width, int height) {
            ImageIcon icon = new ImageIcon("res/favicon.png");
            Image image = icon.getImage().getScaledInstance(width, height, 0);
            return new ImageIcon(image);
        }

        public static Font getRobotFontBig() {
            try {
                return Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/VT323-Regular.ttf")).deriveFont(50f);
            } catch (FontFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Font("Robotto", Font.PLAIN, 18);
        }

        public static Font getRobotFont() {
            try {
                return Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/VT323-Regular.ttf")).deriveFont(20f);
            } catch (FontFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Font("Robotto", Font.PLAIN, 15);
        }
    }

    static public class Pallete {
        public static Color baseColor = new Color(0x16171740);
        public static Color wallColor = new Color(0x626297);
        public static Color btnColor = new Color(0x626297);
        public static Color panelColor = Color.BLACK;
        public static Color darkColor = Color.BLACK;
        public static Color accentColor = Color.ORANGE;
        public static Color lightColor = Color.WHITE;

    }
}
