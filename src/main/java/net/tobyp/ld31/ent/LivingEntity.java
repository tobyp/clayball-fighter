package net.tobyp.ld31.ent;

import javafx.geometry.Pos;
import net.tobyp.ld31.Ld31;
import net.tobyp.ld31.misc.Vector;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author Tom
 */
public class LivingEntity extends Entity {
    float health;

    public LivingEntity(Ld31 game, SpriteSheet sprites) {

    }

    public float getHealth() {
        return health;
    }
}
