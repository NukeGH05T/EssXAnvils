package me.essxanvils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.essxanvils.EssXAnvils.plugin;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String prefix = plugin.getConfig().getString("prefix");
        if (args.length != 1) return true;
        if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("essxanvils.reload")) {
            try {
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GOLD + prefix + " Configuration reloaded successfully.");
            } catch (Exception ex) {
                System.out.println(ex);
                sender.sendMessage(ChatColor.DARK_RED + prefix + " Failed to reload config! Check console for details.");
                return true;
            }

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + prefix + " Unknown Command!");
            return true;
        }
    }
}
