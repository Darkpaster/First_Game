package MainPackage.Levels;

import MainPackage.Game.Game;
import MainPackage.Game.Player;
import MainPackage.Graphics.TextureAtlas;
import MainPackage.Utils.Utils;
import jdk.jshell.execution.Util;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Level {

    public static final int SIZE_OF_TILE = 16;
    public static final int SIZE_OF_TILE_IN_GAME = 4;
    public static final int FINISH_TILE_SIZE = SIZE_OF_TILE * SIZE_OF_TILE_IN_GAME;
    public static final int TILES_IN_WIDTH = Game.WIDTH / FINISH_TILE_SIZE;
    public static final int TILES_IN_HEIGHT = Game.HEIGHT / FINISH_TILE_SIZE;

    public boolean firstTime = true;

    private Integer[][] tileMap;
    private Map<TileType, Tiles> tiles;

    public static ArrayList<Integer> wallX = new ArrayList<Integer>();
    public static ArrayList<Integer> wallY = new ArrayList<Integer>();

    public Level(TextureAtlas atlas) {
        tileMap = new Integer[TILES_IN_HEIGHT][TILES_IN_WIDTH];
        tiles = new HashMap<TileType, Tiles>();
        tiles.put(TileType.GRASS1, new Tiles(atlas.cut(0 * SIZE_OF_TILE, 0 * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE),
                SIZE_OF_TILE_IN_GAME, TileType.GRASS1));
        tiles.put(TileType.GRASS2, new Tiles(atlas.cut(1 * SIZE_OF_TILE, 0 * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE),
                SIZE_OF_TILE_IN_GAME, TileType.GRASS2));
        tiles.put(TileType.GRASS3, new Tiles(atlas.cut(2 * SIZE_OF_TILE, 0 * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE),
                SIZE_OF_TILE_IN_GAME, TileType.GRASS3));

        tiles.put(TileType.DIRT1, new Tiles(atlas.cut(0 * SIZE_OF_TILE, 2 * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE),
                SIZE_OF_TILE_IN_GAME, TileType.DIRT1));
        tiles.put(TileType.DIRT2, new Tiles(atlas.cut(1 * SIZE_OF_TILE, 2 * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE),
                SIZE_OF_TILE_IN_GAME, TileType.DIRT2));
        tiles.put(TileType.DIRT3, new Tiles(atlas.cut(2 * SIZE_OF_TILE, 2 * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE),
                SIZE_OF_TILE_IN_GAME, TileType.DIRT3));

        tiles.put(TileType.TREE, new Tiles(atlas.cut(3 * SIZE_OF_TILE, 0 * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE),
                SIZE_OF_TILE_IN_GAME, TileType.TREE));
        tiles.put(TileType.WALL, new Tiles(atlas.cut(3 * SIZE_OF_TILE, 1 * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE),
                SIZE_OF_TILE_IN_GAME, TileType.WALL));


        try {
            tileMap = Utils.levelParser("src/res/lvl_1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // было 13 и 21
    }


    public void update() {

    }

    public void render(Graphics2D g) {
        for(int i = 0; i < tileMap.length; i++) {
            for(int j = 0; j < tileMap[i].length; j++) {
                if (TileType.getTile(tileMap[i][j]) == TileType.WALL && firstTime) {
                    wallX.add(j * FINISH_TILE_SIZE);
                    wallY.add(i * FINISH_TILE_SIZE);
                }
                tiles.get(TileType.getTile(tileMap[i][j])).render(g, j * FINISH_TILE_SIZE, i * FINISH_TILE_SIZE);

            }
        }
        if (firstTime) {
            System.out.println(wallX.size());
            System.out.println(wallY.size());
            firstTime = false;
        }
    }

}
