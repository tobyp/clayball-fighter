package net.tobyp.ld31.control;

import net.tobyp.ld31.ent.Entity;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 * Created by tobyp on 12/7/14.
 */
public class KeyboardEntityController implements KeyListener {
    private Entity entity;
    private int key_left;
    private int key_right;
    private int key_jump;
    private int key_attack;
    private float direction = 0.f;

    public KeyboardEntityController(Entity entity, int key_left, int key_right, int key_jump, int key_attack) {
        this.entity = entity;
        this.key_left = key_left;
        this.key_right = key_right;
        this.key_jump = key_jump;
        this.key_attack = key_attack;
    }


    public void update(float delta) {
        if (direction == 0.f && Math.abs(entity.getVel().x) < 0.05f) {
            entity.changeVel(new vec2(-entity.getVel().x, 0.f));
        } else if (entity.getVel().x < direction * entity.getCharacter().getSpeed()) {
            entity.changeVel(new vec2(entity.getCharacter().getSpeed() * (1 + Math.abs(direction)) * 4.f * delta, 0.f));
        } else if (entity.getVel().x > direction * entity.getCharacter().getSpeed()) {
            entity.changeVel(new vec2(-(entity.getCharacter().getSpeed() * (1 + Math.abs(direction)) * 4.f * delta), 0.f));
        }

        if (direction > 0) {
            entity.setFlipped(false);
        } else if (direction < 0) {
            entity.setFlipped(true);
        }
    }

    @Override
    public void keyPressed(int c, char ch) {
        if (c == this.key_left) {
            direction -= 1.f;
        } else if (c == this.key_right) {
            direction += 1.f;
        } else if (c == this.key_jump) {
            entity.jump();
        } else if (c == this.key_attack) {
            entity.melee();
        }
        entity.setBounce(direction != 0.f);
    }

    @Override
    public void keyReleased(int c, char ch) {
        if (c == this.key_left) {
            direction += 1.f;
        } else if (c == this.key_right) {
            direction -= 1.f;
        }
        entity.setBounce(direction != 0.f);
    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
