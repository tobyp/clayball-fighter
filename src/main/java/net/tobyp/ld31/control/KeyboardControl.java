package net.tobyp.ld31.control;

import net.tobyp.ld31.ent.Entity;
import net.tobyp.ld31.ent.FighterState;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Tom
 */
public class KeyboardControl extends ControlMethod {
    private int key_left, key_right, key_jump, key_crouch, key_attack;

    public KeyboardControl(Entity entity, int key_left, int key_right, int key_jump, int key_crouch, int key_attack) {
        super(entity);
        this.key_left = key_left;
        this.key_right = key_right;
        this.key_jump = key_jump;
        this.key_crouch = key_crouch;
        this.key_attack = key_attack;
    }

    @Override
    public void keyPressed(int i, char c) {
        if (i == key_left && entity.getState().canMove()) {
                entity.changeVel(vec2.LEFT.mul(entity.getCharacter().getSpeed()));
                entity.setState(entity.getVel().isZero() ? FighterState.IDLE : FighterState.MOVE);
        }
        else if (i == key_right && entity.getState().canMove()) {
            entity.changeVel(vec2.RIGHT.mul(entity.getCharacter().getSpeed()));
            entity.setState(entity.getVel().isZero() ? FighterState.IDLE : FighterState.MOVE);
        }
    }

    @Override
    public void keyReleased(int i, char c) {
        if (i == key_left && entity.getState().canMove()) {
            entity.changeVel(vec2.RIGHT.mul(entity.getCharacter().getSpeed()));
            entity.setState(entity.getVel().isZero() ? FighterState.IDLE : FighterState.MOVE);
        }
        else if (i == key_right && entity.getState().canMove()) {
            entity.changeVel(vec2.LEFT.mul(entity.getCharacter().getSpeed()));
            entity.setState(entity.getVel().isZero() ? FighterState.IDLE : FighterState.MOVE);
        }
    }
}
