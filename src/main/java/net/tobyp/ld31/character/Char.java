package net.tobyp.ld31.character;

import net.tobyp.ld31.Animation;
import net.tobyp.ld31.ent.FighterState;
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
    protected float speed;

    public Char(String name, SpriteSheet sprite_sheet, float speed) {
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

    public Animation getAnimation(FighterState state) {
        switch (state) {
            case IDLE:
            case MOVE:
            default:
                return new Animation(sprite_sheet, 0.2f, 1.f, 1.f, 128, 128);
        }
    }

    public float getSpeed() {
        return speed;
    }
}
