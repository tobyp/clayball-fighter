package net.tobyp.ld31;

import net.tobyp.ld31.control.KeyboardEntityController;
import net.tobyp.ld31.ent.Entity;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 * Created by tobyp on 12/6/14.
 */
public class StateFight extends BasicGameState implements InputListener {
    private Arena arena;
    private Entity left;
    private Entity right;
    KeyboardEntityController p1_control;
    KeyboardEntityController p2_control;

    private SpriteSheet health;
    private SpriteSheet hub_eyes;

    private static final float HEALTH_INNER_MARGIN = 17;
    private static final float HEALTH_OUTER_MARGIN = 67;
    private static final float COMBO_INNER_MARGIN = 23;
    private static final float COMBO_OUTER_MARGIN = 77;

    private static final Color health_color = new Color(1.f, 0.f, 0.f);
    private static final Color charge_color = new Color(0.f, 0.f, 1.f);

    private static final float HAPPY_THRESH = 0.7f;
    private static final float SAD_THRESH = 0.4f;

    private StateOutro outro_state;

    public StateFight(StateOutro state_outro) {
        this.outro_state = state_outro;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        try {
            health = new SpriteSheet(Ld31.class.getResource("/bar.png"), 430, 150);
            hub_eyes = new SpriteSheet(Ld31.class.getResource("/hub_eyes.png"), 150, 150);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        float delta = (float)i/1000.f;

        p1_control.update(delta);
        p2_control.update(delta);

        left.update(delta, this);
        right.update(delta, this);

        //Collisions
        vec2 sep = right.getPos().sub(left.getPos());
        float dist = sep.length();
        if (dist <= .75f) {
            vec2 repel = sep.unit().mul(.75f).sub(sep);
            right.move(repel.mul(.5f));
            left.move(repel.mul(-.5f));
        }
        /*if (Math.abs(right.getPos().x - left.getPos().x) < 1.f) {
            if (Math.abs(right.getPos().y - left.getPos().y) < 0.5f) {
                left.knockBack(left.getPos().x - right.getPos().x, 0);
                right.knockBack(right.getPos().x - left.getPos().x, 0);
            }
        }*/

        if (left.getHealth() <= 0.f || right.getHealth() <= 0.f) {
            outro_state.setResult(left.getCharacter(), right.getCharacter(), (left.getHealth() < right.getHealth() ? right.getCharacter() : (left.getHealth() > right.getHealth() ? left.getCharacter() : null)));
            stateBasedGame.enterState(outro_state.getID());
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(new Color(1, 1, 1, 1));

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

        //left profile
        graphics.drawImage(left.getCharacter().getProfileImage(), 0, 0);
        if (left.getHealth() >= HAPPY_THRESH) {
            graphics.drawImage(hub_eyes.getSubImage(1,0), 0, 0);
        }
        else if (left.getHealth() >= SAD_THRESH) {
            graphics.drawImage(hub_eyes.getSubImage(0,0), 0, 0);
        }
        else if (left.getHealth() > 0.f) {
            graphics.drawImage(hub_eyes.getSubImage(2,0), 0, 0);
        }
        else {
            graphics.drawImage(hub_eyes.getSubImage(3,0), 0, 0);
        }

        //left health
        float left_health_width = 430.f-HEALTH_OUTER_MARGIN-HEALTH_INNER_MARGIN;
        float left_health_take = (float)left.getHealth() * left_health_width;
        graphics.drawImage(health.getSprite(0, 1),
                150.f, 0,
                150.f+HEALTH_OUTER_MARGIN+left_health_take, 150,
                0, 0,
                HEALTH_OUTER_MARGIN+left_health_take, 150,
                health_color);
        graphics.drawImage(health.getSprite(0, 0), 150, 0);

        //right profile
        graphics.drawImage(right.getCharacter().getProfileImage(), 1280-150, 0);
        if (right.getHealth() >= HAPPY_THRESH) {
            graphics.drawImage(hub_eyes.getSubImage(1, 0), 1280-150, 0);
        }
        else if (right.getHealth() >= SAD_THRESH) {
            graphics.drawImage(hub_eyes.getSubImage(0, 0), 1280-150, 0);
        }
        else if (right.getHealth() > 0.f) {
            graphics.drawImage(hub_eyes.getSubImage(2, 0), 1280-150, 0);
        }
        else {
            graphics.drawImage(hub_eyes.getSubImage(3, 0), 1280-150, 0);
        }

        //right health
        float rho = 1280-430-150;
        float right_health_width = 430.f-HEALTH_OUTER_MARGIN-HEALTH_INNER_MARGIN;
        float right_health_donttake = right_health_width - (float)right.getHealth() * right_health_width;
        graphics.drawImage(health.getSprite(0, 4),
                rho+HEALTH_INNER_MARGIN+right_health_donttake, 0,
                1280-150, 150,
                HEALTH_INNER_MARGIN+right_health_donttake, 0,
                430, 150,
                health_color);
        graphics.drawImage(health.getSprite(0, 3), rho, 0);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        p1_control = new KeyboardEntityController(left, Input.KEY_A, Input.KEY_D, Input.KEY_W, Input.KEY_C, Input.KEY_S);
        p2_control = new KeyboardEntityController(right, Input.KEY_J, Input.KEY_L, Input.KEY_I, Input.KEY_PERIOD, Input.KEY_K);
        gameContainer.getInput().addKeyListener(p1_control);
        gameContainer.getInput().addKeyListener(p2_control);
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        gameContainer.getInput().removeKeyListener(p1_control);
        gameContainer.getInput().removeKeyListener(p2_control);
    }

    public void setEntities(Entity left, Entity right) {
        this.left = left;
        this.right = right;
    }

    public Entity getLeft() {
        return left;
    }


    public Entity getRight() {
        return right;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public Arena getArena() {
        return arena;
    }

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public boolean isAcceptingInput() {
        return false;
    }
}
