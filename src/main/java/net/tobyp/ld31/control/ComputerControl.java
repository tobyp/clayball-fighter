package net.tobyp.ld31.control;

import net.tobyp.ld31.ent.LivingEntity;
import org.newdawn.slick.Input;

import java.util.List;

/**
 * @author Tom
 * Speed is currently being defined as Pixels per Millisecond. Can be modified mathmatically by entity classes.
 */
public class ComputerControl extends ControlMethod {
    private List<LivingEntity> targets;
    private LivingEntity current_target;

    public ComputerControl(Input input, List<LivingEntity> targets) {
        super(input);
        this.targets = targets;
    }

    @Override
    public float getXSpeed() {
        return 0;
    }

    @Override
    public boolean getJumping() {
        return false;
    }

    @Override
    public boolean getCrouching() {
        return false;
    }

    @Override
    public boolean getAttacking() {
        return false;
    }

    private void selectTarget() {
        if (this.current_target.getHealth() <= 0) {

        }
    }
}
