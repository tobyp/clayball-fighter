package net.tobyp.ld31.ent;

import net.tobyp.ld31.Animation;
import net.tobyp.ld31.Arena;
import net.tobyp.ld31.StateFight;
import net.tobyp.ld31.character.Char;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.Graphics;

/**
 * @author Tom
 */
public class Entity {
    protected vec2 pos; //defined such that y=0 is the baseline (center of a ball when it's touching the ground), x=0 is the center, and the unit is round about the diameter of a polandball
    protected vec2 vel = new vec2(0.f, 0.f);
    protected Char character;
    protected double health = 0.7;
    protected int team;
    protected boolean flipped;
    protected Animation animation;

    protected int jumps = 0;
    protected Entity target = null;


    public Entity( Char character, vec2 pos, int team, boolean flipped) {
        this.character = character;
        this.pos = pos;
        this.team = team;
        this.flipped = flipped;
        this.animation = character.getIdleAnimation();
    }

    public void update(float delta, StateFight state) {
        animation.update(delta);

        Arena arena = state.getArena();

        //dx/dt
        pos = pos.add(vel.mul(delta));
        if (pos.y > 0.f) { //Game is set in australia
            pos = pos.withY(0.f);
            vel = vel.withY(0.f);
            jumps = 0;
        }else
        if (pos.y < 0.f) {
            vel = vel.add(new vec2(0, 0.2f));
        }

        //x
        if (pos.x < arena.getLeftBoundary()) {
            pos = pos.withX(arena.getLeftBoundary());
            knockBack(-vel.x, 0);
        }
        else if (pos.x > arena.getRightBoundary()) {
            pos = pos.withX(arena.getRightBoundary());
            knockBack(-vel.x, 0);
        }
    }

    public void setTarget(Entity entity) {
        this.target = entity;
    }

    public void jump() {
        if (jumps < 2) {
            jumps++;
            vel = vel.withY(-character.getJumpPower());
        }
    }

    public void knockBack(float x, float y) {
        vel = vel.withX(x);
        vel = vel.add(0, x / 3 + y);
    }

    public void render(Graphics graphics) {
        animation.draw(graphics, pos.x, pos.y);
        //graphics.drawAnimation(animation, );
        graphics.drawRect(pos.x-0.5f, pos.y-0.5f, 1.f, 1.f);
    }

    public void attack() {
        //find nearby entities, maybe check if we're facing the right way, and damage them, possibly dependent on distance.
        //we don't need to do any actual targeting.
    }

    public void changeVel(vec2 vel) {
        this.vel = this.vel.add(vel);
    }

    public vec2 getPos() {
        return pos;
    }

    public vec2 getVel() {
        return vel;
    }

    public Char getCharacter() {
        return character;
    }

    public double getHealth() {
        return health;
    }

    public boolean isDead() {
        return health <= 0.0;
    }

    public int getTeam() {
        return team;
    }
}
