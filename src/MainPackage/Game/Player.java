package MainPackage.Game;

import MainPackage.Display;
import MainPackage.Graphics.Sprite;
import MainPackage.Graphics.SpriteSheet;
import MainPackage.Graphics.TextureAtlas;
import MainPackage.IO.Input;
import MainPackage.Levels.Level;
import MainPackage.Physic.ShapeWall;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static MainPackage.Game.Camera.*;

public class Player extends Entity{
    public static final int SPRITE_SCALE = 16;
    public static final int SPRITES_PER_HEADING = 1;
    public static float SPRITE_FINISH_SCALE = 0;

    private boolean W = false;
    private boolean D = false;
    private boolean S = false;
    private boolean A = false;

    private int n = 0;
    private int e = 0;
    private int s = 0;
    private int w = 0;

    private float camera_new_x = camX;
    private float camera_new_y = camY;

    private float camera_x = camX;
    private float camera_y = camY;

    private float difference_x;
    private float difference_y;

    private boolean firstTime = true;

    private Graphics2D graphics;

    private enum Heading {
        NORTH(1 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        NORTH1(2 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),

        EAST(1 * SPRITE_SCALE, 3 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST1(2 * SPRITE_SCALE, 3 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),

        SOUTH(1 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH1(2 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),

        WEST(1 * SPRITE_SCALE, 2 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST1(2 * SPRITE_SCALE, 2 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),

        NORTH_DEFAULT(0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST_DEFAULT(0 * SPRITE_SCALE, 3 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH_DEFAULT(0 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST_DEFAULT(0 * SPRITE_SCALE, 2 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE);


        private int x, y, h, w;

        Heading(int x, int y, int h, int w) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        protected BufferedImage texture(TextureAtlas atlas) {
            return atlas.cut(x, y, w, h);
        }
    }
    private Heading					heading;
    private Map<Heading, Sprite> spriteMap;
    private float					scale;
    private float					speed;

    protected Player(float x, float y, float scale, float speed, TextureAtlas atlas) {
        super(EntityType.player, x, y);
        heading = Heading.NORTH;
        spriteMap = new HashMap<Heading, Sprite>();
        this.scale = scale;
        this.speed = speed;
        graphics = Display.getGraphics();
        SPRITE_FINISH_SCALE = scale * SPRITE_SCALE;
        for (Heading h : Heading.values()) {
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), SPRITES_PER_HEADING, SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet, scale);
            spriteMap.put(h, sprite);
        }
    }

    protected Player(float x, float y) {
        super(EntityType.player, x, y);
    }

    @Override
    protected void render(Graphics2D g) {
        spriteMap.get(heading).render(g, x, y);
    }

    @Override
    protected void update(Input input) {

        float newX = x;
        float newY = y;


        Camera.cameraUpdater(newX, newY);

        camera_x = camX;
        camera_y = camY;

        if (input.getKey(KeyEvent.VK_W)) {
            W = true;
            A = false;
            S = false;
            D = false;
            n++;
            if (n > 29) {
                n = 0;
            }
            if (n <= 15) {
                newY -= speed;
                heading = Heading.NORTH;
            }
            if (n > 15) {
                newY -= speed;
                heading = Heading.NORTH1;
            }
        } else if (input.getKey(KeyEvent.VK_D)) {
            W = false;
            A = false;
            S = false;
            D = true;
            e++;
            if (e > 29) {
                e = 0;
            }
            if (e <= 15) {
                newX += speed;
                heading = Heading.EAST;
            }
            if (e > 15) {
                newX += speed;
                heading = Heading.EAST1;
            }
        } else if (input.getKey(KeyEvent.VK_S)) {
            W = false;
            A = false;
            S = true;
            D = false;
            s++;
            if (s > 29) {
                s = 0;
            }
            if (s <= 15) {
                newY += speed;
                heading = Heading.SOUTH;
            }
            if (s > 15) {
                newY += speed;
                heading = Heading.SOUTH1;
            }
        } else if (input.getKey(KeyEvent.VK_A)) {
            W = false;
            A = true;
            S = false;
            D = false;
            w++;
            if (w > 29) {
                w = 0;
            }
            if (w <= 15) {
                newX -= speed;
                heading = Heading.WEST;
            }
            if (w > 15) {
                newX -= speed;
                heading = Heading.WEST1;
            }
        }

        if (W && !input.getKey(KeyEvent.VK_W)) {
            heading = Heading.NORTH_DEFAULT;
        } else if (D && !input.getKey(KeyEvent.VK_D)) {
            heading = Heading.EAST_DEFAULT;
        } else if (S && !input.getKey(KeyEvent.VK_S)) {
            heading = Heading.SOUTH_DEFAULT;
        } else if (A && !input.getKey(KeyEvent.VK_A)) {
            heading = Heading.WEST_DEFAULT;
        }


        if (newX < 0) {
            newX = 0;
        }
        if (newY < 0) {
            newY = 0;
        }
        if (newX > WORLD_SIZE_X - SPRITE_SCALE) {
            newX = WORLD_SIZE_X - SPRITE_SCALE;
        }
        if (newY > WORLD_SIZE_Y - SPRITE_SCALE) {
            newY = WORLD_SIZE_Y - SPRITE_SCALE;
        }


        if (firstTime) {
            graphics.translate(-x + CAMERA_SIZE_X / 2 - 16, -y + CAMERA_SIZE_Y / 2 - 16);
            firstTime = false;
        }
        x = newX;
        y = newY;

        for (int i = 0; i < Level.wallX.size(); i++){
                Rectangle wall = new Rectangle(Level.wallX.get(i), Level.wallY.get(i), Level.FINISH_TILE_SIZE, Level.FINISH_TILE_SIZE);
                Rectangle player = new Rectangle((int)x,(int) y, (int) SPRITE_FINISH_SCALE, (int) SPRITE_FINISH_SCALE);
                if (player.intersects(wall)){
                    if (W){
                        newY += speed;
                    } else if (D){
                        newX -= speed;
                    } else if (S) {
                        newY -= speed;
                    } else if (A) {
                        newX += speed;
                }
            }
        }
        x = newX;
        y = newY;

        Camera.cameraUpdater(x, y);

        camera_new_x = camX;
        camera_new_y = camY;

        difference_x = camera_x - camera_new_x;
        difference_y = camera_y - camera_new_y;

        graphics.translate(difference_x, difference_y);
    }


    public float getPlayerX(){
        this.x = x;
        return x;
    }
    public float getPlayerY() {
        this.y = y;
        return y;
    }
    protected float playerReturn(float x, float y){
        this.x = x;
        this.y = y;
        return x;
    }
}
