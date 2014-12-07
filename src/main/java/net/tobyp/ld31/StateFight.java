package net.tobyp.ld31;

import net.tobyp.ld31.control.ControlMethod;
import net.tobyp.ld31.control.KeyboardControl;
import net.tobyp.ld31.ent.Entity;
import org.apache.commons.lang.ArrayUtils;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tobyp on 12/6/14.
 */
public class StateFight extends BasicGameState implements InputListener {
    private Arena arena;
    private Entity left;
    private Entity right;

    private SpriteSheet health;

    private static final float LEFT_HEALTH_BASE = 142;
    private static final float RIGHT_HEALTH_BASE = 142+448+100;

    public StateFight(Arena arena, Entity left, Entity right) {
        this.arena = arena;
        this.left = left;
        this.right = right;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        try {
            health = new SpriteSheet(Ld31.class.getResource("/health.png"), 448, 79);
        } catch (IOException e) {
            e.printStackTrace();
        }

        KeyboardControl p1_control = new KeyboardControl(left, Input.KEY_A, Input.KEY_D, Input.KEY_SPACE, Input.KEY_LSHIFT, Input.KEY_LCONTROL);
        KeyboardControl p2_control = new KeyboardControl(right, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_NUMPAD0, Input.KEY_RSHIFT, Input.KEY_RCONTROL);

        gameContainer.getInput().addListener(p1_control);
        gameContainer.getInput().addListener(p2_control);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        Image bg = arena.getBackground();
        graphics.drawImage(bg, 0, 0, gameContainer.getWidth(), gameContainer.getHeight(), 0, 0, bg.getWidth(), bg.getHeight());

        graphics.pushTransform();
        arena.applyTransform(graphics, gameContainer);
        //ARENA SPACE (1 unit is width of one country ball, origin is center ground)

        graphics.drawLine(-10.f, arena.getGround(), 10.f, arena.getGround());
        graphics.drawLine(0.f, -10.f, 0.f, 10.f);
        graphics.drawLine(-10.f, arena.getGround(), 10.f, arena.getGround());
        graphics.drawLine(arena.getLeftBoundary(), -10.f, arena.getLeftBoundary(), 10.f);
        graphics.drawLine(-10.f, arena.getGround(), 10.f, arena.getGround());
        graphics.drawLine(arena.getRightBoundary(), -10.f, arena.getRightBoundary(), 10.f);

        left.render(graphics);
        right.render(graphics);

        graphics.popTransform();
        //PIXEL SCREEN SPACE (1 unit is one pixel, origin is top left)

        graphics.drawImage(health.getSprite(0, 1), 142, 20);
        graphics.drawImage(left.getCharacter().getHubImage(), 0, 0);
        int lhw = (int)((448-19-14)*(left.getHealth())); //actual width of red, the 19/14 are the left/right margins
        graphics.drawImage(health.getSprite(0, 0),
                LEFT_HEALTH_BASE+(448-14)-lhw, 20,
                LEFT_HEALTH_BASE+(448-14), 79+20,
                (448-14)-lhw, 0,
                (448-14), 79);
        graphics.drawImage(health.getSprite(0, 3), 142+448+100, 20);
        graphics.drawImage(right.getCharacter().getHubImage(), 1280-150, 0);
        int rhw = (int)((448-24-28)*(right.getHealth())); //actual width of red, the 24/28 are the left/right margins
        //Health Bars here
        graphics.drawImage(health.getSprite(0, 2),
                RIGHT_HEALTH_BASE+24, 20,
                RIGHT_HEALTH_BASE+24+rhw, 79+20,
                24, 0,
                24+rhw, 79);

        Image fg = arena.getForeground();
        if (fg != null) {
            graphics.drawImage(fg, 0, 0, gameContainer.getWidth(), gameContainer.getHeight(), 0, 0, fg.getWidth(), fg.getHeight());
        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        left.update((float)i/1000.f, this);
        right.update((float)i/1000.f, this);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    @Override
    public boolean isAcceptingInput() {
        return false;
    }
}
