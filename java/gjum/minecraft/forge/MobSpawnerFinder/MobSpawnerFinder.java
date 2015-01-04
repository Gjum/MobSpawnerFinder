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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

@Mod(modid = MobSpawnerFinder.MODID, name = MobSpawnerFinder.MODNAME, version = MobSpawnerFinder.VERSION, canBeDeactivated = true)
public class MobSpawnerFinder {

    public static final String MODID = "MobSpawnerFinder";
    public static final String MODNAME = "MobSpawner finder";
    public static final String VERSION = "0.1";

    static Set<BlockPos> foundSpawners = new HashSet<BlockPos>();

    @SideOnly(Side.CLIENT)
    @EventHandler
    public void init(FMLInitializationEvent e) {
        // mob spawner finder
        MinecraftForge.EVENT_BUS.register(new ChunkWatchHandler());

        // key bindings
        FMLCommonHandler.instance().bus().register(new KeyInputHandler());
    }

    /**
     * search in all loaded TileEntities for MobSpawners,
     * when a new MobSpawner gets found, display its type and coordinates in chat.
     * @param showKnown show already found spawners
     */
    public static void findSpawners(boolean showKnown) {
        final Minecraft mc = Minecraft.getMinecraft();
        for (Object o : mc.theWorld.loadedTileEntityList) {
            if (o.getClass() == TileEntityMobSpawner.class) {
                final TileEntityMobSpawner spawnerTileEntity = (TileEntityMobSpawner) o;
                final BlockPos spawnerPos = spawnerTileEntity.getPos();
                if (showKnown || !foundSpawners.contains(spawnerPos)) {
                    if (!showKnown) foundSpawners.add(spawnerPos);

                    // tell player about newly found spawner
                    NBTTagCompound nbt = new NBTTagCompound();
                    spawnerTileEntity.writeToNBT(nbt);
                    final String entityId = nbt.getString("EntityId");
                    final ChatComponentText message = new ChatComponentText(String.format(
                            "%d %3d %d %s spawner",
                            spawnerPos.getX(), spawnerPos.getY(), spawnerPos.getZ(), entityId));
                    mc.thePlayer.addChatMessage(message);
                }
            }
        }
    }

}
