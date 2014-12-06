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

    static final float ENTITY_SPEED = 1.8f;
    private SpriteSheet idle_gb;
    private SpriteSheet idle_us;
    private Image arena_us_bg;

    public Ld31(String title) throws SlickException {
        super(title);

    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        idle_gb = new SpriteSheet("src/main/resources/gb/idle.png", 256, 256);
        idle_us = new SpriteSheet("src/main/resources/us/idle.png", 256, 256);
        arena_us_bg = new Image("src/main/resources/us/arena.png");

        Arena arena = new Arena("Western", arena_us_bg, null, null, 9.f, 0.8f, 0.5f, -4.f, 4.f);

        Char char_gb = new Char("United Kingdom", idle_gb, ENTITY_SPEED);
        Char char_us = new Char("United States", idle_us, ENTITY_SPEED);

        Entity p1 = new Entity(char_us, new vec2(-2.5f, 0f), 1, false);
        Entity p2 = new Entity(char_gb, new vec2(2.5f, 0f), 2, true);

        fight = new StateFight(arena, p1, p2);
        addState(fight);
        enterState(fight.getID());
    }

    public static void main(String[] args) {
        Logger logger = Logger.getGlobal();
        try {
            AppGameContainer c = new AppGameContainer(new Ld31("Ludum Dare 31"));
            c.setDisplayMode(1280, 720, false);
            //c.setTargetFrameRate(60);
            c.setVSync(true);
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
