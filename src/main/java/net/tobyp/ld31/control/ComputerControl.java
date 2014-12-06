package net.tobyp.ld31.control;

import org.newdawn.slick.Input;

/**
 * @author Tom
 * Speed is currently being defined as Pixels per Millisecond. Can be modified mathmatically by entity classes.
 */
public class ComputerControl extends ControlMethod {
    private int key_left, key_right, key_jump, key_crouch;

    public ComputerControl(Input input, int key_left, int key_right, int key_jump, int key_crouch) {
        super(input);
        this.key_left = key_left;
        this.key_right = key_right;
        this.key_jump = key_jump;
        this.key_crouch = key_crouch;
    }

    @Override
    public float getXSpeed() {
        return (input.isKeyDown(key_right) ? 1 : 0) - (input.isKeyDown(key_left) ? 1 : 0);
    }

    @Override
    public boolean getJumping() {
        return input.isKeyDown(key_jump);
    }

    @Override
    public boolean getCrouching() {
        return input.isKeyDown(key_crouch);
    }
}
