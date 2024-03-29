package MainPackage.Game;

import MainPackage.IO.Input;

import java.awt.*;

public abstract class Entity {
    public final EntityType type;

    protected float x;
    protected float y;

    protected  Entity(EntityType type, float x, float y) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    protected abstract void render(Graphics2D g);

    protected abstract void update(Input input);
}
