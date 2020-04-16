package fr.ostix.main;

import fr.ostix.main.game.MainMenu;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

public class Component
{
    private boolean running = false;
    long timer = System.currentTimeMillis();

    public static String title = "Jeu 2D";

    public static int scale = 3;
    public static int widht = 720 / scale;
    public static int height = 480 / scale;


    public static boolean tick = false;
    public static boolean render = false;


    DisplayMode mode = new DisplayMode(widht * scale, height * scale);
    MainMenu menu;

    public Component()
    {
        display();

        menu = new MainMenu();
    }

    public static void main(String[] args)
    {
        Component main = new Component();
        main.start();
    }

    public void display()
    {
        try
            {
                Display.setDisplayMode(mode);
                Display.setResizable(true);
                Display.setFullscreen(true);
                Display.setTitle(title);
                Display.create();

                view2D(widht, height);
            } catch (LWJGLException e)
            {
                e.printStackTrace();
                System.err.println("Erreur lors de la creation de la fenetre");
                System.exit(1);
            }
    }

    private void view2D(int widht, int height)
    {
        glViewport(0, 0, widht * scale, height * scale);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluOrtho2D(0, widht, height, 0);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glEnable(GL_TEXTURE_2D);


        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    private void start()
    {
        menu.init();
        running = true;
        loop();
    }

    private void stop()
    {
        running = false;
    }

    public void exit()
    {
        Display.destroy();
        System.exit(0);
    }

    private void loop()
    {


        long before = System.nanoTime();
        long beforeRender = System.nanoTime();
        double elapsed ;
        double elapsedRender ;
        double nanoSeconds = 1000000000.0 / 60;
        double renderTime = 1000000000.0 / 50;

        int ticks = 0;
        int frames = 0;

        while (running)
            {
                if (Display.isCloseRequested()) stop();
                widht = Display.getWidth() / scale;
                height = Display.getHeight() / scale;

                tick = false;
                render = false;

                long now = System.nanoTime();
                elapsed = now - before;
                elapsedRender = now - beforeRender;

                if (elapsed > nanoSeconds)
                    {
                        before += nanoSeconds;
                        update();
                        ticks++;
                    } else if (elapsedRender > renderTime )
                    {
                        render();
                        Display.update();
                        frames++;
                        beforeRender += renderTime;

                    }else
                    {
                        try
                            {
                                Thread.sleep(1000/50);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                    }

                if (System.currentTimeMillis() - timer > 1000)
                    {
                        timer += 1000;
                        Display.setTitle(title + " || Ticks: " + ticks + ", fps: " + frames);
                        ticks = 0;
                        frames = 0;
                    }


            }
        exit();
    }

    private void update()
    {
        menu.update();
    }

    private void render()
    {
        view2D(widht, height);

        glClear(GL_COLOR_BUFFER_BIT);

        menu.render();
    }


}
