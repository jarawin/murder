package utils;

import java.awt.Font;
import java.io.File;

public class UFont {
    private String baseLocate = "/Users/jarawin/Desktop/Study/java/GAME/murder/src/font/";
    private String fileName = "Mali-Bold";
    private Font font = null;
    private int size;

    public UFont(int size) {
        this.size = size;
        setFont();
    }

    public UFont(int size, String fileName) {
        this.fileName = fileName;
        this.size = size;
        setFont();
    }

    private void setFont() {
        try {
            File file = new File(baseLocate + fileName + ".ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, file).deriveFont((float) size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Font get() {
        return font;
    }
}
