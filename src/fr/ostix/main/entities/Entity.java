package fr.ostix.main.entities;

import fr.ostix.main.entities.living.EntityLiving;
import fr.ostix.main.game.level.Level;
import fr.ostix.main.graphics.Texture;
import fr.ostix.main.math.Vector2f;

public abstract class Entity
{

    protected float x, y;
    protected boolean removed = false;
    protected Texture texture;
    protected Level level;
    protected float mass;
    protected float drag;

    protected int[] bounds = new int[4];


    protected Vector2f shootPoint;
    protected Vector2f mouseDirection;

    public Entity(float x, float y)
    {
        this.x = x * 16;
        this.y = y * 16;
    }

    public abstract void init(Level level);

    public abstract void update();

    public abstract void render();


    public boolean isSolidTile(float xa, float ya)
    {
        int x0 = (int) (x + xa + 3) / 16;
        int x1 = (int) (x + xa + 13) / 16;
        int y0 = (int) (y + ya + 2) / 16;
        int y1 = (int) (y + ya + 14) / 16;

        return level.getSolidTilesArray(x0, y0) == null &&
                level.getSolidTilesArray(x1, y0) == null &&
                level.getSolidTilesArray(x1, y1) == null &&
                level.getSolidTilesArray(x0, y1) == null;
    }

    public boolean isSolidTileforbullets(float xa, float ya)
    {
        int x0 = (int) (x + xa) / 16;
        int x1 = (int) (x + xa - 2) / 16;
        int y0 = (int) (y + ya + 2) / 16;
        int y1 = (int) (y + ya) / 16;

        return level.getSolidTilesArray(x0, y0) == null &&
                level.getSolidTilesArray(x1, y0) == null &&
                level.getSolidTilesArray(x1, y1) == null &&
                level.getSolidTilesArray(x0, y1) == null;
    }



    public boolean isGrounded()
    {
        return level.getSolidTilesArray((int) (x + 5) / 16, (int) (y + 14.1) / 16) != null;
    }

    public void removed()
    {
        removed = true;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public boolean isRemoved()
    {
        return removed;
    }

    public Texture getTexture()
    {
        return texture;
    }

    public int getBounds(int index)
    {
        return bounds[index];
    }
}
