package Main;

import java.awt.image.BufferedImage;

public class SprideSheet {

    private BufferedImage image;

    public SprideSheet(BufferedImage spriteSheet) {
        this.image = spriteSheet;
    }

    public BufferedImage grabImage(int col, int row, int width, int height) {
        return image.getSubimage((col * 32) - 32, (row * 32) - 32, width, height);
    }

}
