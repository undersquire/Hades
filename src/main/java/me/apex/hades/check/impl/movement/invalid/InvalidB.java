package me.apex.hades.check.impl.movement.invalid;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import org.bukkit.Difficulty;

@CheckInfo(name = "Invalid", type = "B")
public class InvalidB extends Check {

    private int ticks;

    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof FlyingEvent) {
            if (user.getDeltaY() > 0.0) {
                ticks++;
            } else if (user.getDeltaY() < 0.0) {
                if (ticks > 0 && ticks < 5 && !user.isOnGround() && elapsed(user.getTick(), user.getUnderBlockTick()) > 40 && elapsed(user.getTick(), user.getVelocityTick()) < 100) {
                    flag(user, "too few air ticks, t: " + ticks);
                }
                ticks = 0;
                if (user.getLocation().getWorld().getDifficulty() == Difficulty.PEACEFUL) return;
                if (user.isSprinting() && user.getPlayer().getFoodLevel() < 6) {
                    flag(user, "sprinting while hunger levels are low.");
                }
            }
        }
    }

}
