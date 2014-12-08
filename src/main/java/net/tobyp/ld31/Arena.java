package net.tobyp.ld31;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Created by tobyp on 12/6/14.
 */
public class Arena {
    private Image background;
    private String name;
    private float left_boundary; //left boundary, in units of balls, from the center (should be negative)
    private float right_boundary; //right boundary, in units of balls, from the center (should be positive)
    private float ball_width; //width of whole image, in units of balls
    private float center; //0.0 is left, 1.0 is right of image

    public float getGround() {
        return ground;
    }

    public float getCenter() {
        return center;
    }

    private float ground; //0.0 is top, 1.0 is bottom of image

    public Arena(String name, Image background, float ball_width, float ground, float center, float left_boundary, float right_boundary) {
        this.name = name;
        this.background = background;
        this.ball_width = ball_width;
        this.center = center;
        this.ground = ground;
        this.left_boundary = left_boundary;
        this.right_boundary = right_boundary;
    }

    public static Arena loadArena(String id, String name) throws SlickException {
        Image bg = new Image(Ld31.class.getResourceAsStream("/"+id+"/arena.png"), id+"_arena", false);
        return new Arena(name, bg, 9.f, 0.8f, 0.5f, -3.5f, 3.5f);
    }

    public void applyTransform(Graphics g, GameContainer gc) {
        float s = gc.getWidth()/ball_width;
        g.scale(s, s);
        g.translate(center*ball_width, ground*ball_width*9.f/16.f); //assuming from top left
    }

    public float getLeftBoundary() {
        return left_boundary;
    }

    public float getRightBoundary() {
        return right_boundary;
    }

    public String getName() {
        return name;
    }

    public Image getBackground() {
        return background;
    }
}
