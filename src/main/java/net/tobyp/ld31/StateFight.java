package net.tobyp.ld31;

import net.tobyp.ld31.control.ControlMethod;
import net.tobyp.ld31.control.KeyboardControl;
import net.tobyp.ld31.ent.Entity;
import org.apache.commons.lang.ArrayUtils;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tobyp on 12/6/14.
 */
public class StateFight extends BasicGameState implements InputListener {
    private Arena arena;
    private List<Entity> entities;

    public StateFight(Arena arena, Entity... entities) {
        this.arena = arena;
        this.entities = Arrays.asList(entities);
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        KeyboardControl p1_control = new KeyboardControl(entities.get(0), Input.KEY_A, Input.KEY_D, Input.KEY_SPACE, Input.KEY_LSHIFT, Input.KEY_LCONTROL);
        KeyboardControl p2_control = new KeyboardControl(entities.get(1), Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_NUMPAD0, Input.KEY_RSHIFT, Input.KEY_RCONTROL);

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

        for (Entity e : entities) {
            e.render(graphics);
        }

        graphics.popTransform();
        //PIXEL SCREEN SPACE (1 unit is one pixel, origin is top left)

        //Health Bars here

        Image fg = arena.getForeground();
        if (fg != null) {
            graphics.drawImage(fg, 0, 0, gameContainer.getWidth(), gameContainer.getHeight(), 0, 0, fg.getWidth(), fg.getHeight());
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

        for (Entity e : entities) {
            e.update((float)i/1000.f, this);
            if (e.isDead()) {
                //add Game over state, and stateBasedGame.enter it
            }
        }
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

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public boolean isAcceptingInput() {
        return false;
    }
}
