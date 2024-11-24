package cn.ksmcbrigade.ccs.mixin;

import cn.ksmcbrigade.ccs.Config;
import cn.ksmcbrigade.ccs.utils.PoweredVehicleEntityAccess;
import com.mrcrayfish.vehicle.entity.PlaneEntity;
import com.mrcrayfish.vehicle.entity.PoweredVehicleEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin extends CapabilityProvider<Entity> {

    protected EntityMixin(Class<Entity> baseClass) {
        super(baseClass);
    }

    @Shadow public abstract boolean hurt(DamageSource p_70097_1_, float p_70097_2_);

    @Shadow public abstract void push(double p_70024_1_, double p_70024_3_, double p_70024_5_);

    @Shadow public World level;

    @Shadow public abstract float getBbWidth();

    @Shadow public abstract ITextComponent getName();

    @Shadow private Vector3d position;

    @Inject(method = "tick",at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        Entity self = (Entity) ((Object)this);
        if(self instanceof PoweredVehicleEntity) {
            if(self.getControllingPassenger()==null && ((PoweredVehicleEntityAccess)self).getLastOwner()!=null){
                new Thread(()->{
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ((PoweredVehicleEntityAccess)self).setLastOwner(null);
                }).start();
            }
            return;
        }
        if((self instanceof PlayerEntity) && (((PlayerEntity) self).isCreative() || self.isSpectator())) return;
        if(self.getVehicle()!=null && (self.getVehicle() instanceof PoweredVehicleEntity)) return;
        boolean set = false;
        for (PoweredVehicleEntity entitiesOfClass : this.level.getEntitiesOfClass(PoweredVehicleEntity.class, new AxisAlignedBB(this.position, this.position).inflate(this.getBbWidth()))) {
            if(!(entitiesOfClass instanceof PlaneEntity) && ((PoweredVehicleEntityAccess)entitiesOfClass).getLastOwner()!=self){
                set = true;
                break;
            }
        }
        if(set){
            this.hurt(DamageSource.ANVIL, Config.HEAL.get());
            float push = Config.MOVE.get();
            this.push(push,push,push);
            if(self instanceof PlayerEntity){
                if(Config.ENABLED_MESSAGE.get() && !Config.MESSAGE.get().isEmpty() && this.level.getServer()!=null){
                    this.level.getServer().getPlayerList().broadcastMessage(new TranslationTextComponent("chat.type.text", this.getName(),Config.MESSAGE.get()), ChatType.CHAT,self.getUUID());
                }
            }
        }
    }
}
