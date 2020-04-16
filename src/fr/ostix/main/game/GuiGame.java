package fr.ostix.main.game;

import fr.ostix.main.Component;
import fr.ostix.main.game.level.Level;
import fr.ostix.main.graphics.Renderer;
import fr.ostix.main.graphics.Texture;

public class GuiGame
{
    float widht;
    float height;
    float xa, ya;

    float playerHealth, playerBullets;

    private Level level;

    public GuiGame(float widht, float height)
    {
        this.widht = widht;
        this.height = height;
    }


    public void update()
    {
        widht = Component.widht;
        height = Component.height;

        this.playerHealth = Level.getPlayer().getHealth();
        this.playerBullets = Level.getPlayer().getNumBullets();
        xa = Component.widht / 2;
        ya = Component.height / 2;

    }


    public void render()
    {

        for (int x = 0; x < 10; x++)
            {
                Texture.bullets.bind();
                if (x < playerHealth)
                    {
                        Renderer.renderGuiGame(x * 8 - Game.xScroll, height - Game.yScroll - 8, 8, 8, 2);
                    } else
                    {
                        Renderer.renderGuiGame(x * 8 - Game.xScroll, height - Game.yScroll - 8, 8, 8, 1);
                    }


                Texture.bullets.unbind();
            }
        for (int x = 0; x < playerBullets; x++)
            {
                int xa = x % 10;
                int ya = x / 10;

                Texture.bullets.bind();
                Renderer.renderGuiGame(x * 8 - Game.xScroll + 100, ya * 10 + height - Game.yScroll - 8, 8, 8, 0);
                Texture.bullets.unbind();
            }


    }


}
