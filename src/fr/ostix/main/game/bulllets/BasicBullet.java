package fr.ostix.main.game.bulllets;

import fr.ostix.main.entities.Entity;
import fr.ostix.main.entities.living.EntityLiving;
import fr.ostix.main.entities.particles.Particle;
import fr.ostix.main.entities.particles.ParticleSystem;
import fr.ostix.main.game.level.Level;
import fr.ostix.main.graphics.Color;
import fr.ostix.main.graphics.Texture;
import fr.ostix.main.math.Vector2f;

public class BasicBullet extends Bullet
{

    int id;
    int lifTime;
    private Entity provider;
    private boolean tuchDown = false;

    public BasicBullet(float x, float y, int size, Vector2f dir, int lifTime, Entity provider)
    {
        super(x, y, size, dir);
        this.provider = provider;
        tex = 0;
        this.lifTime = lifTime;
        speed = 0.5f;
        damage = 1;
        mass = 0.001f;
        this.bounds[0] = (int) x;
        this.bounds[1] = (int) y;
        this.bounds[2] = (int) (x + size);
        this.bounds[3] = (int) (y + size);
    }

    float xa, ya;

    @Override
    public void init(Level level)
    {
        this.level = level;
    }

    @Override
    public void update()
    {
        xa += dir.getX() * speed;
        ya += dir.getY() * speed;
        int xStep = (int) Math.abs(xa * 1000);
        for (int i = 0; i < xStep; i++)
            {


                tuchDown = level.isSolidEntityLivingforbullets(x,y,xa, 0,provider,this);//&& provider != Level.getPlayer();
                id = 0;

                if (isSolidTileforbullets(xa / xStep, 0))
                    {
                        x += xa / xStep;
                    } else
                    {
                        xa = 0;
                        destroy();
                    }
            }
        int yStep = (int) Math.abs(ya * 1000);
        for (int i = 0; i < yStep; i++)
            {

                tuchDown = level.isSolidEntityLivingforbullets(x,y,0, ya,provider,this);//&& provider != Level.getPlayer();
                id = 0;


                if (isSolidTileforbullets(0, ya / yStep))
                    {
                        y += ya / yStep;
                    } else
                    {
                        ya = 0;
                        destroy();
                    }


            }
        xa = 0;
        ya = 0;

    }

    public void destroy()
    {
        this.removed = true;
        Particle p = new Particle();
        p.setTexture(Texture.fire_particle);
        p.setLifTime(2);
        p.setSpeed(0);
        p.setSize(10);
        p.setColor(Color.WHITE);
        level.addEntity(new ParticleSystem((int) x - 8, (int) y, 1, p));

    }


    public int getId()
    {
        return id;
    }

    public boolean getTuchDown()
    {
        return tuchDown;
    }



    public EntityLiving getProvider()
    {
        return (EntityLiving) provider;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
