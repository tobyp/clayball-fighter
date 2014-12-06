package net.tobyp.ld31;

import org.newdawn.slick.Image;

/**
 * Created by tobyp on 12/6/14.
 */
public class Arena {
    private Image background;
    private Image foreground;
    private Image preview;
    private String name;
    private float left_boundary;

    public float getLeftBoundary() {
        return left_boundary;
    }

    public float getRightBoundary() {
        return right_boundary;
    }

    private float right_boundary;

    public String getName() {
        return name;
    }

    public Image getPreview() {
        return preview;
    }

    public Image getForeground() {
        return foreground;
    }

    public Image getBackground() {
        return background;
    }
}
