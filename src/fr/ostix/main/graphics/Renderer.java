package fr.ostix.main.graphics;

import static org.lwjgl.opengl.GL11.*;

public class Renderer
{
    public static void quadData(float x, float y, int widht, int height, float[] color, int xo, int yo)
    {
        glColor4f(color[0], color[1], color[2], color[3]);
        glTexCoord2f((xo) / 32.0f, (yo) / 32.0f);
        glVertex2f(x, y);
        glTexCoord2f((1 + xo) / 32.0f, (yo) / 32.0f);
        glVertex2f(x + widht, y);
        glTexCoord2f((1 + xo) / 32.0f, (1 + yo) / 32.0f);
        glVertex2f(x + widht, y + height);
        glTexCoord2f((xo) / 32.0f, (1 + yo) / 32.0f);
        glVertex2f(x, y + height);
    }

    public static void renderQuad(float x, float y, int widht, int height, float[] color, int xo, int yo)
    {
        glBegin(GL_QUADS);

        Renderer.quadData(x, y, widht, height, color, xo, yo);

        glEnd();
    }

    public static void renderEntity(float x, float y, int widht, int height, float[] color, float size, int xo, int yo)
    {
        glBegin(GL_QUADS);
        glColor4f(color[0], color[1], color[2], color[3]);
        glTexCoord2f((xo) / size, (yo) / size);
        glVertex2f(x, y);
        glTexCoord2f((1 + xo) / size, (yo) / size);
        glVertex2f(x + widht, y);
        glTexCoord2f((1 + xo) / size, (1 + yo) / size);
        glVertex2f(x + widht, y + height);
        glTexCoord2f((xo) / size, (1 + yo) / size);
        glVertex2f(x, y + height);
        glEnd();
    }

    public static void particleData(float x, float y, int size, float[] color)
    {
        glColor4f(color[0], color[1], color[2], color[3]);

        glTexCoord2f(0, 2);
        glVertex2f(x, y);
        glTexCoord2f(1, 0);
        glVertex2f(x + size, y);
        glTexCoord2f(1, 1);
        glVertex2f(x + size, y + size);
        glTexCoord2f(0, 1);
        glVertex2f(x, y + size);
    }

    public static void renderBullet(float x, float y, float texSize, float size, int tex, float angle)
    {
        int xo = tex % 8;
        int yo = tex / 8;
        glPushMatrix();

        glTranslatef(x + size / 2, y + size / 2, 0);
        glRotatef(angle, 0, 0, 1);
        glTranslatef(-x - size / 2, -y - size / 2, 0);

        glBegin(GL_QUADS);
        glColor4f(1, 1, 1, 1);

        glTexCoord2f((xo) / texSize, (yo) / size);
        glVertex2f(x, y);

        glTexCoord2f((1 + xo) / texSize, (yo) / size);
        glVertex2f(x + size, y);

        glTexCoord2f((1 + xo) / texSize, (1 + yo) / size);
        glVertex2f(x + size, y + size);

        glTexCoord2f((xo) / texSize, (1 + yo) / size);
        glVertex2f(x, y + size);
        glEnd();

        glPopMatrix();
    }

    public static void renderGuiGame(float x, float y, float texSize, float size, int tex)
    {
        int xo = tex % 8;
        int yo = tex / 8;

        glBegin(GL_QUADS);
        glColor4f(1, 1, 1, 1);

        glTexCoord2f((xo) / texSize, (yo) / size);
        glVertex2f(x, y);

        glTexCoord2f((1 + xo) / texSize, (yo) / size);
        glVertex2f(x + size, y);

        glTexCoord2f((1 + xo) / texSize, (1 + yo) / size);
        glVertex2f(x + size, y + size);

        glTexCoord2f((xo) / texSize, (1 + yo) / size);
        glVertex2f(x, y + size);
        glEnd();

    }

    public static void renderBackGround(int x, int y, int widht, int height)
    {
        glBegin(GL_QUADS);
        glColor4f(1, 1, 1, 1);

        glTexCoord2f(0, 0);
        glVertex2f(x, y);

        glTexCoord2f(1, 0);
        glVertex2f(x + widht, y);

        glTexCoord2f(1, 1);
        glVertex2f(x + widht, y + height);

        glTexCoord2f(0, 1);
        glVertex2f(x, y + height);

        glEnd();
    }
}
