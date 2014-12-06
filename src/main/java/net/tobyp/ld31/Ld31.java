package net.tobyp.ld31;

import org.newdawn.slick.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tobyp on 12/6/14.
 */
public class Ld31 extends BasicGame implements InputListener {
    public Ld31(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

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
