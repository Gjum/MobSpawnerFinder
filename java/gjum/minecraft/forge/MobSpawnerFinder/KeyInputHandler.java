/*
 * Copyright (C) 2015  Gjum <code.gjum+MobSpawnerFinder@gmail.com>
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc.,
 *     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package gjum.minecraft.forge.MobSpawnerFinder;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        Minecraft mc = Minecraft.getMinecraft();
        if (KeyBindings.listSpawnersKeyBinding.isPressed()) {
            // list all currently loaded spawners
            for (Object o : mc.theWorld.loadedTileEntityList) {
                if (o.getClass() == TileEntityMobSpawner.class) {
                    final TileEntityMobSpawner spawnerTileEntity = (TileEntityMobSpawner) o;
                    final BlockPos spawnerPos = spawnerTileEntity.getPos();
                    NBTTagCompound nbt = new NBTTagCompound();
                    spawnerTileEntity.writeToNBT(nbt);
                    final String entityId = nbt.getString("EntityId");
                    mc.thePlayer.addChatMessage(new ChatComponentText(entityId + "-Spawner at " + spawnerPos));
                }
            }
        }
    }

}
