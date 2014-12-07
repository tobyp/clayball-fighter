package net.tobyp.ld31;

import net.tobyp.ld31.character.Char;
import net.tobyp.ld31.ent.Entity;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.*;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tobyp on 12/6/14.
 */
public class Ld31 extends StateBasedGame {
    StateFight fight;

    static final float ENTITY_SPEED = 4.f;
    static final float ENTITY_JUMP_POWER = 6f;

    public Ld31(String title) throws SlickException {
        super(title);

    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        SpriteSheet idle_gb;
        SpriteSheet idle_us;
        SpriteSheet idle_se;
        Image hub_gb;
        Image hub_us;
        Image hub_se;
        Image arena_us_bg;
        Image arena_se_bg;
        Image flag_gb;
        Image flag_us;
        Image flag_se;
        Image bg_stripes;
        Image special_lights;

        try {
            idle_gb = new SpriteSheet(Ld31.class.getResource("/gb/idle.png"), 256, 256);
            idle_us = new SpriteSheet(Ld31.class.getResource("/us/idle.png"), 256, 256);
            idle_se = new SpriteSheet(Ld31.class.getResource("/se/idle.png"), 256, 256);
            hub_gb = new Image(Ld31.class.getResourceAsStream("/gb/hub.png"), "gb_hub", false);
            hub_us = new Image(Ld31.class.getResourceAsStream("/us/hub.png"), "us_hub", false);
            hub_se = new Image(Ld31.class.getResourceAsStream("/se/hub.png"), "se_hub", false);
            arena_us_bg = new Image(TextureLoader.getTexture("picture", Ld31.class.getResourceAsStream("/us/arena.png")));
            arena_se_bg = new Image(TextureLoader.getTexture("picture", Ld31.class.getResourceAsStream("/se/arena.png")));
            flag_gb = new Image(TextureLoader.getTexture("picture", Ld31.class.getResourceAsStream("/gb/flag.png")));
            flag_us = new Image(TextureLoader.getTexture("picture", Ld31.class.getResourceAsStream("/gb/flag.png")));
            flag_se = new Image(TextureLoader.getTexture("picture", Ld31.class.getResourceAsStream("/gb/flag.png")));
            bg_stripes = new Image(Ld31.class.getResourceAsStream("/stripes.png"), "stripes", false);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SlickException(e.getMessage(), e);
        }

        Arena[] arenae = new Arena[]{
                new Arena("Western", arena_us_bg, null, null, 9.f, 0.8f, 0.5f, -3.5f, 3.5f),
                new Arena("IBÖRKA", arena_se_bg, null, null, 9.f, 0.8f, 0.5f, -3.5f, 3.5f)
        };

        Char[] characters = new Char[]{
                new Char("United Kingdom", idle_gb, hub_gb, flag_gb, ENTITY_SPEED, ENTITY_JUMP_POWER),
                new Char("United States", idle_us, hub_us, flag_us, ENTITY_SPEED, ENTITY_JUMP_POWER),
                new Char("Sweden", idle_se, hub_se, flag_se, ENTITY_SPEED, ENTITY_JUMP_POWER)
        };

        StateFight fight = new StateFight();
        StateSelection sel = new StateSelection(characters, arenae, fight, bg_stripes);
        addState(sel);
        addState(fight);
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
