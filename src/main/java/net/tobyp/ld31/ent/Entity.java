package net.tobyp.ld31.ent;

import net.tobyp.ld31.Arena;
import net.tobyp.ld31.StateFight;
import net.tobyp.ld31.character.Char;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

/**
 * @author Tom
 */
public class Entity {
    protected vec2 pos; //defined such that y=0 is the ground, x=0 is the center, and the unit is round about the diameter of a polandball
    protected vec2 vel = new vec2(0.f, 0.f);
    protected Char character;
    protected FighterState state = FighterState.IDLE;
    protected double health = 1.0;
    protected int team;
    protected boolean flipped;
    protected Animation animation;

    public Entity(Char character, vec2 pos, int team, boolean flipped) {
        this.character = character;
        this.pos = pos;
        this.team = team;
        this.flipped = flipped;
        this.animation = character.getAnimation(state);
    }

    public void update(float delta, StateFight state) {

        Arena arena = state.getArena();

        pos.add(vel.mul(delta));
        if (pos.y <= 0.f) {
            pos = pos.withY(0.f);
            onLand();
        }

        else if (pos.x < arena.getLeftBoundary()) {
            pos = pos.withX(arena.getLeftBoundary()); //don't change state, we allow "running into the wall"
        }
        else if (pos.x > arena.getRightBoundary()) {
            pos = pos.withX(arena.getRightBoundary()); //don't change state, we allow "running into the wall"
        }

        //test collision

        /*float x_speed = 0; //Pixels per millisecond
        float y_speed = 0; //Pixels per millisecond

        if (controller.getJumping()) { //Regular jump. Cancel slide.
            slide = 0;
        }else
        if (controller.getCrouching() && Math.abs(controller.getXSpeed()) == 1 && slide > 0) { //Slide
            slide = 1500;
        }

        if (slide > 0) {
            x_speed = slide / 500;
            slide = slide - delta;
        }else{
            x_speed = (controller.getXSpeed() * character.getSpeed()) / 1000;
        }

        //TODO: Gravity from jumping or being knocked back applied to y speed.

        vel = new vec2(x_speed * delta, y_speed);*/
    }

    public void onLand() {

    }

    public void render(Graphics graphics) {
        graphics.pushTransform();
        if (flipped) {
            graphics.scale(-1.f, 1.f);
        }
        graphics.drawAnimation(animation, pos.x, pos.y);
        graphics.popTransform();
    }

    public void changeVel(vec2 vel) {
        this.vel = this.vel.add(vel);
    }

    public void setState(FighterState state) {
        this.state = state;
    }

    public vec2 getPos() {
        return pos;
    }

    public vec2 getVel() {
        return vel;
    }

    public FighterState getState() {
        return state;
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
