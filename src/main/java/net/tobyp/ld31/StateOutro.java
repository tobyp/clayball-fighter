package net.tobyp.ld31;

import net.tobyp.ld31.character.Char;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 * Created by tobyp on 12/7/14.
 */
public class StateOutro extends BasicGameState {
    private static final float OUTRO_TIME = 6.f;
    private Char left;
    private Char right;
    private Char winner;
    private SpriteSheet identities;
    private SpriteSheet hub_eyes;
    private Image neutral;

    private int state_selection_id;

    private float time = 0.f;
    private float display_time = OUTRO_TIME;

    private Image flag;
    private Image identity;
    private Image player_id;

    public StateOutro(int state_selection_id) {
        this.state_selection_id = state_selection_id;
    }

    public void setResult(Char left, Char right, Char winner) {
        this.left = left;
        this.right = right;
        this.winner = winner;

        if (winner == null) {
            flag = neutral;
            identity = identities.getSprite(0, 4);
        }
        else {
            flag = winner.getFlag();
            identity = identities.getSprite(0, 2);
            player_id = identities.getSprite(0, winner == left ? 0 : 1);
        }
    }

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        try {
            hub_eyes = new SpriteSheet(Ld31.class.getResource("/hub_eyes.png"), 150, 150);
            identities = new SpriteSheet(Ld31.class.getResource("/identities.png"), 300, 120);
            neutral = new Image(Ld31.class.getResourceAsStream("/neutral.png"), "neutral", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        float scale = (float)Math.abs(Math.sin(time * 2.f * Math.PI / OUTRO_TIME)) + .5f / 2.f; //oscillate 1 time between 0 and 1

        graphics.drawImage(flag,
                0, 0, flag.getWidth(), flag.getHeight(),
                0, 0, gameContainer.getWidth(), gameContainer.getHeight(),
                new Color(0.4f, 0.4f, 0.4f));
        float identity_cy = gameContainer.getHeight()/2.f;
        if (winner != null) {
            graphics.drawImage(winner.getProfileImage(), (gameContainer.getWidth() - 150) / 2.f, gameContainer.getHeight()/2 - 75, 0, 0, 150, 150);
            graphics.drawImage(hub_eyes.getSprite(1,0), (gameContainer.getWidth() - 150) / 2.f, gameContainer.getHeight()/2 - 75, 0, 0, 150, 150);
            graphics.drawImage(player_id, (gameContainer.getWidth()-player_id.getWidth())/2.f, (gameContainer.getHeight()-player_id.getWidth())/4.f);
            identity_cy = 370.f + (gameContainer.getHeight() - 370.f) / 2.f;
        }
        float identity_width = scale*identity.getWidth();
        float identity_height = scale*identity.getHeight();
        graphics.drawImage(identity,
                (gameContainer.getWidth()-identity_width)/2.f, identity_cy - (identity_height / 2.f), (gameContainer.getWidth()+identity_width)/2.f, identity_cy + (identity_height / 2.f),
                0, 0, identity.getWidth(), identity.getHeight());
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        float delta = (float)i/1000.f;
        time += delta;
        display_time = Math.max(0, display_time - delta);

        if (time >= OUTRO_TIME) {
            stateBasedGame.enterState(state_selection_id);
        }
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        time = 0.f;
    }

    @Override
    public void keyPressed(int key, char c) {
        //esc key skips
        if (key == Input.KEY_ESCAPE) time = OUTRO_TIME;
    }
}
