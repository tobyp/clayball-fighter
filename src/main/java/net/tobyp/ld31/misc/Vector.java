package net.tobyp.ld31.misc;

import java.awt.geom.Point2D;

/**
 * Created by Tom on 06/12/2014.
 */
public class Vector {
    private float x, y;
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Vector() {
        this(0, 0);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public Vector sub(Vector compare) {
        return new Vector(x - compare.getX(), y - compare.getY());
    }


    public double distance(Vector compare) {
        return Point2D.distance(x, y, compare.getX(), compare.getY());
    }
    public double distanceSq(Vector compare) {
        return Point2D.distanceSq(x, y, compare.getX(), compare.getY());
    }

}
