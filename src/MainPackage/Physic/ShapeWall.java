package MainPackage.Physic;

import MainPackage.Game.Game;
import MainPackage.Game.Player;
import MainPackage.Levels.Level;

public class ShapeWall {
    private final int WALL_SHAPE = Level.FINISH_TILE_SIZE;
    private final float PLAYER_SHAPE = Player.SPRITE_FINISH_SCALE;
    private final int WALL_CENTRE = WALL_SHAPE / 2;
    private final float PLAYER_CENTRE = PLAYER_SHAPE / 2;
    private float sum = WALL_CENTRE + PLAYER_CENTRE;

    public void checkOffSet(float PlayerX, float PlayerY){
            for (int j = 0; j < Level.wallY.size(); j++) {
                if (Level.wallX.get(j) + WALL_CENTRE - PlayerX + PLAYER_CENTRE < sum){
                    PlayerX -= Game.speed;
                    System.out.println(Level.wallX.get(j) + WALL_CENTRE - PlayerX + PLAYER_CENTRE +  "<" +  sum + " 1");
                }
                if (Level.wallX.get(j) + WALL_CENTRE - PlayerX + PLAYER_CENTRE > -sum){
                    PlayerX += Game.speed;
                    System.out.println(Level.wallX.get(j) + WALL_CENTRE - PlayerX + PLAYER_CENTRE  + ">" + -sum + " 2");
                }
                if (Level.wallY.get(j) + WALL_CENTRE - PlayerY + PLAYER_CENTRE < sum){
                    PlayerY -= Game.speed;
                    System.out.println(Level.wallY.get(j) + WALL_CENTRE - PlayerY + PLAYER_CENTRE + "<" + sum + " 3");
                }
                if (Level.wallX.get(j) + WALL_CENTRE - PlayerY + PLAYER_CENTRE > -sum){
                    PlayerY += Game.speed;
                    System.out.println(Level.wallX.get(j) + WALL_CENTRE - PlayerY + PLAYER_CENTRE  + ">" +  -sum + " 4");
                }
                System.out.println(PlayerX + " and " + PlayerY);
            }
    }
}
