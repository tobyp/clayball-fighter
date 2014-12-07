package net.tobyp.ld31.control;

import net.tobyp.ld31.ent.Entity;
import net.tobyp.ld31.ent.FighterState;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 06/12/2014.
 */
public class ControlManager {
    Map<Entity, ControlMethod> entity_controller_map = new HashMap<>();
    Map<Entity, List<ControlButton>> entity_button_activity_map = new HashMap<>();

    public void register(Entity entity, ControlMethod controller) {
        entity_controller_map.put(entity, controller);
        entity_button_activity_map.put(entity, new ArrayList<ControlButton>());
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        for (Map.Entry<Entity, ControlMethod> pair : entity_controller_map.entrySet()) {
            Entity ent = pair.getKey();
            ControlMethod controller = pair.getValue();
            List<ControlButton> active_buttons = entity_button_activity_map.get(ent);

            if (controller.isPushed(ControlButton.JUMP) && !active_buttons.contains(ControlButton.JUMP)) {
                ent.jump();
            }

            if (controller.getHorizontalDirection() == 0.f && Math.abs(ent.getVel().x) < 0.05f) {
                ent.changeVel(new vec2(-ent.getVel().x, 0.f));
            }else
            if (ent.getVel().x < controller.getHorizontalDirection() * ent.getCharacter().getSpeed()) {
                ent.changeVel(new vec2(((ent.getCharacter().getSpeed() * (1 + Math.abs(controller.getHorizontalDirection()))) / 250) * i, 0.f));
            }else
            if (ent.getVel().x > controller.getHorizontalDirection() * ent.getCharacter().getSpeed()) {
                ent.changeVel(new vec2(-(((ent.getCharacter().getSpeed() * (1 + Math.abs(controller.getHorizontalDirection()))) / 250) * i), 0.f));
            }

            if (controller.getHorizontalDirection() != 0.f) {
                ent.setState(FighterState.MOVE);
            }else{
                ent.setState(FighterState.IDLE);
            }

            active_buttons = new ArrayList<>();
            entity_button_activity_map.get(ent).clear();
            if (controller.isPushed(ControlButton.LEFT)) active_buttons.add(ControlButton.LEFT);
            if (controller.isPushed(ControlButton.RIGHT)) active_buttons.add(ControlButton.RIGHT);
            if (controller.isPushed(ControlButton.JUMP)) active_buttons.add(ControlButton.JUMP);
            if (controller.isPushed(ControlButton.CROUCH)) active_buttons.add(ControlButton.CROUCH);
            if (controller.isPushed(ControlButton.ATTACK)) active_buttons.add(ControlButton.ATTACK);

            entity_button_activity_map.put(ent, active_buttons);
        }
    }
}
