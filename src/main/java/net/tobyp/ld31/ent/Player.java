package net.tobyp.ld31.ent;

import net.tobyp.ld31.ControlMethod;
import net.tobyp.ld31.Ld31;

/**
 * Created by Tom on 06/12/2014.
 */
public class Player extends LivingEntity {
    public Player(Ld31 game, Character character, ControlMethod method) {
        super(name, sprites);
    }

    @Override
    public void update(int delta) {
        super.update(delta);
    }
}
