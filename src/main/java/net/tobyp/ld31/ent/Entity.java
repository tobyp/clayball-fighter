package net.tobyp.ld31.ent;

import net.tobyp.ld31.Animation;
import net.tobyp.ld31.Arena;
import net.tobyp.ld31.StateFight;
import net.tobyp.ld31.character.Char;
import net.tobyp.ld31.misc.GameSound;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.Graphics;

import java.lang.management.BufferPoolMXBean;
import java.util.List;

/**
 * @author Tom
 */
public class Entity {
    protected static final float BOUNCE_PERIOD = .35f;
    protected static final float BOUNCE_HEIGHT = .3f; //can't be too high, as we're not actually offsetting the position (sorry)

    protected vec2 pos; //defined such that y=0 is the baseline (center of a ball when it's touching the ground), x=0 is the center, and the unit is round about the diameter of a polandball
    protected vec2 vel = new vec2(0.f, 0.f);
    protected Char character;
    protected double health = 1;
    protected int team;
    protected boolean flipped;
    protected Animation animation;
    protected Animation base_animation;
    protected boolean bouncing = false;
    protected float bounce_time = 0.f;

    protected StateFight stateFight;

    protected int jumps = 0;


    public Entity(Char character, vec2 pos, int team, boolean flipped) {
        this.character = character;
        this.pos = pos;
        this.team = team;
        this.flipped = flipped;
        this.base_animation = character.getIdleAnimation();
        this.animation = base_animation;
    }

    public void update(float delta, StateFight state) {
        if (animation.update(delta) && animation != base_animation) {
            animation = base_animation;
        };

        /*if (bouncing && jumps == 0) {
            bounce_time = (bounce_time + delta) % BOUNCE_PERIOD;
        }
        else {
            if (bounce_time > 0.f) {
                bounce_time += delta;
                if (bounce_time > BOUNCE_PERIOD) {
                    bounce_time = 0.f;
                }
            }
        }*/

        if (bouncing && jumps == 0 || bounce_time > 0) {
            bounce_time += delta;
            if (!bouncing) {
                bounce_time = 0.f;
            }
            else {
                bounce_time %= BOUNCE_PERIOD;
            }
        }

        Arena arena = state.getArena();
        this.stateFight = state;

            //dx/dt
            pos = pos.add(vel.mul(delta));
            if (pos.y > 0.f) { //Game is set in australia
                pos = pos.withY(0.f);
                vel = vel.withY(0.f);
                jumps = 0;
            } else if (pos.y < 0.f) {
                vel = vel.add(new vec2(0, 0.2f));
            }

            //x
            if (pos.x < arena.getLeftBoundary()) {
                pos = pos.withX(arena.getLeftBoundary());
                knockBack(-vel.x, 0);
            } else if (pos.x > arena.getRightBoundary()) {
                pos = pos.withX(arena.getRightBoundary());
                knockBack(-vel.x, 0);
            }
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public void jump() {
        if (jumps < 2) {
            jumps++;
            bounce_time = 0.f;
            vel = vel.withY(-character.getJumpPower());
            GameSound.JUMP.play(1, 1);
        }
    }
    public void damage(vec2 source, float amount) {
        this.health = Math.max(0.f, health - amount);
        knockBack((pos.x - source.x) * (amount * 100), (pos.y - source.y) * (amount * 100));
    }

    public void melee() {
        Entity ent = this == stateFight.getLeft() ? stateFight.getRight() : stateFight.getLeft();

        vec2 distance = ent.getPos().sub(pos);
        if (distance.x > 0f && flipped) return;
        if (distance.x < 0f && !flipped) return;
        if (Math.abs(distance.x) > 1.5f) return;
        if (Math.abs(distance.y) > 0.5f) return;

        GameSound.MELEE.play(1, 1);
        animation = character.getAttackAnimation();
        ent.damage(pos, (float) (Math.random() * 0.05) + 0.02f);
    }

    public void knockBack(float x, float y) {
        vel = vel.withX(x).add(0, x / 3 + y);
    }

    public void setBounce(boolean bounce) {
        this.bouncing = bounce;
    }

    public void render(Graphics graphics) {
        float bounce_y = BOUNCE_HEIGHT * (float)Math.abs(Math.sin(bounce_time * Math.PI / BOUNCE_PERIOD)); //BOUNCE_PERIOD is half a sine period, so divide py PI instead of 2 PI
        animation.draw(graphics, pos.x, pos.y - bounce_y, flipped);
        //graphics.drawRect(pos.x - 0.5f, pos.y - 0.5f + bounce_y, 1.f, 1.f);
    }

    public void move(vec2 d) { this.pos = this.pos.add(d); }

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
