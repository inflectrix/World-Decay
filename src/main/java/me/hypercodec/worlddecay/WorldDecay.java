package main.java.me.hypercodec.worlddecay;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class WorldDecay extends JavaPlugin implements Listener {

    public static Plugin plugin;

    public static Set<Material> special = new HashSet<>();
    public void onEnable() {
        plugin = this;

        this.saveDefaultConfig();

        for(String mat : this.getConfig().getStringList("specblocks")) {
            special.add(Material.valueOf(mat));
        }

        this.getServer().getPluginManager().registerEvents(this, this);

        this.getLogger().info("World Decay v2.0 has been loaded!");


        if(Objects.equals(this.getConfig().getString("mode"), "active")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getWorld().getName().equals("corrupted_lands") || player.getWorld().getName().equals("corrupted_lands_nether") || player.getWorld().getName() == "corrupted_lands_the_end") {
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
    }

    public void onDisable() {
        plugin = null;

        this.getLogger().info("World Decay plugin unloaded");
    }

    public static @Nullable Block getRandNearbyBlock(Location location, int radius) {
        for(int i=0;i<100000;i++) {
            int x = ThreadLocalRandom.current().nextInt((int) location.getX() - radius, (int) location.getX() + radius);
            int y = ThreadLocalRandom.current().nextInt((int) location.getY() - radius, (int) location.getY() + radius);
            int z = ThreadLocalRandom.current().nextInt((int) location.getZ() - radius, (int) location.getZ() + radius);

            if (!Objects.requireNonNull(location.getWorld()).getBlockAt(x, y, z).getBlockData().getMaterial().isAir() && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BEDROCK) {
                if (!plugin.getConfig().getBoolean("protectspecblocks") && plugin.getConfig().getBoolean("effectliquids")) {
                    return location.getWorld().getBlockAt(x, y, z);
                }
                if (plugin.getConfig().getBoolean("protectspecblocks")) {
                    if (!special.contains(location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial())) {
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