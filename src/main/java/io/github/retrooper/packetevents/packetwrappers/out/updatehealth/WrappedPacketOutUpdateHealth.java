package io.github.retrooper.packetevents.packetwrappers.out.updatehealth;

import io.github.retrooper.packetevents.packetwrappers.Sendable;
import io.github.retrooper.packetevents.packetwrappers.api.WrappedPacket;
import io.github.retrooper.packetevents.reflectionutils.Reflection;
import io.github.retrooper.packetevents.utils.NMSUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class WrappedPacketOutUpdateHealth extends WrappedPacket implements Sendable {
    private float health, foodSaturation;
    private int food;
    public WrappedPacketOutUpdateHealth(final Object packet) {
        super(packet);
    }

    /**
     * See https://wiki.vg/Protocol#Update_Health
     * @param health
     * @param food
     * @param foodSaturation
     */
    public WrappedPacketOutUpdateHealth(final float health, final int food, final float foodSaturation) {
        super(null);
        this.health= health;
        this.food = food;
        this.foodSaturation = foodSaturation;
    }

    @Override
    protected void setup() {
        this.health = healthAccessor.get(packet);
        this.foodSaturation = foodSaturationAccessor.get(packet);
        this.food = foodAccessor.get(packet);
    }

    public float getHealth() {
        return health;
    }

    public float getFoodSaturation() {
        return foodSaturation;
    }

    public int getFood() {
        return food;
    }

    @Override
    public Object asNMSPacket() {
        try {
            return packetConstructor.newInstance(health, food, foodSaturation);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Class<?> packetClass;

    private static Constructor<?> packetConstructor;

    private static final Reflection.FieldAccessor<Float> healthAccessor, foodSaturationAccessor;

    private static final Reflection.FieldAccessor<Integer> foodAccessor;

    static {
        try {
            packetClass = NMSUtils.getNMSClass("PacketPlayOutUpdateHealth");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            packetConstructor = packetClass.getConstructor(float.class, int.class, float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        healthAccessor = Reflection.getField(packetClass, float.class, 0);
        foodSaturationAccessor = Reflection.getField(packetClass, float.class, 1);
        foodAccessor = Reflection.getField(packetClass, int.class, 0);
    }
}
