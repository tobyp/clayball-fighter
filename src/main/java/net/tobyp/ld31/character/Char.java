package net.tobyp.ld31.character;

import net.tobyp.ld31.Animation;
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
    protected SpriteSheet sprite_sheet, attack_sheet;
    protected Image profile_image, flag_image;
    protected float speed, jump_power;

    public Char(String name, SpriteSheet sprite_sheet, SpriteSheet attack_sheet, Image hub_image, Image flag_image, float speed, float jump_power) {
        this.name = name;
        this.sprite_sheet = sprite_sheet;
        this.attack_sheet = attack_sheet;
        this.profile_image = hub_image;
        this.flag_image = flag_image;
        this.speed = speed;
        this.jump_power = jump_power;
    }

    public String getName() {
        return name;
    }

    public SpriteSheet getSpriteSheet() {
        return sprite_sheet;
    }

    public Image getProfileImage() {
        return profile_image;
    }

    public Image getFlag() { return flag_image; }

    public Animation getIdleAnimation() {
        return new Animation(sprite_sheet, 0.15f, 1.5f, 1.5f, 128, 128);
    }

    public Animation getAttackAnimation() {
        return new Animation(attack_sheet, 0.07f, 1.5f, 1.5f, 128, 128);
    }

    public float getSpeed() {
        return speed;
    }
    public float getJumpPower() {
        return jump_power;
    }
}
