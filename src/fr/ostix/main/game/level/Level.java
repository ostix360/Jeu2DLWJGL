package fr.ostix.main.game.level;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import fr.ostix.main.Component;
import fr.ostix.main.entities.AI;
import fr.ostix.main.entities.Entity;
import fr.ostix.main.entities.Player;
import fr.ostix.main.entities.living.EntityLiving;
import fr.ostix.main.game.GuiGame;
import fr.ostix.main.game.bulllets.BasicBullet;
import fr.ostix.main.game.level.tiles.Tile;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Level
{
    public float gravity = 0f;
    int number = 1;

    public int width, height;

    List<Tile> tiles = new ArrayList<>();
    Tile[][] solidTilesArray;
    Tile[][] bgTilesArray;
    AI[] AIArray = new AI[5];

    private int[] bounds = new int[4];

    List<EntityLiving> entitiesLiving = new ArrayList<>();
    List<Entity> entities = new ArrayList<>();
    private static Player player;
    AI ai1;
    GuiGame guiGame;

    public Level()
    {
        loadLevel("mon_level_metaldebug");
        guiGame = new GuiGame(Component.widht, Component.height);

    }

    public void spawner(int x, int y)
    {
        addEntity(player = new Player(x, y));
    }

    public void spawnerAI(int x, int y,int id)
    {
        addEntity(ai1 = new AI(x, y,id));
    }

    public void loadLevel(String name)
    {

        int[] pixels;

        BufferedImage image = null;
        try
            {
                image = ImageIO.read(Level.class.getResource("/levels/" + name + ".png"));
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        assert image != null;
        width = image.getWidth();
        height = image.getHeight();

        bounds[0] = -16;
        bounds[1] = -16;
        bounds[2] = -width * 16 + 16 + Display.getWidth() / Component.scale;
        bounds[3] = -height * 16 + 16 + Display.getHeight() / Component.scale;

        pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        solidTilesArray = new Tile[width][height];
        bgTilesArray = new Tile[width][height];

        for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                    {
                        if (pixels[x + y * width] == 0xFFffffff)
                            {
                                solidTilesArray[x][y] = new Tile(x, y, Tile.Tiles.SOLIDE_METAL);
                            }
                        if (pixels[x + y * width] == 0xFF00ffff)
                            {
                                bgTilesArray[x][y] = new Tile(x, y, Tile.Tiles.BG_METAL);
                                spawnerAI(x,y,number);
                                number++;
                            }
                        if (pixels[x + y * width] == 0xFFffff00)
                            {
                                bgTilesArray[x][y] = new Tile(x, y, Tile.Tiles.BG_METAL);
                                spawner(x, y);
                            } else
                            {
                                bgTilesArray[x][y] = new Tile(x, y, Tile.Tiles.BG_METAL);
                            }
                    }
            }

        setTiles();

    }

    public void setTiles()

    {
        for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                    {
                        if (x - 1 < 0 || y - 1 < 0 || x + 1 >= width || y + 1 >= height) continue;
                        boolean vu = false, vd = false, vl = false, vr = false;
                        boolean vur = false, vdr = false, vul = false, vdl = false;

                        if (solidTilesArray[x + 1][y] == null)
                            {
                                vr = true;
                            }
                        if (solidTilesArray[x - 1][y] == null)
                            {
                                vl = true;
                            }
                        if (solidTilesArray[x][y + 1] == null)
                            {
                                vd = true;
                            }
                        if (solidTilesArray[x][y - 1] == null)
                            {
                                vu = true;
                            }
                        //********************************************//
                        if (solidTilesArray[x + 1][y + 1] == null)
                            {
                                vdr = true;
                            }
                        if (solidTilesArray[x - 1][y - 1] == null)
                            {
                                vul = true;
                            }
                        if (solidTilesArray[x - 1][y + 1] == null)
                            {
                                vdl = true;
                            }
                        if (solidTilesArray[x + 1][y - 1] == null)
                            {
                                vur = true;
                            }

                        if (solidTilesArray[x][y] != null)
                            {
                                solidTilesArray[x][y].setTiles(vr, vl, vd, vu, vdr, vur, vdl, vul);
                            }
                        addTiles(x, y);

                    }
            }

    }



    public Tile getSolidTilesArray(int x, int y)
    {
        if (x < 0 || y < 0 || x >= width || y >= height) return null;
        return solidTilesArray[x][y];
    }

    public void addTiles(int x, int y)
    {
        if (solidTilesArray[x][y] != null)
            {
                tiles.add(solidTilesArray[x][y]);
            } else if (bgTilesArray[x][y] != null)
            {
                tiles.add(bgTilesArray[x][y]);
            }
    }

    public void addEntity(Entity e)
    {

        if (e instanceof BasicBullet)
            {
                e.init(this);
                entities.add(e);
            }
        if (e instanceof Player)
            {
                e.init(this);
                entitiesLiving.add(0,(EntityLiving) e);
            }
        if (e instanceof AI)
            {
                e.init(this);
                entitiesLiving.add(number,(EntityLiving) e);
            } else
            {
                entities.add(e);
            }
    }

    public void removeEntity(Entity e)
    {
        entities.remove(e);
    }


    public void init()
    {
        for (Entity e : entities)
            {
                e.init(this);
            }
    }
    int h =0;

    public void update()
    {
        //if (player.isDead())Component.exit();

        for (int i = 0; i < entities.size(); i++)
            {
                Entity e = entities.get(i);
                if (e instanceof EntityLiving)continue;
                if (e.isRemoved()) entities.remove(e);
                e.update();

                if (e instanceof BasicBullet)
                    {
                        if (((BasicBullet) e).getTuchDown())
                            {
                                entitieDamage((BasicBullet)e);
                            }

                        ;
                    }
            }
        for (int i = 0; i< entitiesLiving.size();i++)
            {
                EntityLiving e = entitiesLiving.get(i);
                if (e.isDead()){
                    entitiesLiving.remove(i);
                }
                System.out.println(entitiesLiving.get(i).getId());
                e.update();
            }
        guiGame.update();

    }

    private void entitieDamage(BasicBullet e)
    {
        for (EntityLiving entityLiving : entitiesLiving)
            {
                if (e.getId() == e.getProvider().getId())
                    {
                        return;
                    } else if (e.getId() == entityLiving.getId())
                    {
                        entityLiving.damage(e.getProvider(), e.getDamage());

                    }
            }
    }

    public void render()
    {
        for (Tile tile : tiles)
            {
                tile.render();
            }
        for (Entity e : entities)
            {
                if (e instanceof EntityLiving)continue;
                e.render();

            }

        for (EntityLiving entityLiving : entitiesLiving)
            {
                if (!(entityLiving instanceof Player)) entityLiving.render();
            }
        entitiesLiving.get(0).render();
        guiGame.render();
    }


    int timer = 0;
    public boolean isSolidEntityLivingforbullets(float x,float y,float xa, float ya, Entity provider,BasicBullet bullet)
    {
        timer ++ ;
        for (EntityLiving entityLiving : entitiesLiving)
            {

                int x0 = (int) (x + xa);
                int x1 = (int) (x + xa);
                int y0 = (int) (y + ya+10);
                int y1 = (int) (y + ya);

                if (timer >= 30)
                    {

                        System.out.println(entityLiving.getId());
//                        System.out.print("x0:                " + x0);
//                        System.out.print(" x1:                " + x1);
//                        System.out.print(" y0:                " + y0);
//                        System.out.println(" y1:               " + y1);
//
//
//                        System.out.print("EntityBounds( 0 ): " + entityLiving.getBounds(0) );
//                        System.out.print(" EntityBounds( 2 ): " + entityLiving.getBounds(2) );
//                        System.out.print(" EntityBounds( 1 ): " + entityLiving.getBounds(1) );
//                        System.out.println(" EntityBounds( 3 ): " + entityLiving.getBounds(3));
                        timer = 0;

                    }
                if (provider == entityLiving)return false;
                if ((entityLiving.getBounds(0)  < x0) &&
                        (entityLiving.getBounds(2)  >= x1) &&
                        (entityLiving.getBounds(1)  < y0) &&
                        (entityLiving.getBounds(3) >= y1))
                    {
                        System.out.println(entityLiving.getId() + " est touché");
                        bullet.setId(entityLiving.getId());
                        bullet.destroy();
                        System.out.println(entityLiving.toString());
                        return true;
                    }

            }
        return false;
    }

    public Entity getEntitiesLiving(int index)
    {
        return entitiesLiving.get(index);
    }

    public List<EntityLiving> getEntitiesLiving()
    {
        return entitiesLiving;
    }

    public int getBounds(int index)
    {
        return bounds[index];
    }

    public static Player getPlayer()
    {
        return player;
    }


}
