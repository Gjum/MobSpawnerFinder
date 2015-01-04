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

import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChunkWatchHandler {

    /**
     * When the world loads, clear list of known MobSpawners,
     * so when loading the same world again, the MobSpawners show up again.
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load e) {
        MobSpawnerFinder.foundSpawners.clear();
    }

    /**
     * When a new chunk gets loaded, search for MobSpawners
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load e) {
        MobSpawnerFinder.findSpawners(false);
    }

}
