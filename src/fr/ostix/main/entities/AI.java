package fr.ostix.main.entities;

import fr.ostix.main.entities.living.EntityLiving;
import fr.ostix.main.entities.particles.Particle;
import fr.ostix.main.entities.particles.ParticleSystem;
import fr.ostix.main.game.Game;
import fr.ostix.main.game.bulllets.BasicBullet;
import fr.ostix.main.game.level.Level;
import fr.ostix.main.graphics.Color;
import fr.ostix.main.graphics.Renderer;
import fr.ostix.main.graphics.Texture;
import fr.ostix.main.math.Vector2f;
import fr.ostix.main.utiles.Animation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.*;


public class AI extends EntityLiving
{
    Animation anim;
    int dir = 0;

    float speed = 0.1f;
    int health = 10, numBullets = 10;
    int timer;
    int size = 16;
    float xa, ya;
    int id;

    public AI(float x, float y, int id)
    {
        super(x, y, id);
        this.id = id;
        texture = Texture.player;
        anim = new Animation(3, 10, true);
        this.timerforBullets = 0;
        mass = 0.5f;
        drag = 0.95f;

        shootPoint = new Vector2f(8, 8);
        mouseDirection = new Vector2f();


    }

    @Override
    public void init(Level level)
    {
        this.level = level;

    }

    @Override
    public void update()
    {
        this.bounds[0] = (int) x;
        this.bounds[1] = (int) y ;
        this.bounds[2] = (int) (x + size);
        this.bounds[3] = (int) (y + size);

        timerforBullets++;

        timer++;
        ya += level.gravity * mass;

        anim.update();
        anim.pause();

        if (isGrounded())
            {
                drag = 0.8f;
            } else
            {
                drag = 0.95f;
            }

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            {
                ya -= speed * 3;

                Particle p = new Particle();
                p.setColor(Color.WHITE);
                p.setTexture(Texture.fire_particle);
                p.setDirection(new Vector2f(0, 4));
                p.setSize(5);
                p.setSpeed(0.5f);
                p.setLifTime(9);
                level.addEntity(new ParticleSystem(((int) x + 8), (int) y + 8, 10, p));

            }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP))
            {
                if (isGrounded())
                    {
                        ya -= 3.4;
                    }
            }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
            {
                ya += speed;
            }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
            {
                xa -= speed;
                dir = 1;
                anim.play();
            }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
            {
                xa += speed;
                dir = 0;
                anim.play();
            }

        if (numBullets >= 9)
            {
                if (timerforBullets > 15)
                    {

                        if (Mouse.isButtonDown(0))
                            {
                                level.addEntity(new BasicBullet(x + shootPoint.getX(), y + shootPoint.getY(), 8, mouseDirection, 2, this));
                                numBullets--;
                                timerforBullets = 0;

                            }
                    }
            }

        if (Game.getMouseX() > x - 8) dir = 1;
        if (Game.getMouseX() > x + 8) dir = 0;


        mouseDirection.set(Game.getMouseX() - (x + shootPoint.getX()), Game.getMouseY() - (y + shootPoint.getY())).normalize();


        int xStep = (int) Math.abs(xa * 1000);
        for (int i = 0; i < xStep; i++)
            {
                if (isSolidTile(xa / xStep, 0))
                    {
                        x += xa / xStep;
                    } else
                    {
                        xa = 0;
                    }
            }
        int yStep = (int) Math.abs(ya * 1000);
        for (int i = 0; i < yStep; i++)
            {
                if (isSolidTile(0, ya / yStep))
                    {
                        y += ya / yStep;
                    } else
                    {
                        ya = 0;
                    }
            }

        xa *= drag;
        ya *= drag;


        if (numBullets < 10)
            {
                recharge();
            }


    }

    private void recharge()
    {

        if (timer >= 25)
            {
                if (numBullets >= 10) numBullets = 10;
                numBullets++;
                timer = 0;
            }
    }

    @Override
    public void render()
    {
        texture.bind();
        Renderer.renderEntity(x, y, size, size, Color.WHITE, 4, 1 + dir, anim.getCurrentFrame());
        texture.unbind();

        glLineWidth(5);
        glBegin(GL_LINES);
        glVertex2f(x + shootPoint.getX(), y + shootPoint.getY());
        glVertex2f(x + shootPoint.getX() + mouseDirection.getX() * 16, y + shootPoint.getY() + mouseDirection.getY() * 16);
        glEnd();
    }

    public int getHealth()
    {
        return health;
    }

    public int getNumBullets()
    {
        return numBullets;
    }

    @Override
    public String toString()
    {
        return "Bot" + this.id;
    }
}
