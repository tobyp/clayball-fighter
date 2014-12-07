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
    private static final float OUTRO_TIME = 8.f;
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

    public StateOutro(int state_selection_id) {
        this.state_selection_id = state_selection_id;
    }

    public void setResult(Char left, Char right, Char winner) {
        this.left = left;
        this.right = right;
        this.winner = winner;

        if (winner == null) {
            flag = neutral;
        }
        else {
            flag = winner.getFlag();
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
    public void keyPressed(int key, char c) {
        //any key skips
        time = OUTRO_TIME;
    }
}
