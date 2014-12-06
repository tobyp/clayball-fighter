package net.tobyp.ld31.control;

import org.newdawn.slick.Input;

/**
 * Created by Tom on 06/12/2014.
 */
public abstract class ControlMethod {
    protected Input input;
    public ControlMethod(Input input) {
        this.input = input;
    }

    public abstract float getXSpeed();
    public abstract boolean getJumping();
    public abstract boolean getCrouching();
    public abstract boolean getAttacking();
}
