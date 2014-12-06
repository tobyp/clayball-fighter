package net.tobyp.ld31.control;

import net.tobyp.ld31.ent.Entity;
import net.tobyp.ld31.ent.FighterState;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tom
 */
public class KeyboardControl implements ControlMethod {
    private Input input;
    private Map<ControlButton, Integer> buttons = new HashMap<>();

    public KeyboardControl(Input input, int key_left, int key_right, int key_jump, int key_crouch, int key_attack) {
        this.input = input;
        buttons.put(ControlButton.LEFT, key_left);
        buttons.put(ControlButton.RIGHT, key_right);
        buttons.put(ControlButton.JUMP, key_jump);
        buttons.put(ControlButton.CROUCH, key_crouch);
        buttons.put(ControlButton.ATTACK, key_attack);
    }

    @Override
    public float getHorizontalDirection() {
        return (isPushed(ControlButton.RIGHT) ? 1 : 0) - (isPushed(ControlButton.LEFT) ? 1 : 0);
    }

    @Override
    public boolean isPushed(ControlButton button) {
        return input.isKeyDown(buttons.get(button));
    }
}
