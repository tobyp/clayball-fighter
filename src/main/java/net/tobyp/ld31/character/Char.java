package net.tobyp.ld31.character;

import net.tobyp.ld31.Animation;
import net.tobyp.ld31.ent.FighterState;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * @author Tom
 * Contains the unique characteristics of a playable character/countryball.
 *  - Speed (1 is pixels per millisecond)
 *  - Spritesheet
 *  - Catchphrases (?)
 */
public class Char {
    protected String name;
    protected SpriteSheet sprite_sheet;
    protected Image hub_image;
    protected float speed, jump_power;

    public Char(String name, SpriteSheet sprite_sheet, Image hub_image, float speed, float jump_power) {
        this.name = name;
        this.sprite_sheet = sprite_sheet;
        this.hub_image = hub_image;
        this.speed = speed;
        this.jump_power = jump_power;
    }

    public String getName() {
        return name;
    }

    public SpriteSheet getSpriteSheet() {
        return sprite_sheet;
    }

    public Image getHubImage() {
        return hub_image;
    }

    public Animation getAnimation(FighterState state) {
        switch (state) {
            case IDLE:
            case MOVE:
            default:
                return new Animation(sprite_sheet, 0.15f, 1.5f, 1.5f, 128, 128);
        }
    }

    public float getSpeed() {
        return speed;
    }
    public float getJumpPower() {
        return jump_power;
    }
}
