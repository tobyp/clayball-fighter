package net.tobyp.ld31.ent;

/**
 * Created by tobyp on 12/6/14.
 */
public enum FighterState {
    IDLE(true, true),
    MOVE(true, true),
    JUMP(false, false),
    ATTACK(true, false),
    SLAM(false, false),
    SLIDE(true, false),
    CROUCH(true, true);

    FighterState(boolean can_move, boolean can_jump) {
        this.can_move = can_move;
        this.can_jump = can_jump;
    }

    private boolean can_move;
    private boolean can_jump;

    public boolean canMove() {
        return this.can_move;
    }

    public boolean canJump() {
        return this.can_jump;
    }
}
