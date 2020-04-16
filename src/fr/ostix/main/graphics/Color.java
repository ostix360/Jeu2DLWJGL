package fr.ostix.main.graphics;

public class Color
{
    public float r, g, b, a;

    public static final float[] WHITE = new Color(1, 1, 1, 1).getFloatColor();
    public static final float[] BLACK = new Color(0, 0, 0, 1).getFloatColor();
    public static final float[] BLUE = new Color(0, 0, 1, 1).getFloatColor();
    public static final float[] RED = new Color(1, 0, 0, 1).getFloatColor();
    public static final float[] GREEN = new Color(0, 1, 0, 1).getFloatColor();

    public Color(float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public float[] getFloatColor()
    {
        return new float[]{r, g, b, a};
    }
}
