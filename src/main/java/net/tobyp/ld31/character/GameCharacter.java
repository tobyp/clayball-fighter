package net.tobyp.ld31.character;

import org.newdawn.slick.SpriteSheet;

/**
 * @author Tom
 * Should define the unique characteristics of all possible characters.
 *  - Speed
 *  - Catchphrases
 *  - Spritesheet
 * (May be a good idea to do this as a class instead of an enum, thinking about it.)
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
