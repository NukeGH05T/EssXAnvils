package me.essxanvils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import static me.essxanvils.EssXAnvils.plugin;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String prefix = plugin.getConfig().getString("prefix");
        if (args.length <=0) return true;
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

            //        0     1           2     3
            // /exa give NukeGH05T sharpness 7
        } else if (args[0].equalsIgnoreCase("give") && sender.hasPermission("essxanvils.give")) {
            if (!(sender instanceof Player)) return true;

            Player player = Bukkit.getPlayer(args[1]);

            if (player == null) {
                sender.sendMessage(ChatColor.RED + "Player Not found");
                return true;
            }

            if (args.length < 4) {
                sender.sendMessage(ChatColor.RED + "Invalid Usage. Try " + ChatColor.GRAY + "/exa give <name> <enchantment> <level>");
                return true;
            }

            Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(args[2].toLowerCase()));
            if (enchantment == null) {
                player.sendMessage(ChatColor.RED + "Invalid enchantment: " + args[2]);
                return true;
            }

            int level;
            try {
                level = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Invalid level: " + args[3]);
                return true;
            }

            ItemStack enchantedBook = createEnchantedBook(enchantment, level);
            player.getInventory().addItem(enchantedBook);
            player.sendMessage(ChatColor.GREEN + "You have received an enchanted book!");
//            String playerName = args[1];
//            String enchantmentName = args[2].toLowerCase();
//            Integer level = Integer.valueOf(args[3]);
//
//            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),
//                    // /minecraft:give @p enchanted_book{StoredEnchantments:[{id:"minecraft:unbreaking",lvl:8s}]}
//                    "/give " + playerName + " enchanted_book{StoredEnchantments:[{id:\"minecraft:" + enchantmentName + "\"" + ",lvl:" + level + "s}]}");
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + prefix + " Unknown Command!");
            return true;
        }
    }

    private ItemStack createEnchantedBook(Enchantment enchantment, int level) {
        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
        meta.addStoredEnchant(enchantment, level, true);
        enchantedBook.setItemMeta(meta);
        return enchantedBook;
    }

    public boolean hasEmptySlot(Player player) {
        ItemStack[] inventoryContents = player.getInventory().getContents();

        for (ItemStack item : inventoryContents) {
            if (item == null) {
                return true; // Found an empty slot
            }
        }

        return false; // No empty slots found
    }

    public void spawnItemOnPlayerLocation(Player player, ItemStack itemStack) {
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();

        Item droppedItem = player.getWorld().dropItem(player.getLocation(), itemStack);

        droppedItem.teleport(player.getLocation());
        droppedItem.setPickupDelay(20); //Delay in ticks
    }
}
