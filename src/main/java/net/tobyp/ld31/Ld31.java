package net.tobyp.ld31;

import net.tobyp.ld31.character.Char;
import net.tobyp.ld31.control.KeyboardControl;
import net.tobyp.ld31.ent.Entity;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tobyp on 12/6/14.
 */
public class Ld31 extends StateBasedGame {
    StateFight fight;

    static final float ENTITY_SPEED = 0.2f;
    private SpriteSheet sprites_uk;
    private SpriteSheet sprites_us;

    public Ld31(String title) {
        super(title);
        sprites_uk = new SpriteSheet((Image)null, 256, 256);
        sprites_us = new SpriteSheet((Image)null, 256, 256);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        Arena arena = new Arena();

        Char char_uk = new Char("United Kingdom", sprites_uk, ENTITY_SPEED);
        Char char_us = new Char("United States", sprites_us, ENTITY_SPEED);

        Entity p1 = new Entity(char_us, new vec2(-2.f, 0.5f), 1, false);
        Entity p2 = new Entity(char_uk, new vec2(2.f, 0.5f), 2, true);

        KeyboardControl p1_control = new KeyboardControl(p1, Input.KEY_A, Input.KEY_D, Input.KEY_SPACE, Input.KEY_LSHIFT, Input.KEY_LCONTROL);
        KeyboardControl p2_control = new KeyboardControl(p2, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_NUMPAD0, Input.KEY_RSHIFT, Input.KEY_RCONTROL);

        fight = new StateFight(arena, p1, p2);
        addState(fight);
    }

    public static void main(String[] args) {
        Logger logger = Logger.getGlobal();
        try {
            AppGameContainer c = new AppGameContainer(new ScalableGame(new Ld31("Ludum Dare 31"), 800, 600));
            c.setDisplayMode(800, 600, false);
            c.setShowFPS(false);
            //c.setIcon("src/main/resources/icon/icon.png");
            c.start();
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Game error.", e);
        }
    }
}
