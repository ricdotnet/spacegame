package Images;

import java.awt.image.BufferedImage;

public class SprideSheet {

    private BufferedImage components;
    private BufferedImage icons;

    public SprideSheet(BufferedImage spriteSheet) {
        this.components = spriteSheet;
        this.icons = spriteSheet;
    }

    public BufferedImage grabImage(int col, int row, int width, int height) {
        return components.getSubimage((col * 32) - 32, (row * 32) - 32, width, height);
    }

    public BufferedImage grabIcon(int col, int row, int width, int height) {
        return icons.getSubimage((col * 16) - 16, (row * 16) - 16, width, height);
    }

}
