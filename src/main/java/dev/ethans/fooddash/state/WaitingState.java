package dev.ethans.fooddash.state;

import dev.ethans.fooddash.FoodDash;
import dev.ethans.fooddash.state.base.GameState;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class WaitingState extends GameState {

    public WaitingState() {
        super(FoodDash.getInstance());
    }

    @Override
    public @NotNull Duration getDuration() {
        return Duration.ZERO;
    }

    @Override
    protected void onStart() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    protected void onEnd() {

    }

    @Override
    public boolean isReadyToEnd() {
        return !getPlayers().isEmpty();
    }
}
