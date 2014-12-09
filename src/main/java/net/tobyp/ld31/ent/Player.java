package net.tobyp.ld31.ent;

import net.tobyp.ld31.Animation;
import net.tobyp.ld31.Arena;
import net.tobyp.ld31.StateFight;
import net.tobyp.ld31.character.Char;
import net.tobyp.ld31.misc.GameSound;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * @author Tom
 */
public class Player {
    protected static final float BOUNCE_PERIOD = .35f;
    protected static final float BOUNCE_HEIGHT = .3f; //can't be too high, as we're not actually offsetting the position (sorry)

    protected static final float DAMAGE_BASE = 0.005f;
    protected static final float DAMAGE_VAR = 0.015f;
    protected static final float CHARGE_BASE = 0.0025f;
    protected static final float CHARGE_VAR = 0.0035f;

    protected vec2 pos; //defined such that y=0 is the baseline (center of a ball when it's touching the ground), x=0 is the center, and the unit is round about the diameter of a polandball
    protected vec2 vel = new vec2(0.f, 0.f);
    protected Char character;
    protected double health = 1; // 0 to 1
    protected boolean flipped;
    protected Animation animation;
    protected Animation base_animation;
    protected boolean bouncing = false;
    protected float bounce_time = 0.f;

    protected StateFight stateFight;

    protected int jumps = 0;
    protected boolean slam = false;

    protected int streak = 0;

    public float locked = 0;
    public float lastdmg = 0;
    public float lastattack = 0;

    public Player(Char character, vec2 pos, boolean flipped) {
        this.character = character;
        this.pos = pos;
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
            if (slam) slam();
            slam = false;
        } else if (pos.y < 0.f && vel.y < 7.f) {
            vel = vel.add(new vec2(0, (float) (0.2/16)*(delta*1000)));
        }

        //x
        if (pos.x < arena.getLeftBoundary()) {
            pos = pos.withX(arena.getLeftBoundary());
            knockBack(-vel.x, 0);
        } else if (pos.x > arena.getRightBoundary()) {
            pos = pos.withX(arena.getRightBoundary());
            knockBack(-vel.x, 0);
        }

        if (lastdmg > 0) {
            lastdmg -= delta;
        }else{
            if (health < 1) {
                health = Math.min(1.f, health + delta/50);
            }
        }

        if (lastattack > 0) {
            lastattack = Math.max(0.f, lastattack - delta);
            if (lastattack == 0) expireStreak();
        }
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public boolean getFlipped() {
        return flipped;
    }

    public void crouch() {
        if (jumps > 0 && !slam) {
            slam = true;
            vel = vel.add(new vec2(0, 7));
        }
    }

    public void slam() {
        Player ent = this == stateFight.getLeft() ? stateFight.getRight() : stateFight.getLeft();

        vec2 distance = ent.getPos().sub(pos);
        if (Math.abs(distance.x) > 0.75f) return;
        if (Math.abs(distance.y) > 1.25f) return;

        ent.damage(pos, (float) (Math.random() * 0.05) +0.04f);
        TextParticle.add(pos.add(0.f, -0.5f), "SLAM!", new Color(.45f, .05f, .05f, 0.7f), 1.3f);

        vel = new vec2(0, 0);
        locked = 1.5f;

    }

    public void jump() {
        if (jumps < 2 && !slam) {
            jumps++;
            bounce_time = 0.f;
            vel = vel.withY(-character.getJumpPower());
            bounce_time = 0.f;
            GameSound.JUMP.play(1, 1);
        }
    }

    public void damage(vec2 source, float amount) {
        this.health = Math.max(0.f, health - amount);
        TextParticle.add(pos.add(0.f, -0.5f), Integer.toString((int) Math.round(amount * 1000.f)), new Color(1, 0, 0, 0.7f), 0.6f);
        knockBack((pos.x - source.x) * (amount * 100), (pos.y - source.y) * (amount * 100));

        lastdmg = 3.f;

        resetStreak();
    }

    public void melee() {
        Player ent = this == stateFight.getLeft() ? stateFight.getRight() : stateFight.getLeft();

        animation = character.getAttackAnimation();

        vec2 distance = ent.getPos().sub(pos);
        if (distance.x > 0f && flipped) return;
        if (distance.x < 0f && !flipped) return;
        if (Math.abs(distance.x) > 1.5f) return;
        if (Math.abs(distance.y) > 0.5f) return;

        double damage = DAMAGE_BASE + CHARGE_BASE*getCharge() + (DAMAGE_VAR + CHARGE_VAR*getCharge())*Math.random();
        ent.damage(pos, (float)damage);

        GameSound.MELEE.play(1, 1);
        updateStreak(1);

        lastattack = 1.3f;
    }

    public void knockBack(float x, float y) {
        vel = vel.withX(x).add(0, x / 3 + y);
    }

    public void updateStreak(int update) {
        int newstreak = streak + update;

        if (newstreak >= 3 && newstreak <= 10) {
            TextParticle.add(pos.add(0.f, -0.5f), "COMBO", new Color(.21f, .76f, .78f, 0.8f), 0.9f);
        }

        streak = newstreak;
    }

    public void resetStreak() {
        if (streak >= 3) {
            TextParticle.add(pos.add(0.f, -0.5f), "C-C-COMBO BREAKER", new Color(1.f, .73f, 0.16f, 0.95f), 1.3f);
        }

        streak = 0;
    }

    public void expireStreak() {
        streak = 0;
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

    public double getCharge() {
        if (streak < 3) return 0.0;
        if (streak > 9) return 1.0;
        return (streak - 2) * 1.0/7.0;
    }
}
