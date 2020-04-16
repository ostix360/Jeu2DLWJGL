package fr.ostix.main.entities.particles;

import fr.ostix.main.graphics.Color;
import fr.ostix.main.graphics.Renderer;
import fr.ostix.main.graphics.Texture;
import fr.ostix.main.math.Vector2f;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class Particle
{
    public boolean removed = false;


    public Texture texture;
    float x, y;
    double rx, ry;
    int time = 0;
    private float[] color;
    private int size;
    private float speed;

    Random rand = new Random();
    private int lifTime;

    public Particle()
    {
    }

    private Vector2f direction;

    public Particle(Particle p, int x, int y)
    {
        this.x = x;
        this.y = y;

        this.direction = p.direction;
        this.color = p.color;
        this.size = p.size;
        this.speed = p.speed;
        this.lifTime = p.lifTime;

        this.direction = p.direction;

        rx = rand.nextGaussian();
        ry = rand.nextGaussian();
    }

    public Particle(Texture texture, int size, float speed, int lifTime, int[] randomness)
    {
    }

    public Particle(Color color, int size, float speed, int lifTime, int[] randomness)
    {
    }

    public void update()
    {
        time++;
        if (time > lifTime)
            {
                removed = true;
                return;
            }
        if (speed == 0 || direction == null)
            {
                return;
            }
        x += (rx + direction.getX()) * speed;
        y += (ry + direction.getY()) * speed;
    }

    public void render()
    {
        GL11.glBegin(GL11.GL_QUADS);
        Renderer.particleData(x, y, size, color);
        GL11.glEnd();
    }

    public Particle setColor(float[] color)
    {
        this.color = color;
        return this;
    }

    public Particle setSize(int size)
    {
        this.size = size;
        return this;
    }

    public Particle setSpeed(float speed)
    {
        this.speed = speed;
        return this;
    }

    public Particle setLifTime(int lifTime)
    {
        this.lifTime = lifTime;
        return this;
    }

    public Particle setDirection(Vector2f direction)
    {
        this.direction = direction;
        return this;
    }

    public Texture getTexture()
    {
        return texture;
    }

    public Particle setTexture(Texture texture)
    {
        this.texture = texture;
        return this;
    }
}
