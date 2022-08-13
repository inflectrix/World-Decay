package me.hypercodec.worlddecay;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class WorldDecay extends JavaPlugin implements Listener {

    public static Plugin plugin;

    public static Set<Material> special = new HashSet<>();

    double version = 2.0;
    public void onEnable() {
        plugin = this;

        this.saveDefaultConfig();

        for(String mat : this.getConfig().getStringList("specblocks")) {
            special.add(Material.valueOf(mat));
        }

        boolean active = Objects.equals(this.getConfig().getString("mode"), "active");

        if(!active) {
            this.getServer().getPluginManager().registerEvents(this, this);
        }

        this.getLogger().info(String.format("World Decay v%s has been loaded!", version));


        if(active) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        int radius = plugin.getConfig().getInt("radius");

                        Block block = getRandNearbyBlock(player.getLocation(), radius);
                        Block airblock = getRandNearbyAir(player.getLocation(), radius);

                        if (block != null && airblock != null) {

                            airblock.setType(block.getType());
                            block.setType(Material.AIR);
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

    @EventHandler
    public void onChunkLoad(@NotNull ChunkLoadEvent event) {
        if(event.isNewChunk()) {
            Location loc = new Location(event.getWorld(), event.getChunk().getX(), 0, event.getChunk().getZ());
            Location spawn = new Location(event.getWorld(), 0, 0, 0);

            this.getLogger().info("distorting chunk");

            for (int i2 = (int) loc.distance(spawn); i2 > 0; i2--) {
                for (int i = 5; i > 0; i--) {
                    int i3 = i2;
                    int i4 = i2 - 5;

                    if (i3 > 256) {
                        i3 = 256;
                    }
                    if (i4 < 0) {
                        i4 = 0;
                    }

                    Block block = getRandNearbyBlock(event.getChunk(), i4, i3);
                    Block airblock = getRandNearbyAir(event.getChunk(), i4, i3);

                    if (block != null && airblock != null) {
                        airblock.setType(block.getType());
                        block.setType(Material.AIR);

                        this.getLogger().info("swapped block");
                        continue;
                    }
                    this.getLogger().info("failed to swap block");
                }
            }
        }
    }


    public static @Nullable Block getRandNearbyBlock(Chunk chunk, int miny, int maxy) {
        for(int i = 0; i < 1000; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, 15);
            int y = ThreadLocalRandom.current().nextInt(miny, maxy);
            int z = ThreadLocalRandom.current().nextInt(0, 15);

            Block block = chunk.getBlock(x, y, z);

            if (!(block.getType().isAir() && block.getType() != Material.BEDROCK && ((!plugin.getConfig().getBoolean("protectspecblocks") && plugin.getConfig().getBoolean("effectliquids")) || (plugin.getConfig().getBoolean("protectspecblocks") && !special.contains(block.getType()))) && ((!plugin.getConfig().getBoolean("effectliquids") && block.getType() != Material.WATER && block.getType() != Material.LAVA) || plugin.getConfig().getBoolean("effectliquids")))) {
                return block;
            }
        }

        return null;
    }
    public static @Nullable Block getRandNearbyBlock(Location loc, int radius) {
        for(int i = 0; i < 1000; i++) {
            int x = ThreadLocalRandom.current().nextInt((int) loc.getX() - radius, (int) loc.getX() + radius);
            int y = ThreadLocalRandom.current().nextInt((int) loc.getY() - radius, (int) loc.getY() + radius);
            int z = ThreadLocalRandom.current().nextInt((int) loc.getZ() - radius, (int) loc.getZ() + radius);

            if (!Objects.requireNonNull(loc.getWorld()).getBlockAt(x, y, z).getType().isAir() && loc.getWorld().getBlockAt(x, y, z).getType() != Material.BEDROCK && ((!plugin.getConfig().getBoolean("protectspecblocks") && plugin.getConfig().getBoolean("effectliquids")) || (plugin.getConfig().getBoolean("protectspecblocks") && !special.contains(loc.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial()))) && ((!plugin.getConfig().getBoolean("effectliquids") && loc.getWorld().getBlockAt(x, y, z).getType() != Material.WATER && loc.getWorld().getBlockAt(x, y, z).getType() != Material.LAVA) || plugin.getConfig().getBoolean("effectliquids"))) {
                return loc.getWorld().getBlockAt(x, y, z);
            }
        }

        return null;
    }

    public static @Nullable Block getRandNearbyAir(Chunk chunk, int miny, int maxy) {
        for(int i = 0; i < 1000; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, 15);
            int y = ThreadLocalRandom.current().nextInt(miny, maxy);
            int z = ThreadLocalRandom.current().nextInt(0, 15);

            Block block = chunk.getBlock(x, y, z);

            if (Objects.requireNonNull(block.getType()).isAir()) {
                return block;
            }
        }

        return null;
    }
    public static @Nullable Block getRandNearbyAir(Location loc, int radius) {
        for(int i = 0; i < 1000; i++) {
            int x = ThreadLocalRandom.current().nextInt((int) loc.getX() - radius, (int) loc.getX() + radius);
            int y = ThreadLocalRandom.current().nextInt((int) loc.getY() - radius, (int) loc.getY() + radius);
            int z = ThreadLocalRandom.current().nextInt((int) loc.getZ() - radius, (int) loc.getZ() + radius);

            if (Objects.requireNonNull(loc.getWorld()).getBlockAt(x, y, z).getType().isAir()) {
                return loc.getWorld().getBlockAt(x, y, z);
            }
        }

        return null;
    }
}