package net.tobyp.ld31.misc;

/**
 * Created by tobyp on 8/23/14.
 */
public class vec2 {
    public final float x, y;

    public vec2() {
        this.x = 0.f;
        this.y = 0.f;
    }

    public vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public vec2(vec2 o) {
        this.x = o.x;
        this.y = o.y;
    }

    public vec2 add(vec2 o) {
        return new vec2(this.x + o.x, this.y + o.y);
    }

    public vec2 add(float x, float y) {
        return new vec2(this.x + x, this.y + y);
    }

    public vec2 sub(vec2 o) {
        return new vec2(this.x - o.x, this.y - o.y);
    }

    public vec2 sub(float x, float y) {
        return new vec2(this.x - x, this.y - y);
    }

    public vec2 mul(float f) {
        return new vec2(x*f, y*f);
    }

    public vec2 div(float f) {
        return new vec2(x/f, y/f);
    }

    public vec2 negate() {
        return new vec2(-this.x, -this.y);
    }

    public vec2 floor() {
        return new vec2((float)Math.floor(this.x), (float)Math.floor(this.y));
    }

    public float length() {
        return (float)Math.sqrt(this.x*this.x +this.y*this.y);
    }

    public vec2 unit() {
        float l = length();
        if (l == 0.f) {
            return ZERO;
        }
        return new vec2(this.x/l, this.y/l);
    }

    public int getFloorX() {
        return (int)Math.floor(this.x);
    }

    public int getFloorY() {
        return (int)Math.floor(this.y);
    }

    public float getDistance(vec2 point) {
        float min_x, min_y, max_x, max_y;
        if (this.x > point.x) min_x = point.x; else min_x = this.x;
        if (this.y > point.y) min_y = point.y; else min_y = this.y;
        if (this.x < point.x) max_x = point.x; else max_x = this.x;
        if (this.y < point.y) max_y = point.y; else max_y = this.y;

        return (max_x - min_x) + (max_y - min_y);
    }

    public float getRot() {
        return (float)Math.atan2(this.y, this.x) - (float)(Math.PI/2.f);
    }

    public vec2 withX(float x) {
        return new vec2(x, this.y);
    }

    public vec2 withY(float y) {
        return new vec2(this.x, y);
    }

    public boolean isZero() {
        return x == 0.f && y == 0.f;
    }

    @Override
    public String toString() {
        return "vec2("+Float.toString(x)+"; "+Float.toString(y)+")";
    }

    public static final vec2 ZERO = new vec2(0.f, 0.f);
    public static final vec2 UP = new vec2(0.f, -1.f);
    public static final vec2 DOWN = new vec2(0.f, 1.f);
    public static final vec2 LEFT = new vec2(-1.f, 0.f);
    public static final vec2 RIGHT = new vec2(1.f, 0.f);

}