package net.tobyp.ld31.control;

import net.tobyp.ld31.ent.Entity;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tom on 06/12/2014.
 */
public class ControlManager {
    Map<Entity, ControlMethod> entity_controller_map = new HashMap<>();

    public void register(Entity entity, ControlMethod controller) {
        entity_controller_map.put(entity, controller);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        for (Map.Entry<Entity, ControlMethod> pair : entity_controller_map.entrySet()) {
            Entity ent = pair.getKey();
            ControlMethod controller = pair.getValue();

            if (ent.getVel().x < controller.getHorizontalDirection() * ent.getCharacter().getSpeed()) {
                ent.changeVel(new vec2((ent.getCharacter().getSpeed() / 1000) * i, 0));
            }else
            if (ent.getVel().x > controller.getHorizontalDirection() * ent.getCharacter().getSpeed()) {
                ent.changeVel(new vec2(-((ent.getCharacter().getSpeed() / 1000) * i), 0));
            }
        }
    }
}
