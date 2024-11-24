package cn.ksmcbrigade.ccs.mixin;

import cn.ksmcbrigade.ccs.utils.PoweredVehicleEntityAccess;
import com.mrcrayfish.vehicle.entity.PoweredVehicleEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PoweredVehicleEntity.class)
public class PoweredVehicleEntityMixin implements PoweredVehicleEntityAccess {

    @Unique
    private Entity lastOwner;

    @Override
    public Entity getLastOwner() {
        return this.lastOwner;
    }

    @Override
    public void setLastOwner(Entity owner) {
        this.lastOwner = owner;
    }

    @Inject(method = "interact",at = @At("RETURN"))
    public void interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResultType> cir){
        this.lastOwner = player;
    }
}
