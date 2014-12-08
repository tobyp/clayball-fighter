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
    private int key_crouch;
    private float direction = 0.f;

    private int keysdown = 0;

    public KeyboardEntityController(Entity entity, int key_left, int key_right, int key_jump, int key_attack, int key_crouch) {
        this.entity = entity;
        this.key_left = key_left;
        this.key_right = key_right;
        this.key_jump = key_jump;
        this.key_attack = key_attack;
        this.key_crouch = key_crouch;
    }


    public void update(float delta) {
        if (entity.locked > 0) {
            entity.locked -= delta;
            return;
        }

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
            keysdown++;
        } else if (c == this.key_right) {
            direction += 1.f;
            keysdown++;
        } else if (c == this.key_jump) {
            entity.jump();
            keysdown++;
        } else if (c == this.key_attack) {
            entity.melee();
            keysdown++;
        } else if (c == this.key_crouch) {
            entity.crouch();
            keysdown++;
        }
        entity.setBounce(direction != 0.f);
    }

    @Override
    public void keyReleased(int c, char ch) {
        if (keysdown == 0) return;
        if (c == this.key_left) {
            direction += 1.f;
            keysdown--;
        } else if (c == this.key_right) {
            direction -= 1.f;
            keysdown--;
        } else if (c == this.key_jump) {
            keysdown--;
        } else if (c == this.key_attack) {
            keysdown--;
        } else if (c == this.key_crouch) {
            keysdown--;
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
