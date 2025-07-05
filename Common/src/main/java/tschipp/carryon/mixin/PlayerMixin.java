/*
 * GNU Lesser General Public License v3
 * Copyright (C) 2024 Tschipp
 * mrtschipp@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package tschipp.carryon.mixin;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tschipp.carryon.common.carry.CarryOnData;
import tschipp.carryon.common.carry.CarryOnDataManager;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements CarryOnDataManager.ICarrying {

    @Unique
    private static final EntityDataAccessor<CompoundTag> CARRY_DATA_KEY = SynchedEntityData.defineId(Player.class, EntityDataSerializers.COMPOUND_TAG);

    @Override
    public void setCarryOnData(CarryOnData data)
    {
        try {
            java.lang.reflect.Field selectedField = Inventory.class.getDeclaredField("selected");
            selectedField.setAccessible(true);
            data.setSelected((Integer) selectedField.get(this.getInventory()));
        } catch (Exception e) {
            data.setSelected(0); // fallback
        }
        CompoundTag nbt = data.getNbt();
        nbt.putInt("tick", tickCount);
        this.getEntityData().set(CARRY_DATA_KEY, nbt);
    }

    @Override
    public CarryOnData getCarryOnData()
    {
        CompoundTag data = this.getEntityData().get(CARRY_DATA_KEY);
        return new CarryOnData(data.copy());
    }

    @Shadow
    public abstract Inventory getInventory();


    private PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }


    @Inject(method = "defineSynchedData(Lnet/minecraft/network/syncher/SynchedEntityData$Builder;)V", at = @At("RETURN"))
    private void onDefineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(CARRY_DATA_KEY, new CompoundTag());
    }


    @Inject(method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("RETURN"))
    private void onAddAdditionalSaveData(CompoundTag tag, CallbackInfo info)
    {
        CarryOnData carry = CarryOnDataManager.getCarryData((Player)(Object)this);
        tag.put("CarryOnData", carry.getNbt());
    }

    @Inject(method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("RETURN"))
    private void onReadAdditionalSaveData(CompoundTag tag, CallbackInfo info)
    {
        if (tag.contains("CarryOnData")) {
            CarryOnData data = new CarryOnData(tag.getCompound("CarryOnData").orElse(new CompoundTag()));
            setCarryOnData(data);
        }
    }

}
