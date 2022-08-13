package main.java.me.hypercodec.worlddecay;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

public class WorldDecay extends JavaPlugin {

    private static Plugin plugin;

    public void onEnable() {
        plugin = this;

        this.saveDefaultConfig();

        this.getLogger().info("World Decay v2.0 has been loaded!");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if(player.getWorld().getName().equals("corrupted_lands") || player.getWorld().getName().equals("corrupted_lands_nether") || player.getWorld().getName() == "corrupted_lands_the_end") {
                        if (getRandNearbyBlock(player.getLocation(), plugin.getConfig().getInt("radius")) != null) {
                            Block block = getRandNearbyBlock(player.getLocation(), plugin.getConfig().getInt("radius"));
                            Block airblock = getRandNearbyAir(player.getLocation(), plugin.getConfig().getInt("radius"));

                            assert block != null;
                            assert airblock != null;

                            airblock.setType(block.getBlockData().getMaterial());
                            block.setType(Material.AIR);
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 20, plugin.getConfig().getInt("decaydelay"));
    }

    public void onDisable() {
        plugin = null;
        this.getLogger().log(Level.INFO, "World Decay plugin unloaded");
    }

    public static @Nullable Block getRandNearbyBlock(Location location, int radius) {
        for(int i=0;i<100000;i++) {
            int x = ThreadLocalRandom.current().nextInt((int) location.getX() - radius, (int) location.getX() + radius);
            int y = ThreadLocalRandom.current().nextInt((int) location.getY() - radius, (int) location.getY() + radius);
            int z = ThreadLocalRandom.current().nextInt((int) location.getZ() - radius, (int) location.getZ() + radius);

            if (!Objects.requireNonNull(location.getWorld()).getBlockAt(x, y, z).getBlockData().getMaterial().isAir() && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BEDROCK) {
                if (!plugin.getConfig().getBoolean("protectspecblocks") && plugin.getConfig().getBoolean("effectliquids")) {
                    return location.getWorld().getBlockAt(x, y, z);
                } else if (plugin.getConfig().getBoolean("protectspecblocks")) {
                    if (location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BLACK_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BLUE_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BROWN_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.LIGHT_BLUE_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.CYAN_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.GRAY_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.GREEN_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.LIGHT_GRAY_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.LIME_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.MAGENTA_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.ORANGE_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.PINK_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.RED_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.YELLOW_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.WHITE_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.PURPLE_BED && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.FURNACE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.CHEST && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.ENDER_CHEST && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.CRAFTING_TABLE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.SMITHING_TABLE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.ANVIL && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.CHIPPED_ANVIL && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.DAMAGED_ANVIL && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.GRINDSTONE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.ENCHANTING_TABLE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BOOKSHELF && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.HOPPER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.STONECUTTER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BREWING_STAND && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.DISPENSER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.DROPPER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.CARTOGRAPHY_TABLE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BEEHIVE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BEE_NEST && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.SMOKER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BLAST_FURNACE && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BARREL && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.TRAPPED_CHEST && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.SHULKER_BOX) {
                        if (!plugin.getConfig().getBoolean("effectliquids")) {
                            if (location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.WATER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.LAVA) {
                                return location.getWorld().getBlockAt(x, y, z);
                            }
                        }
                    }
                } else if (!plugin.getConfig().getBoolean("effectliquids")) {
                    if (location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.WATER && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.LAVA) {
                        return location.getWorld().getBlockAt(x, y, z);
                    }
                }
            }
        }
        return null;
    }

    public static @Nullable Block getRandNearbyAir(Location location, int radius) {
        for(int i=0;i<100000;i++) {
            int x = ThreadLocalRandom.current().nextInt((int)location.getX()-radius, (int)location.getX()+radius);
            int y = ThreadLocalRandom.current().nextInt((int)location.getY()-radius, (int)location.getY()+radius);
            int z = ThreadLocalRandom.current().nextInt((int)location.getZ()-radius, (int)location.getZ()+radius);
            if (Objects.requireNonNull(location.getWorld()).getBlockAt(x, y, z).getBlockData().getMaterial().isAir()) {
                return location.getWorld().getBlockAt(x, y, z);
            }
        }
        return null;
    }
}