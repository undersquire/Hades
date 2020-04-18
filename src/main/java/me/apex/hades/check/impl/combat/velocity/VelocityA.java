package me.apex.hades.check.impl.combat.velocity;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.utils.BlockUtils;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;

@CheckInfo(name = "Velocity", type = "A")
public class VelocityA extends Check {

    private double lastDiff;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getType())) {
            if (e.getTimeStamp() - user.getLastVelocity() < 250 && user.getLastVelY() > 0.2) {
                double diff = MathUtils.sigmoid(user.getDeltaY() - user.getLastVelY()) * 0.4D;
                double lastDiff = this.lastDiff;
                this.lastDiff = diff;

                if (BlockUtils.isClimbableBlock(user.getLocation().getBlock())
                        || PlayerUtils.isInWeb(user.getPlayer())
                        || PlayerUtils.isInLiquid(user.getPlayer())
                        || PlayerUtils.blockNearHead(user.getPlayer())) {
                    vl = 0;
                    return;
                }

                if (diff == lastDiff || (diff < 0.179D && lastDiff < 0.179D)) {
                    if (vl++ > 2)
                        flag(user, "diff = " + diff + ", lastDiff = " + lastDiff);
                } else vl = 0;
            }
        }
    }

}
