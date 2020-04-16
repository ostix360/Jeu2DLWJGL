package fr.ostix.main.entities.particles;

import fr.ostix.main.entities.Entity;
import fr.ostix.main.game.level.Level;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class ParticleSystem extends Entity
{
    private List<Particle> particles = new ArrayList<>();

    public ParticleSystem(int x, int y, int num, Particle p)
    {
        super(x, y);
        for (int i = 0; i < num; i++)
            {
                particles.add(new Particle(p, x, y));
            }

        if (p.texture != null) this.texture = p.texture;
    }

    @Override
    public void init(Level level)
    {
        this.level = level;
    }

    @Override
    public void update()
    {
        for (int i = 0; i < particles.size(); i++)
            {
                Particle p = particles.get(i);
                p.update();
                if (p.removed)
                    {
                        particles.remove(p);
                    }
            }

    }

    @Override
    public void render()
    {
        if (texture != null) texture.bind();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE);
        for (Particle p : particles)
            {
                p.render();
            }

        if (texture != null) texture.unbind();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    }
}
