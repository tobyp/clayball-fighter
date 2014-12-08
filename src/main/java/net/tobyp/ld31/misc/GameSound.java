package net.tobyp.ld31.misc;

import net.tobyp.ld31.Ld31;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.Log;

import java.util.Random;

/**
 * Created by Tom on 07/12/2014.
 */
public enum GameSound {

    JUMP ("jump"),
    MELEE ("melee"),
    CONFIRM ("confirm"),
    SELECT ("select");

    private Random random = new Random();
    private String[] sound;

    GameSound(String... sound) {
        this.sound = sound;
    }

    public void play(float pitch, float volume) {
        try {
            String file = sound[random.nextInt(sound.length)];
            Sound s = new Sound(Ld31.class.getResource("/sound/" + file + ".ogg"));
            s.play(pitch, volume);
        }catch (Exception e) {
            Log.info("Couldn't play sound " + name() + " - " + e.getMessage());
        }
    }
}
