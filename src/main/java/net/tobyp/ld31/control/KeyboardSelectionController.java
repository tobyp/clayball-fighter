package net.tobyp.ld31.control;

import net.tobyp.ld31.StateSelection;
import net.tobyp.ld31.misc.GameSound;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 * Created by tobyp on 12/7/14.
 */
public class KeyboardSelectionController implements KeyListener {
    private int selection_index = 0;
    private boolean done = false;
    private StateSelection sel;
    private int key_left;
    private int key_right;
    private int key_up;
    private int key_down;
    private int key_accept;

    public KeyboardSelectionController(StateSelection sel, int key_left, int key_right, int key_up, int key_down, int key_accept) {
        this.sel = sel;
        this.key_left = key_left;
        this.key_right = key_right;
        this.key_up = key_up;
        this.key_down = key_down;
        this.key_accept = key_accept;
    }

    public int getSelectionIndex() {
        return selection_index;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public void keyPressed(int c, char ch) {
        if (c == key_left && selection_index != 0) {
            selection_index--;
            GameSound.SELECT.play(1, 1);
            done = false;
        }
        else if (c == key_right && selection_index + 1 < sel.getCharCount()) {
            selection_index++;
            GameSound.SELECT.play(1, 1);
            done = false;
        }
        else if (c == key_up && selection_index >= sel.getLineWidth()) {
            selection_index-=sel.getLineWidth();
            GameSound.SELECT.play(1, 1);
            done = false;
        }
        else if (c == key_down && selection_index + sel.getLineWidth() < sel.getCharCount()) {
            selection_index+=sel.getLineWidth();
            GameSound.SELECT.play(1, 1);
            done = false;
        }
        else if (c == key_accept) {
            done = !done;
            if (done) {
                System.out.println("Selected "+selection_index);
            }
            GameSound.CONFIRM.play(1, 1);
        }
    }

    @Override
    public void keyReleased(int i, char c) {

    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
