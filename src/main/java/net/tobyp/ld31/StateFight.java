package net.tobyp.ld31;

import net.tobyp.ld31.control.KeyboardEntityController;
import net.tobyp.ld31.ent.Entity;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 * Created by tobyp on 12/6/14.
 */
public class StateFight extends BasicGameState implements InputListener {
    public Arena arena;
    public Entity left;
    public Entity right;
    KeyboardEntityController p1_control;
    KeyboardEntityController p2_control;

    private SpriteSheet health;
    private SpriteSheet hub_eyes;
    private SpriteSheet identities;
    private Image special_lights;
    private Image divider;

    private static final float LEFT_HEALTH_BASE = 142;
    private static final float RIGHT_HEALTH_BASE = 142+448+100;

    private static final float HAPPY_THRESH = 0.7f;
    private static final float SAD_THRESH = 0.4f;

    private float game_time;
    private float display_time = 6;

    public StateFight() {

    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        try {
            health = new SpriteSheet(Ld31.class.getResource("/health.png"), 448, 79);
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

        p1_control.update(delta);
        p2_control.update(delta);

        left.update(delta, this);
        right.update(delta, this);

        //Collisions
        if (Math.abs(right.getPos().x - left.getPos().x) < 1.f) {
            if (Math.abs(right.getPos().y - left.getPos().y) < 0.5f) {
                left.knockBack(left.getPos().x - right.getPos().x, 0);
                right.knockBack(right.getPos().x - left.getPos().x, 0);
            }
        }

        game_time += delta;
        display_time = Math.max(0, display_time - delta);

        if (game_time > 3 && left.locked && right.locked) {
            left.locked = false;
            right.locked = false;
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(new Color(1, 1, 1, 1));

        if (display_time > 0) {
            float scaleup = 2;

            Color win_color = new Color(0.5f, 0.5f, 0.5f, 1f);
            Color default_color = new Color(0.f, 0.f, 0.5f, 1f);

            special_lights.setImageColor(1, 1, 1, 0.5f);

            Image flagl = left.getCharacter().getFlag().copy();
            Image flagr = right.getCharacter().getFlag().copy();
            graphics.drawImage(flagl, 0, 0, gameContainer.getWidth()/2, gameContainer.getHeight(), gameContainer.getWidth()/2 - (6-display_time)*250, 0, gameContainer.getWidth() - (6-display_time)*250, gameContainer.getHeight(), new Color(0, 0, 0.5f, 1f));
            graphics.fillRect(0, 0, gameContainer.getWidth()/2, gameContainer.getHeight(), special_lights, special_lights.getWidth() + gameContainer.getWidth()/2 - Math.round(((6-display_time)) * 500), 0);
            graphics.fillRect(0, 0, gameContainer.getWidth()/2, gameContainer.getHeight(), special_lights, special_lights.getWidth() + gameContainer.getWidth()/2 - Math.round(((6-display_time)) * 300), 0);
            Image profile = left.getCharacter().getProfileImage();
            profile.draw(gameContainer.getWidth()/4 - profile.getWidth()/2*scaleup - (6-display_time)*20, gameContainer.getHeight()/2 - profile.getHeight()/2*scaleup, scaleup);
            hub_eyes.getSprite(0, 0).draw(gameContainer.getWidth()/4 - profile.getWidth()/2*scaleup - (6-display_time)*20, gameContainer.getHeight()/2 - profile.getHeight()/2*scaleup, scaleup);
            Image identity = identities.getSprite(0, 0);
            identity.draw(gameContainer.getWidth()/4 - identity.getWidth()/2 + (6-display_time)*20, gameContainer.getHeight() - gameContainer.getHeight()/4);

            graphics.drawImage(flagr.getFlippedCopy(true, false), gameContainer.getWidth()/2, 0, gameContainer.getWidth(), gameContainer.getHeight(), (6-display_time)*250, 0, gameContainer.getWidth()/2 + (6-display_time)*250, gameContainer.getHeight(), new Color(0, 0, 0.5f, 1f));
            graphics.fillRect(gameContainer.getWidth()/2, 0, gameContainer.getWidth()/2, gameContainer.getHeight(), special_lights, Math.round(((6-display_time)) * 500), 0);
            graphics.fillRect(gameContainer.getWidth()/2, 0, gameContainer.getWidth()/2, gameContainer.getHeight(), special_lights, Math.round(((6-display_time)) * 300), 0);
            profile = right.getCharacter().getProfileImage();
            profile.getFlippedCopy(true, false).draw(gameContainer.getWidth() - gameContainer.getWidth()/4 - profile.getWidth()/2*scaleup + (6-display_time)*20, gameContainer.getHeight()/2 - profile.getHeight()/2*scaleup, scaleup);
            hub_eyes.getSprite(0, 0).getFlippedCopy(true, false).draw(gameContainer.getWidth() - gameContainer.getWidth()/4 - profile.getWidth()/2*scaleup + (6-display_time)*20, gameContainer.getHeight()/2 - profile.getHeight()/2*scaleup, scaleup);
            identity = identities.getSprite(0, 1);
            identity.draw(gameContainer.getWidth() - gameContainer.getWidth()/4 - identity.getWidth()/2 - (6-display_time)*20, gameContainer.getHeight() - gameContainer.getHeight()/4);

            graphics.drawImage(divider, gameContainer.getWidth()/2-divider.getWidth()/2, 0);
            return;
        }

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
        int lhw = (int)((448-19-14)*(left.getHealth())); //actual width of red, the 19/14 are the left/right margins
        graphics.drawImage(health.getSprite(0, 0),
                LEFT_HEALTH_BASE+(448-14)-lhw, 20,
                LEFT_HEALTH_BASE+(448-14), 79+20,
                (448-14)-lhw, 0,
                (448-14), 79);
        graphics.drawImage(health.getSprite(0, 3), 142+448+100, 20);
        graphics.drawImage(right.getCharacter().getProfileImage(), 1280-150, 0);
        if (right.getHealth() >= HAPPY_THRESH) {
            graphics.drawImage(hub_eyes.getSubImage(1,0), 1280-150, 0);
        }
        else if (right.getHealth() >= SAD_THRESH) {
            graphics.drawImage(hub_eyes.getSubImage(0,0), 1280-150, 0);
        }
        else if (right.getHealth() > 0.f) {
            graphics.drawImage(hub_eyes.getSubImage(2,0), 1280-150, 0);
        }
        else {
            graphics.drawImage(hub_eyes.getSubImage(3,0), 1280-150, 0);
        }
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
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        p1_control = new KeyboardEntityController(left, Input.KEY_A, Input.KEY_D, Input.KEY_SPACE, Input.KEY_LCONTROL);
        p2_control = new KeyboardEntityController(right, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_NUMPAD0, Input.KEY_RCONTROL);
        gameContainer.getInput().addKeyListener(p1_control);
        gameContainer.getInput().addKeyListener(p2_control);
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        gameContainer.getInput().removeKeyListener(p1_control);
        gameContainer.getInput().removeKeyListener(p2_control);
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
