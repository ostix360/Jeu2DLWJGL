package fr.ostix.main.game;

import fr.ostix.main.Component;
import fr.ostix.main.graphics.Renderer;
import fr.ostix.main.graphics.Texture;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class MainMenu
{

    Game game;
    private boolean started;

    public MainMenu()
    {

        started = false;
    }

    public void init()
    {
        if (game != null)
            game.init();
    }

    public void update()
    {

        if (Mouse.isButtonDown(0) && Mouse.getX() > 230 &&
                Mouse.getX() < 300 || Mouse.isButtonDown(0) && Mouse.getY() > 230 &&
                Mouse.getY() < 300) start();
        if (game != null)
            game.update();
        if (Keyboard.isKeyDown(Keyboard.KEY_M))
            {
                start();
            }
    }

    private void start()
    {

        if (!started)
            game = new Game();

        started = true;
    }

    public void render()
    {
        if (game != null)
            game.render();
        else
            {
                Texture.mainMenu.bind();
                Renderer.renderBackGround(0, 0, Component.widht, Component.height);
                Texture.mainMenu.unbind();

                GL11.glBegin(GL11.GL_QUADS);
                GL11.glColor4f(1, 1, 1, 1);
                GL11.glVertex2f(230, 230);
                GL11.glVertex2f(230 + 300, 230);
                GL11.glVertex2f(230 + 300, 230 + 300);
                GL11.glVertex2f(230, 230 + 300);
                GL11.glEnd();
            }
    }
}
