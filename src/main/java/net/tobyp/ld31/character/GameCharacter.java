package net.tobyp.ld31.character;

import org.newdawn.slick.SpriteSheet;

/**
 * @author Tom
 * Contains the unique characteristics of a playable character/countryball.
 *  - Speed (1 is pixels per millisecond)
 *  - Spritesheet
 *  - Catchphrases (?)
 */
public class GameCharacter {
    protected String name;
    protected SpriteSheet sprite_sheet;
    protected float speed;

    public GameCharacter(String name, SpriteSheet sprite_sheet, float speed) {
        this.name = name;
        this.sprite_sheet = sprite_sheet;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public SpriteSheet getSpriteSheet() {
        return sprite_sheet;
    }

    public float getSpeed() {
        return speed;
    }
}
