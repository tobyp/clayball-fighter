package net.tobyp.ld31;

import net.tobyp.ld31.character.Char;
import net.tobyp.ld31.control.KeyboardSelectionController;
import net.tobyp.ld31.ent.Entity;
import net.tobyp.ld31.misc.GameSound;
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

    private SpriteSheet frame_sprites;
    private SpriteSheet hub_eyes;
    private Image bg_pattern;
    private Image keycaps;

    private static final int ARENA_KEY = Input.KEY_TAB;

    private KeyboardSelectionController left;
    private KeyboardSelectionController right;
    private StateFight fight_state;
    private StateIntro intro_state;

    private int bg_step = 0;
    private int bg_offset = 0;

    private SpriteSheetFont font;

    public StateSelection(Char[] characters, Arena[] arenae, StateIntro intro_state, StateFight fight_state) {
        this.characters = characters;
        this.arenae = arenae;
        this.fight_state = fight_state;
        this.intro_state = intro_state;

        left = new KeyboardSelectionController(this, Input.KEY_A, Input.KEY_D, Input.KEY_W, Input.KEY_S, Input.KEY_C);
        right = new KeyboardSelectionController(this, Input.KEY_J, Input.KEY_L, Input.KEY_I, Input.KEY_K, Input.KEY_PERIOD);
    }

    public void reset() {
        left.reset();
        right.reset();
    }

    public int getCharCount() { return characters.length; }

    public int getLineWidth() { return 5; }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void keyPressed(int key, char c) {
        if (key == ARENA_KEY) {
            arena_index = (arena_index + 1) % arenae.length;
            GameSound.SELECT.play(1, 1);
        }
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        try {
            frame_sprites = new SpriteSheet(Ld31.class.getResource("/frames.png"), 150, 170);
            hub_eyes = new SpriteSheet(Ld31.class.getResource("/hub_eyes.png"), 150, 150);
            keycaps = new Image(Ld31.class.getResourceAsStream("/keycaps.png"), "keycaps", false);
            bg_pattern = new Image(Ld31.class.getResourceAsStream("/stripes.png"), "stripes", false);
            font = new SpriteSheetFont(new SpriteSheet(Ld31.class.getResource("/fonts/scribble.png"), 30, 42), ' ');
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
        if (graphics.getFont() != font) graphics.setFont(font);

        float width = 650.f;

        Arena arena = arenae[arena_index];
        graphics.drawImage(arena.getBackground(),
                0, 0, gameContainer.getWidth(), gameContainer.getHeight(),
                0, 0, arena.getBackground().getWidth(), arena.getBackground().getHeight());
        graphics.fillRect(0, 0, gameContainer.getWidth(), gameContainer.getHeight(),
                bg_pattern.getSubImage(bg_offset, 0, 50, 50),
                0, 0);
        graphics.setColor(new Color(.28f, 0.33f, 0.58f, 0.4f));
        graphics.fillRect(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
        graphics.setColor(new Color(1, 1, 1));

        int w = graphics.getFont().getWidth(arena.getName());
        graphics.drawString(arena.getName().toUpperCase(), (gameContainer.getWidth() - w) / 2.f, 74);

        graphics.drawImage(keycaps,
                0, 0, gameContainer.getWidth(), gameContainer.getHeight(),
                0, 0, keycaps.getWidth(), keycaps.getHeight());

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

            Entity left_ent = new Entity(left_char, new vec2(-2.5f, 0.f), false);
            Entity right_ent = new Entity(right_char, new vec2(2.5f, 0.f), true);

            intro_state.setCharacters(left_char, right_char);
            fight_state.setArena(arenae[arena_index]);
            fight_state.setEntities(left_ent, right_ent);
            stateBasedGame.enterState(intro_state.getID());
            reset();
        }

        bg_step += i;

        if (bg_step > 100) {
            bg_step = 0;
            bg_offset += 1;
            if (bg_offset > 49) {
                bg_offset = 0;
            }
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

    @Override
    public boolean isAcceptingInput() {
        return true;
    }
}
