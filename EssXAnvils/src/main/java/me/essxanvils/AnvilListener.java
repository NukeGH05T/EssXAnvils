package me.essxanvils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

import static me.essxanvils.EssXAnvils.plugin;

public class AnvilListener implements Listener {
    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent e) {
        ItemStack firstItem = e.getInventory().getItem(0);
        ItemStack secondItem = e.getInventory().getItem(1);

        //Result Update on enchant
        if (firstItem != null && firstItem.getType() != Material.AIR &&
                secondItem != null && secondItem.getType() == Material.ENCHANTED_BOOK) {
            AnvilInventory anvilInv = e.getInventory();

            if (secondItem.getType().name().equals("ENCHANTED_BOOK")) {

                ItemStack eBook = new ItemStack(secondItem);
                ItemStack sword = new ItemStack(firstItem);

                if (eBook.getType() == Material.ENCHANTED_BOOK && eBook.hasItemMeta()) {
                    EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) eBook.getItemMeta();
                    Map<Enchantment, Integer> enchantments = bookMeta.getStoredEnchants();

                    if (plugin.getConfig().getBoolean("force-roman-numerals")) {
                        sword.setItemMeta(null);
                    }

                    for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                        Enchantment enchantment = entry.getKey();
                        int level = entry.getValue();

                        if (enchantment.canEnchantItem(sword)) {
                            sword.addUnsafeEnchantment(enchantment, level);
                        }
                    }
                }
                e.setResult(sword);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            if (e.getView().getType() == InventoryType.ANVIL) {
                AnvilInventory anvilInv = (AnvilInventory) e.getInventory();
                int slot = e.getRawSlot();


                ItemStack firstItem = anvilInv.getItem(0);
                ItemStack secondItem = anvilInv.getItem(1);

                if (firstItem != null && firstItem.getType() != Material.AIR &&
                        secondItem != null && secondItem.getType() == Material.ENCHANTED_BOOK) {
                    ItemStack[] itemsInAnvil = anvilInv.getContents();

                    if (slot == 2) {

                        //ItemStack[] itemsInAnvil = anvilInv.getContents();
                        if (itemsInAnvil[0].equals(null) || itemsInAnvil[1].equals(null)) return;

                        if (itemsInAnvil[1].getType().name().equals("ENCHANTED_BOOK")) {

                            ItemStack eBook = new ItemStack(itemsInAnvil[1]);
                            ItemStack sword = new ItemStack(itemsInAnvil[0]);

                            if (eBook.getType() == Material.ENCHANTED_BOOK && eBook.hasItemMeta()) {
                                EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) eBook.getItemMeta();
                                Map<Enchantment, Integer> enchantments = bookMeta.getStoredEnchants();

                                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                                    Enchantment enchantment = entry.getKey();
                                    int level = entry.getValue();

                                    if (enchantment.canEnchantItem(sword)) {
                                        sword.addUnsafeEnchantment(enchantment, level);
                                    } else {

                                        //Giving back extra enchants
                                        if (EssXAnvils.plugin.getConfig().getBoolean("return-unused-enchant-book")) {
                                            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                                            book.addUnsafeEnchantment(enchantment, level);
                                            e.getWhoClicked().getInventory().addItem(book);
                                        }

                                    }
                                }

                            }

                            e.setCurrentItem(sword);
                        }

                    }
                }
            }
        }
    }
}
