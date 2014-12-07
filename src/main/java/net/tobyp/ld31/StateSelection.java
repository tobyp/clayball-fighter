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
    private Char[] characters;
    private int arena_index = 0;
    private Arena[] arenae;

    private SpriteSheet arrow_sprites;
    private SpriteSheet frame_sprites;
    private SpriteSheet hub_eyes;

    private static final int ARENA_KEY = Input.KEY_TAB;
    private static final float profile_width = 150.0f;

    private KeyboardSelectionController left;
    private KeyboardSelectionController right;
    private StateFight fight_state;

    public StateSelection(Char[] characters, Arena[] arenae, StateFight fight_state) {
        this.fight_state = fight_state;
        this.characters = characters;
        this.arenae = arenae;

        left = new KeyboardSelectionController(this, Input.KEY_A, Input.KEY_D, Input.KEY_W, Input.KEY_S, Input.KEY_SPACE);
        right = new KeyboardSelectionController(this, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LCONTROL);
    }

    public int getCharCount() { return characters.length; }

    public int getLineWidth() { return 5; }

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
            frame_sprites = new SpriteSheet(Ld31.class.getResource("/frames.png"), 150, 170);
            arrow_sprites = new SpriteSheet(Ld31.class.getResource("/arrows.png"), 50, 50);
            hub_eyes = new SpriteSheet(Ld31.class.getResource("/hub_eyes.png"), 150, 150);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private float getXOfIndex(int index) {
        index = index % 5;
        return 215.f + index*(150+25);
    }

    private float getYOfIndex(int index) {
        index = index / 5;
        return 198.f + (index * 150+24);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        float width = 650.f;
        //TODO arena preview

        Arena arena = arenae[arena_index];
        //graphics.drawImage(arena.getPreview(), 315, 24, 965, 174, 0, 0, 650, 150);
        graphics.drawRect(315, 24, 650, 150);
        graphics.drawString(arena.getName(), 415, 74);
        graphics.drawImage(arrow_sprites.getSprite(0, 0), 340, 74);
        graphics.drawImage(arrow_sprites.getSprite(1, 0), 890, 74);

        for (int i=0; i<characters.length; i++) {
            float x = getXOfIndex(i);
            float y = getYOfIndex(i);
            graphics.drawImage(frame_sprites.getSprite(4+i%3, 0), x, y, x+150, y+150, 0, 0, 150, 150);
            graphics.drawImage(characters[i].getProfileImage(), x, y, x+150, y+150, 0, 0, 150, 150);
            int selcount = (left.getSelectionIndex() == i ? 1 : 0) + (right.getSelectionIndex() == i ? 1 : 0);
            graphics.drawImage(hub_eyes.getSprite(selcount,0), x, y, x+150, y+150, 0, 0, 150, 150);
            if (right.getSelectionIndex() == i) {
                graphics.drawImage(frame_sprites.getSprite(right.isDone() ? 3 : 2, 0), x, y, x+150, y+170, 0, 0, 150, 170);
            }
            if (left.getSelectionIndex() == i) {
                graphics.drawImage(frame_sprites.getSprite(left.isDone() ? 1 : 0, 0), x, y, x+150, y+170, 0, 0, 150, 170);
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
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
        container.getInput().addKeyListener(left);
        container.getInput().addKeyListener(right);
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        container.getInput().removeKeyListener(left);
        container.getInput().removeKeyListener(right);
    }
}
