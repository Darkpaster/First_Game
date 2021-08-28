package MainPackage.Game;

import MainPackage.Display;

import java.awt.*;

public class Camera {
    public static final int WORLD_SIZE_X = 10080;
    public static final int WORLD_SIZE_Y = 10080;
    public static final int CAMERA_SIZE_X = 1008;
    public static final int CAMERA_SIZE_Y = 624;
    private static final int offSetMaxX = WORLD_SIZE_X - CAMERA_SIZE_X;
    private static final int offSetMaxY = WORLD_SIZE_Y - CAMERA_SIZE_Y;
    private static final int offSetMinX = 0;
    private static final int offSetMinY = 0;

    public static float camX = Game.x - CAMERA_SIZE_X / 2 + 16;
    public static float camY = Game.y - CAMERA_SIZE_Y / 2 + 16;

    private static Graphics2D graphics = Display.getGraphics();

    public static void cameraUpdater(float PlayerX, float PlayerY) {
        camX = PlayerX - CAMERA_SIZE_X / 2 + 16;
        camY = PlayerY - CAMERA_SIZE_Y / 2 + 16;
        if (camX > offSetMaxX) {
            camX = offSetMaxX;
        }
        if (camX < offSetMinX) {
            camX = offSetMinX;
        }
        if (camY > offSetMaxY) {
            camY = offSetMaxY;
        }
        if (camY < offSetMinY){
            camY = offSetMinY;
        }
    }
}
