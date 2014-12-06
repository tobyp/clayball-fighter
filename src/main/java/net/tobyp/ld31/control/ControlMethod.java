package net.tobyp.ld31.control;

import net.tobyp.ld31.ent.Entity;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;

/**
 * Created by Tom on 06/12/2014.
 */
public interface ControlMethod {
    public float getHorizontalDirection();

    public boolean isPushed(ControlButton button);
}
