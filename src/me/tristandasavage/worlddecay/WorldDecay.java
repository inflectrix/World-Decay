package me.tristandasavage.worlddecay;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorldDecay extends JavaPlugin {

    private static WorldDecay instance;
    private Logger logger;

    public WorldDecay() {
    }

    public static WorldDecay getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        this.logger = this.getLogger();
        this.saveDefaultConfig();
        this.logger.log(Level.INFO, "World Decay v1.3 has been loaded!");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if(getRandNearbyBlock(player.getLocation(), instance.getConfig().getInt("radius")) != null) {
                        Block block = getRandNearbyBlock(player.getLocation(), instance.getConfig().getInt("radius"));
                        Block airblock = getRandNearbyAir(player.getLocation(), instance.getConfig().getInt("radius"));
                        airblock.setType(block.getBlockData().getMaterial());
                        block.setType(Material.AIR);
                    }
                }
            }
        }.runTaskTimer(instance, 20, instance.getConfig().getInt("decaydelay"));
    }

    public void onDisable() {
        instance = null;
        this.logger.log(Level.INFO, "World Decay plugin unloaded");
    }

    public static Block getRandNearbyBlock(Location location, int radius) {
        for(int i=0;i<100000;i++) {
            int x = ThreadLocalRandom.current().nextInt((int) location.getX() - radius, (int) location.getX() + radius);
            int y = ThreadLocalRandom.current().nextInt((int) location.getY() - radius, (int) location.getY() + radius);
            int z = ThreadLocalRandom.current().nextInt((int) location.getZ() - radius, (int) location.getZ() + radius);
            if (!location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial().isAir() && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BEDROCK) {
                if (!instance.getConfig().getBoolean("protectspecblocks") && instance.getConfig().getBoolean("effectliquids")) {
                    return location.getWorld().getBlockAt(x, y, z);
                } else if (instance.getConfig().getBoolean("protectspecblocks")) {
                    if (location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BLACK_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BLUE_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BROWN_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.LIGHT_BLUE_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.CYAN_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.GRAY_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.GREEN_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.LIGHT_GRAY_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.LIME_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.MAGENTA_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.ORANGE_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.PINK_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.RED_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.YELLOW_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.WHITE_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.PURPLE_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.FURNACE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.CHEST && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.ENDER_CHEST && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.CRAFTING_TABLE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.SMITHING_TABLE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.ANVIL && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.CHIPPED_ANVIL && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.DAMAGED_ANVIL && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.GRINDSTONE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.ENCHANTING_TABLE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BOOKSHELF && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.HOPPER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.STONECUTTER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BREWING_STAND && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.DISPENSER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.DROPPER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.CARTOGRAPHY_TABLE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BEEHIVE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BEE_NEST && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.SMOKER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BLAST_FURNACE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BARREL && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.TRAPPED_CHEST && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.SHULKER_BOX) {
                        if (!instance.getConfig().getBoolean("effectliquids")) {
                            if (location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.WATER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.LAVA) {
                                return location.getWorld().getBlockAt(x, y, z);
                            }
                        }
                    }
                } else if (!instance.getConfig().getBoolean("effectliquids")) {
                    if (location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.WATER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.LAVA) {
                        return location.getWorld().getBlockAt(x, y, z);
                    }
                }
            }
        }
        return null;
    }

    public static Block getRandNearbyAir(Location location, int radius) {
        for(int i=0;i<100000;i++) {
            Random rand = new Random();
            int x = ThreadLocalRandom.current().nextInt((int)location.getX()-radius, (int)location.getX()+radius);
            int y = ThreadLocalRandom.current().nextInt((int)location.getY()-radius, (int)location.getY()+radius);
            int z = ThreadLocalRandom.current().nextInt((int)location.getZ()-radius, (int)location.getZ()+radius);
            if (location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial().isAir()) {
                Block block = location.getWorld().getBlockAt(x, y, z);
                return block;
            }
        }
        return null;
    }
}