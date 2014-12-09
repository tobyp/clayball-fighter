package net.tobyp.ld31.ent;

import net.tobyp.ld31.Animation;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by tobyp on 12/8/14.
 */
public class Projectile {
    private static final vec2 GRAVITY = new vec2(0.f, 6.125f); //half gravity, would usually be 12.5f
    private vec2 pos;
    private vec2 vel;
    private Entity thrower;
    private Entity victim; //for multiplayer this would be a list
    private Animation anim;

    public Projectile(Entity victim, Entity thrower, vec2 pos, vec2 vel, Animation animation) {
        this.pos = pos;
        this.vel = vel;
        this.victim = victim;
        this.thrower = thrower;
        this.anim = animation;
    }

    public boolean update(float delta) {
        vel = vel.add(GRAVITY.mul(delta));
        pos = pos.add(vel.mul(delta));
        anim.update(delta);

        if (victim.getPos().getDistance(pos) < .5f) { //.f = half the victim
            thrower.updateStreak(1);
            thrower.lastattack = 1.3f;
            victim.damage(pos, (float) (Math.random() * 0.05) +0.04f);
            return false;
        }

        if (pos.y > 0.5f) { //0 is the mid-height point of a grounded ball, and + is down
            return false;
        }
        if (Math.abs(pos.y) > 5.f) { //left arena
            return false;
        }

        return true;
    }

    public void render(Graphics graphics) {
        anim.draw(graphics, pos.x, pos.y, false);
    }
}
