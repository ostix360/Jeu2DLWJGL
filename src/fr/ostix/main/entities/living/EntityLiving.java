package fr.ostix.main.entities.living;

import fr.ostix.main.entities.Entity;
import fr.ostix.main.game.bulllets.BasicBullet;
import fr.ostix.main.game.level.Level;

public abstract class EntityLiving extends Entity
{
    protected float health;
    protected int numBullets, timer, timerforBullets;
    protected boolean dead = false;
    protected int size;
    protected float speed;
    int id;

    public EntityLiving(float x, float y, int id)
    {
        super(x, y);
        this.id = id;
    }

    @Override
    public abstract void init(Level level);

    @Override
    public abstract void update();

    @Override
    public abstract void render();

    public boolean isDead()
    {
        return dead;
    }



    Entity backE;

    public void damage(Entity provider, float damage)
    {
        if (isDead())return;

        if (backE != null)
            {
                if (backE == provider && provider instanceof BasicBullet) ((BasicBullet) provider).destroy();
            }
        health -= damage/2;
        if (provider instanceof BasicBullet)
            {
                backE = provider;
                ((BasicBullet) provider).destroy();
            }
    }

    public int getId()
    {
        return id;
    }
}
