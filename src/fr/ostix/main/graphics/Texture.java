package fr.ostix.main.graphics;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class Texture
{
    int widht, height, id;

    public static Texture tiles = loadTexture("/textures/Tiles.png");
    public static Texture player = loadTexture("/textures/PlayerSprites.png");
    public static Texture fire_particle = loadTexture("/textures/fire.png");
    public static Texture bullets = loadTexture("/textures/bullets.png");
    public static Texture mainMenu = loadTexture("/textures/minecraft.png");


    public Texture(int widht, int height, int id)
    {
        this.widht = widht;
        this.height = height;
        this.id = id;
    }

    public static Texture loadTexture(String path)
    {
        BufferedImage image = null;
        try
            {
                image = ImageIO.read(Texture.class.getResource(path));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        int w = image.getWidth();
        int h = image.getHeight();

        int[] pixels = new int[w*h];
        image.getRGB(0,0,w,h,pixels,0,w);

        ByteBuffer buffer = BufferUtils.createByteBuffer(w*h*4);

        for (int y = 0;y<w;y++)
        {
            for (int x = 0;x<h;x++)
            {
                int i = pixels[x+y*w];
                buffer.put((byte) ((i >> 16) & 0xFF));
                buffer.put((byte) ((i >> 8) & 0xFF));
                buffer.put((byte) ((i ) & 0xFF));
                buffer.put((byte) ((i >> 24) & 0xFF));
            }
        }

        buffer.flip();


        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D,id);

        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_CLAMP_TO_EDGE);


        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);




        glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,w,h,0,GL_RGBA,GL_UNSIGNED_BYTE,buffer);

        return new Texture(w,h, id);
    }

    public int getWidht()
    {
        return widht;
    }

    public int getHeight()
    {
        return height;
    }

    public void bind()
    {
        glBindTexture(GL_TEXTURE_2D,id);
    }
    public void unbind()
    {
        glBindTexture(GL_TEXTURE_2D,0);
    }
}
