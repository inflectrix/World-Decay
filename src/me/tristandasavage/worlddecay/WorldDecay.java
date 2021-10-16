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
        this.getCommand("decayreload").setExecutor(new Reload());
        this.logger = this.getLogger();
        this.saveDefaultConfig();
        this.logger.log(Level.INFO, "World Decay v1.0 has been loaded!");

        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Block block = getNearbyBlocks(player.getLocation(), instance.getConfig().getInt("radius"));
                    Block airblock = getNearbyAirBlocks(player.getLocation(), instance.getConfig().getInt("radius"));
                    airblock.setType(block.getBlockData().getMaterial());
                    block.setType(Material.AIR);
                }
            }
        }.runTaskTimer(instance, 20, instance.getConfig().getInt("decaydelay"));
    }

    public void onDisable() {
        instance = null;
        this.logger.log(Level.INFO, "World Decay plugin unloaded");
    }

    public static Block getNearbyBlocks(Location location, int radius) {
        while(true) {
            int x = ThreadLocalRandom.current().nextInt((int)location.getX()-radius, (int)location.getX()+radius);
            int y = ThreadLocalRandom.current().nextInt((int)location.getY()-radius, (int)location.getY()+radius);
            int z = ThreadLocalRandom.current().nextInt((int)location.getZ()-radius, (int)location.getZ()+radius);
            if (!location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial().isAir() && location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial() != Material.BEDROCK) {
                Block block = location.getWorld().getBlockAt(x, y, z);
                return block;
            }
        }
    }

    public static Block getNearbyAirBlocks(Location location, int radius) {
        while(true) {
            Random rand = new Random();
            int x = ThreadLocalRandom.current().nextInt((int)location.getX()-radius, (int)location.getX()+radius);
            int y = ThreadLocalRandom.current().nextInt((int)location.getY()-radius, (int)location.getY()+radius);
            int z = ThreadLocalRandom.current().nextInt((int)location.getZ()-radius, (int)location.getZ()+radius);
            if (location.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial().isAir()) {
                Block block = location.getWorld().getBlockAt(x, y, z);
                return block;
            }
        }
    }
}