package net.tobyp.ld31;

import net.tobyp.ld31.character.Char;
import net.tobyp.ld31.control.KeyboardSelectionController;
import net.tobyp.ld31.ent.Entity;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 * Created by tobyp on 12/7/14.
 */
public class StateSelection extends BasicGameState {
    private SpriteSheet frames;
    private Char[] characters;
    private int arena_index = 0;
    private Arena[] arenae;

    private static final int ARENA_KEY = Input.KEY_TAB;

    private KeyboardSelectionController left;
    private KeyboardSelectionController right;
    private StateFight fight_state;

    private boolean inside;

    public StateSelection(Char[] characters, Arena[] arenae, StateFight fight_state) {
        this.fight_state = fight_state;
        this.characters = characters;
        this.arenae = arenae;

        left = new KeyboardSelectionController(this, Input.KEY_A, Input.KEY_D, Input.KEY_W, Input.KEY_S, Input.KEY_SPACE);
        right = new KeyboardSelectionController(this, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LCONTROL);
    }

    public int getCharCount() { return characters.length; }

    public int getLineWidth() { return 3; }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void keyPressed(int key, char c) {
        if (c == ARENA_KEY) {
            arena_index = (arena_index + 1) % arenae.length;
        }
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        try {
            frames = new SpriteSheet(Ld31.class.getResource("/frames.png"), 150, 170);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        if (!inside) return;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (!inside) return;

        if (left.isDone() && right.isDone()) {
            gameContainer.getInput().removeKeyListener(left);
            gameContainer.getInput().removeKeyListener(right);

            Char left_char = characters[left.getSelectionIndex()];
            Char right_char = characters[right.getSelectionIndex()];

            Entity left_ent = new Entity(left_char, new vec2(-2.5f, 0.f), 1, false);
            Entity right_ent = new Entity(right_char, new vec2(2.5f, 0.f), 2, true);

            fight_state.left = left_ent;
            fight_state.right = right_ent;
            fight_state.arena = arenae[arena_index];
            stateBasedGame.enterState(fight_state.getID());
        }
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        System.out.println("Enter Selection");
        container.getInput().addKeyListener(left);
        container.getInput().addKeyListener(right);
        inside = true;
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        System.out.println("Leave Selection");
        inside = false;
        container.getInput().removeKeyListener(left);
        container.getInput().removeKeyListener(right);
    }
}
