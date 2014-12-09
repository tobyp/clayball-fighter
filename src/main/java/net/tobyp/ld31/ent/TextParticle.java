package net.tobyp.ld31.ent;

import net.tobyp.ld31.Ld31;
import net.tobyp.ld31.misc.vec2;
import org.newdawn.slick.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 08/12/2014.
 */
public class TextParticle {

    public static List<TextParticle> list = new ArrayList<>();
    public static void add(vec2 pos, String content, Color color, float end) {
        list.add(new TextParticle(pos, content, color, end));
    }

    public static void update(float delta) {
        for (TextParticle t : new ArrayList<>(list)) {
            t.u(delta);
        }
    }

    public static void render(GameContainer gameContainer, Graphics graphics, Font font) {
        for (TextParticle t : new ArrayList<>(list)) {
            t.r(gameContainer, graphics, font);
        }
    }

    protected vec2 anchor;
    protected vec2 modifier;
    protected String content;
    protected Color color;
    protected float end;
    protected float time = 0;

    private TextParticle(vec2 pos, String content, Color color, float end) {
        this.anchor = pos;
        this.content = content;
        this.color = color;
        this.end = end;
    }

    public void u(float delta) {
        time += delta;

        if (time >= end) {
            list.remove(this);
        }

        float progress = time/end;
        modifier = new vec2(0.f, -progress);
        color.a = 1.f-progress;

        System.out.println(progress);
    }

    public void r(GameContainer gameContainer, Graphics graphics, Font font) {
        graphics.setColor(color);
        graphics.setFont(font);
        graphics.drawString(content, (anchor.x + modifier.x) * 150 + gameContainer.getWidth() * 0.5f, (anchor.y + modifier.y) * 150 + gameContainer.getHeight() * 0.8f);
        graphics.drawLine(anchor.x, anchor.y, anchor.x + modifier.x, anchor.y + modifier.y);
    }
}
