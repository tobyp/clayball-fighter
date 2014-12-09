package net.tobyp.ld31.character;

import net.tobyp.ld31.Animation;
import net.tobyp.ld31.Ld31;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author Tom
 * Contains the unique characteristics of a playable character/countryball.
 *  - Speed (1 is pixels per millisecond)
 *  - Spritesheet
 *  - Catchphrases (?)
 */
public class Char {
    static final float SPEED = 4.f;
    static final float JUMP_POWER = 6f;

    protected String name;
    protected SpriteSheet sprite_sheet, attack_sheet, projectile_sheet;
    protected Image profile_image, flag_image;
    protected float speed, jump_power;

    public Char(String name, SpriteSheet sprite_sheet, SpriteSheet attack_sheet, SpriteSheet projectile_sheet, Image hub_image, Image flag_image, float speed, float jump_power) {
        this.name = name;
        this.sprite_sheet = sprite_sheet;
        this.attack_sheet = attack_sheet;
        this.profile_image = hub_image;
        this.projectile_sheet = projectile_sheet;
        this.flag_image = flag_image;
        this.speed = speed;
        this.jump_power = jump_power;
    }

    public static Char loadCharacter(String id, String name) throws SlickException {
        SpriteSheet idle = new SpriteSheet(new Image(Ld31.class.getResourceAsStream("/"+id+"/idle.png"), id+"_idle", false), 256, 256);
        SpriteSheet attack = new SpriteSheet(new Image(Ld31.class.getResourceAsStream("/"+id+"/attack.png"), id+"_attack", false), 256, 256);
        SpriteSheet projectile = new SpriteSheet(new Image(Ld31.class.getResourceAsStream("/"+id+"/projectile.png"), id+"_projectile", false), 64, 64);
        Image hub = new Image(Ld31.class.getResourceAsStream("/"+id+"/hub.png"), id+"_hub", false);
        Image flag = new Image(Ld31.class.getResourceAsStream("/"+id+"/flag.png"), id+"_flag", false);
        return new Char(name, idle, attack, projectile, hub, flag, SPEED, JUMP_POWER);
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
        return new Animation(attack_sheet, 0.05f, 1.5f, 1.5f, 128, 128);
    }

    public Animation getProjectileAnimation() {
        return new Animation(projectile_sheet, 0.05f, .5f, .5f, 32, 32);
    }

    public float getSpeed() {
        return speed;
    }
    public float getJumpPower() {
        return jump_power;
    }
}
