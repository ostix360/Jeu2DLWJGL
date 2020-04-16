package fr.ostix.main.math;

public class Vector2f
{
    public float x, y;

    public Vector2f(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2f()
    {
        this(0, 0);
    }

    public Vector2f(Vector2f v)
    {
        this(v.x, v.y);
    }

    public Vector2f set(float x, float y)
    {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2f normalize()
    {
        float mag = (float) Math.sqrt(x * x + y * y);

        x /= mag;
        y /= mag;

        return this;
    }

    public Vector2f mul(float v)
    {
        x *= v;
        y *= v;

        return this;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }
}

