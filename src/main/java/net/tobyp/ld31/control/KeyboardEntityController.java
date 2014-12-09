package net.tobyp.ld31.control;

import net.tobyp.ld31.StateFight;
import net.tobyp.ld31.ent.Player;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 * Created by tobyp on 12/7/14.
 */
public class KeyboardEntityController implements KeyListener {
    private Player player;
    private StateFight fight;
    private int key_left;
    private int key_right;
    private int key_jump;
    private int key_attack;
    private int key_crouch;
    private int key_projectile;
    private float direction = 0.f;

    private int keysdown = 0;

    public KeyboardEntityController(StateFight fight, Player player, int key_left, int key_right, int key_jump, int key_attack, int key_crouch, int key_projectile) {
        this.fight = fight;
        this.player = player;
        this.key_left = key_left;
        this.key_right = key_right;
        this.key_jump = key_jump;
        this.key_attack = key_attack;
        this.key_crouch = key_crouch;
        this.key_projectile = key_projectile;
    }


    public void update(float delta) {
        if (player.locked > 0) {
            player.locked -= delta;
            return;
        }

        if (direction == 0.f && Math.abs(player.getVel().x) < 0.05f) {
            player.changeVel(new vec2(-player.getVel().x, 0.f));
        } else if (player.getVel().x < direction * player.getCharacter().getSpeed()) {
            player.changeVel(new vec2(player.getCharacter().getSpeed() * (1 + Math.abs(direction)) * 4.f * delta, 0.f));
        } else if (player.getVel().x > direction * player.getCharacter().getSpeed()) {
            player.changeVel(new vec2(-(player.getCharacter().getSpeed() * (1 + Math.abs(direction)) * 4.f * delta), 0.f));
        }

        if (direction > 0) {
            player.setFlipped(false);
        } else if (direction < 0) {
            player.setFlipped(true);
        }
    }

    @Override
    public void keyPressed(int c, char ch) {
        if (c == this.key_left) {
            direction -= 1.f;
            keysdown++;
        } else if (c == this.key_right) {
            direction += 1.f;
            keysdown++;
        } else if (c == this.key_jump) {
            player.jump();
            keysdown++;
        } else if (c == this.key_attack) {
            player.melee();
            keysdown++;
        } else if (c == this.key_crouch) {
            player.crouch();
            keysdown++;
        }
        else if (c == this.key_projectile) {
            fight.addProjectile(player);
            keysdown++;
        }
        player.setBounce(direction != 0.f);
    }

    @Override
    public void keyReleased(int c, char ch) {
        if (keysdown == 0) return;
        if (c == this.key_left) {
            direction += 1.f;
            keysdown--;
        } else if (c == this.key_right) {
            direction -= 1.f;
            keysdown--;
        } else if (c == this.key_jump) {
            keysdown--;
        } else if (c == this.key_attack) {
            keysdown--;
        } else if (c == this.key_crouch) {
            keysdown--;
        }
        else if (c == this.key_projectile) {
            keysdown--;
        }
        player.setBounce(direction != 0.f);
    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
