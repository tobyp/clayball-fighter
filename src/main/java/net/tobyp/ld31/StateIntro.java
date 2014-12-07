package net.tobyp.ld31;

import net.tobyp.ld31.character.Char;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 * Created by tobyp on 12/7/14.
 */
public class StateIntro extends BasicGameState {
    private static final float INTRO_TIME = 1.f;

    private SpriteSheet hub_eyes;
    private SpriteSheet identities;
    private Image special_lights;
    private Image divider;

    private Char left, right;

    private float time = 0.f;
    private GameState next_state;

    public StateIntro(GameState next_state) {
        this.next_state = next_state;
    }

    public void setCharacters(Char left, Char right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        try {
            hub_eyes = new SpriteSheet(Ld31.class.getResource("/hub_eyes.png"), 150, 150);
            identities = new SpriteSheet(Ld31.class.getResource("/identities.png"), 300, 120);
            special_lights = new Image(Ld31.class.getResourceAsStream("/lights.png"), "lights", false);
            divider = new Image(Ld31.class.getResourceAsStream("/divider.png"), "divider", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        float delta = (float)i/1000.f;
        time += delta;

        if (time >= INTRO_TIME) {
            stateBasedGame.enterState(next_state.getID());
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        float scaleup = 2;

        special_lights.setImageColor(1, 1, 1, 0.35f);

        Image flagl = left.getFlag().copy();
        Image flagr = right.getFlag().copy();
        graphics.drawImage(flagl,
                0, 0, gameContainer.getWidth()/2, gameContainer.getHeight(),
                gameContainer.getWidth()/2 - time*250, 0, gameContainer.getWidth() - time*250, gameContainer.getHeight(),
                new Color(0, 0, 0.5f, 1f));
        graphics.fillRect(0, 0, gameContainer.getWidth()/2, gameContainer.getHeight(), special_lights, special_lights.getWidth() + gameContainer.getWidth()/2 - Math.round((time) * 500), 0);
        graphics.fillRect(0, 0, gameContainer.getWidth()/2, gameContainer.getHeight(), special_lights, special_lights.getWidth() + gameContainer.getWidth()/2 - Math.round((time) * 300), 0);
        Image profile = left.getProfileImage();
        profile.draw(gameContainer.getWidth()/4 - profile.getWidth()/2*scaleup - time*20, gameContainer.getHeight()/2 - profile.getHeight()/2*scaleup, scaleup);
        hub_eyes.getSprite(0, 0).draw(gameContainer.getWidth()/4 - profile.getWidth()/2*scaleup - time*20, gameContainer.getHeight()/2 - profile.getHeight()/2*scaleup, scaleup);
        Image identity = identities.getSprite(0, 0);
        identity.draw(gameContainer.getWidth()/4 - identity.getWidth()/2 + time*20, gameContainer.getHeight() - gameContainer.getHeight()/4);

        graphics.drawImage(flagr.getFlippedCopy(true, false), gameContainer.getWidth()/2, 0, gameContainer.getWidth(), gameContainer.getHeight(), time*250, 0, gameContainer.getWidth()/2 + time*250, gameContainer.getHeight(), new Color(0, 0, 0.5f, 1f));
        graphics.fillRect(gameContainer.getWidth()/2, 0, gameContainer.getWidth()/2, gameContainer.getHeight(), special_lights, Math.round((time) * 500), 0);
        graphics.fillRect(gameContainer.getWidth()/2, 0, gameContainer.getWidth()/2, gameContainer.getHeight(), special_lights, Math.round((time) * 300), 0);
        profile = right.getProfileImage();
        profile.getFlippedCopy(true, false).draw(gameContainer.getWidth() - gameContainer.getWidth()/4 - profile.getWidth()/2*scaleup + time*20, gameContainer.getHeight()/2 - profile.getHeight()/2*scaleup, scaleup);
        hub_eyes.getSprite(0, 0).getFlippedCopy(true, false).draw(gameContainer.getWidth() - gameContainer.getWidth()/4 - profile.getWidth()/2*scaleup + time*20, gameContainer.getHeight()/2 - profile.getHeight()/2*scaleup, scaleup);
        identity = identities.getSprite(0, 1);
        identity.draw(gameContainer.getWidth() - gameContainer.getWidth()/4 - identity.getWidth()/2 - time*20, gameContainer.getHeight() - gameContainer.getHeight()/4);

        graphics.drawImage(divider, (gameContainer.getWidth()-divider.getWidth())/2, 0);
    }

    @Override
    public void keyPressed(int key, char c) {
        //any key will skip intro
        time = INTRO_TIME;
    }
}
