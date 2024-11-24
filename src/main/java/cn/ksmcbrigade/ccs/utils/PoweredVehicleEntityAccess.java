package cn.ksmcbrigade.ccs.utils;

import net.minecraft.entity.Entity;

public interface PoweredVehicleEntityAccess {
    Entity getLastOwner();
    void setLastOwner(Entity owner);
}
