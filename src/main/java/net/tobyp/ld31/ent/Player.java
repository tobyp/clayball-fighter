package net.tobyp.ld31.ent;

import net.tobyp.ld31.character.GameCharacter;
import net.tobyp.ld31.control.ControlMethod;
import net.tobyp.ld31.Ld31;
import net.tobyp.ld31.misc.Vector;

/**
 * @author Tom
 */
public class Player extends LivingEntity {
    ControlMethod controller;
    GameCharacter character;

    int slide = 0;
    boolean slam = false;

    public Player(Ld31 game, GameCharacter character, ControlMethod controller) {
        super(game, character.getSpriteSheet());
        this.character = character;
        this.controller = controller;
    }

    @Override
    public void update(int delta) {
        super.update(delta);

        float x_speed = 0; //Pixels per millisecond
        float y_speed = 0; //Pixels per millisecond

        if (controller.getJumping()) { //Regular jump. Cancel slide.
            slide = 0;
        }else
        if (controller.getCrouching() && Math.abs(controller.getXSpeed()) == 1 && slide > 0) { //Slide
            slide = 1500;
        }

        if (slide > 0) {
            x_speed = slide / 500;
            slide = slide - delta;
        }else{
            x_speed = (controller.getXSpeed() * character.getSpeed()) / 1000;
        }

        //TODO: Gravity from jumping or being knocked back applied to y speed.

        vel = new Vector(x_speed * delta, y_speed);
    }
}
