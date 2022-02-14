package utils;

import javax.swing.ImageIcon;

public class UPicture {
    private String baseLocate = "/Users/jarawin/Desktop/Study/java/GAME/murder/src";
    private ImageIcon image;

    public UPicture(String type, String fileName) {
        image = new ImageIcon(baseLocate + "/" + type + "/" + fileName + ".png");
    }

    public UPicture(String type, int fileName) {
        image = new ImageIcon(baseLocate + "/" + type + "/" + fileName + ".png");
    }

    public UPicture(String type, String folderName, String fileName) {
        image = new ImageIcon(baseLocate + "/" + type + "/" + folderName + "/" + fileName + ".png");
    }

    public UPicture(String type, int folderName, String fileName) {
        image = new ImageIcon(baseLocate + "/" + type + "/" + folderName + "/" + fileName + ".png");
    }

    public ImageIcon get() {
        return image;
    }
}
