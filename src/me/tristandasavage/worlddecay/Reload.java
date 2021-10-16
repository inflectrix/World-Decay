package me.tristandasavage.worlddecay;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Keyed;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Reload implements CommandExecutor {
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("World-Decay");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("decayreload")) {
            if(sender.hasPermission("decay.reload")) {
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Config reloaded successfully");
            }
            else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            }
            return true;
        }
        return false;
    }
}
