package fr.ostix.main.game.bulllets;

import fr.ostix.main.entities.Entity;
import fr.ostix.main.game.level.Level;
import fr.ostix.main.graphics.Renderer;
import fr.ostix.main.graphics.Texture;
import fr.ostix.main.math.Vector2f;

public abstract class Bullet extends Entity
{
    protected int texSize = 8;
    protected int size;
    protected int tex;
    protected Vector2f dir;
    protected float speed;
    protected int damage;
    protected float angle;

    public Bullet(float x, float y, int size, Vector2f dir)
    {
        super(x / 16, y / 16);

        this.size = size;
        this.dir = new Vector2f(dir);
        this.angle = (float) Math.toDegrees(Math.atan2(dir.getY(), dir.getX()));
    }

    @Override
    public abstract void init(Level level);

    @Override
    public abstract void update();


    @Override
    public void render()
    {
        Texture.bullets.bind();
        Renderer.renderBullet(x - size / 2, y - size / 2, texSize, size, tex, angle);
        Texture.bullets.unbind();
    }

    public int getDamage()
    {
        return damage;
    }
}
