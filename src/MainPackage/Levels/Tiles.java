package MainPackage.Levels;

import MainPackage.Graphics.SpriteSheet;
import MainPackage.Utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tiles {
    private BufferedImage image;
    private TileType type;

    protected Tiles(BufferedImage image, int scale, TileType type) {
        this.type = type;
        this.image = Utils.resize(image, image.getWidth() * scale, image.getHeight() * scale);
    }

    protected void render(Graphics2D g, int x, int y) {
        g.drawImage(image, x, y, null);
    }

    protected TileType type() {
        return type;
    }
}
