package me.purplex.packetevents.utils.nms_entityfinder;

import net.minecraft.server.v1_13_R1.WorldServer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_13_R1.CraftWorld;
import org.bukkit.entity.Entity;

class EntityFinder_1_13 {
    public static Entity getEntityById(final World world, final int id) {
        WorldServer worldServer = ((CraftWorld) world).getHandle();
        return worldServer.getEntity(id).getBukkitEntity();
    }

}