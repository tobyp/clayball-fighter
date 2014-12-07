package net.tobyp.ld31;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by tobyp on 12/6/14.
 */
public class Animation {
    Image[] loop;
    int current_frame;
    float delay;
    float accum = 0.f;
    float width; //width of each sprite in polandballs
    float height; //height of each sprite in polandballs
    float center_x; //center of sprite in polandballs from top left
    float center_y; //center of sprite in polandballs from top left
    float tex_w;
    float tex_h;

    /* cx and cy in pixels from top left! */
    public Animation(SpriteSheet sheet, float delay, float width, float height, float cx, float cy) {
        this.delay = delay;
        this.width = width;
        this.height = height;
        loop = new Image[sheet.getVerticalCount()];
        for (int i=0; i<sheet.getVerticalCount(); i++) {
            loop[i]=sheet.getSprite(0, i);
        }
        this.tex_w = loop[0].getWidth();
        this.tex_h = loop[0].getHeight();
        this.center_x = width * (cx / tex_w);
        this.center_y = height * (cy / tex_h);
    }

    public boolean update(float delta) {
        accum += delta;
        current_frame = (current_frame + (int)(accum / delay));
        boolean was_finished = current_frame >= loop.length;
        current_frame %= loop.length;
        accum = accum % delay;
        return was_finished;
    }

    public float getDuration() {
        return delay * loop.length;
    }

    public void draw(Graphics g, float cx, float cy, boolean flipped) {
        float top_left_x = cx - this.center_x;
        float top_left_y = cy - this.center_y;
        g.drawImage(loop[current_frame].getFlippedCopy(flipped, false), top_left_x, top_left_y, top_left_x + width, top_left_y + height, 0, 0, tex_w, tex_h);
    }
}
