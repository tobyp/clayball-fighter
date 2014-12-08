package net.tobyp.ld31;

import net.tobyp.ld31.character.Char;
import net.tobyp.ld31.ent.Entity;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.*;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tobyp on 12/6/14.
 */
public class Ld31 extends StateBasedGame {
    StateFight fight;

    public Ld31(String title) throws SlickException {
        super(title);

    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        Arena[] arenae = new Arena[]{
                Arena.loadArena("us", "Western (United States)"),
                Arena.loadArena("se", "IBORKA (Sweden)"),
        };

        Char[] characters = new Char[]{
                Char.loadCharacter("cn", "China"),
                Char.loadCharacter("se", "Swedeb"),
                Char.loadCharacter("gb", "United Kingdom"),
                Char.loadCharacter("us", "United States"),
        };

        StateOutro outro = new StateOutro(0); //0 for selection state id
        StateFight fight = new StateFight(outro);
        StateIntro intro = new StateIntro(fight);
        StateSelection sel = new StateSelection(characters, arenae, intro, fight);
        addState(sel);
        addState(intro);
        addState(fight);
        addState(outro);
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
