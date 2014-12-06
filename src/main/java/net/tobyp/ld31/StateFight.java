package net.tobyp.ld31;

import net.tobyp.ld31.control.ControlMethod;
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
public class StateFight extends BasicGameState {
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

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        Image bg = arena.getBackground();
        graphics.drawImage(bg, 0, 0, gameContainer.getWidth(), gameContainer.getHeight(), 0, 0, bg.getWidth(), bg.getHeight());

        for (Entity e : entities) {
            e.render(graphics);
        }

        //Health Bars here

        Image fg = arena.getBackground();
        graphics.drawImage(fg, 0, 0, gameContainer.getWidth(), gameContainer.getHeight(), 0, 0, fg.getWidth(), fg.getHeight());
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
}
