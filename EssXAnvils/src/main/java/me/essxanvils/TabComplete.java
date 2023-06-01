package me.essxanvils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            completions.add("reload");
            completions.add("give");

            for (String s : completions) {
                if (s.toLowerCase().startsWith(args[0].toLowerCase())) {
                    commands.add(s);
                }
            }

            return commands;
        }

        if (args.length == 3) {
            List<String> completions = minecraftEnchantmentList();

            for (String s : completions) {
                if (s.toLowerCase().startsWith(args[2].toLowerCase())) {
                    commands.add(s);
                }
            }

            return commands;
        }

        if (args.length == 4) {
            List<String> completions = new ArrayList<>();
            completions.add("1");
            completions.add("2");
            completions.add("3");
            completions.add("4");
            completions.add("5");
            completions.add("6");
            completions.add("7");
            completions.add("8");
            completions.add("9");
            completions.add("10");

            for (String s : completions) {
                if (s.toLowerCase().startsWith(args[3].toLowerCase())) {
                    commands.add(s);
                }
            }

            return commands;
        }
        return null;
    }

    private List<String> minecraftEnchantmentList() {
        List<String> completions = new ArrayList<>();
        completions.add("sharpness");
        completions.add("smite");
        completions.add("bane_of_arthropods");
        completions.add("knockback");
        completions.add("fire_aspect");
        completions.add("looting");
        completions.add("sweeping_edge");
        completions.add("efficiency");
        completions.add("silk_touch");
        completions.add("unbreaking");
        completions.add("fortune");
        completions.add("power");
        completions.add("punch");
        completions.add("flame");
        completions.add("infinity");
        completions.add("luck_of_the_sea");
        completions.add("lure");
        completions.add("mending");
        completions.add("frost_walker");
        completions.add("curse_of_binding");
        completions.add("curse_of_vanishing");
        completions.add("aqua_affinity");
        completions.add("depth_strider");
        completions.add("respiration");
        completions.add("thorns");
        completions.add("protection");
        completions.add("fire_protection");
        completions.add("blast_protection");
        completions.add("projectile_protection");
        completions.add("swift_sneak");
        completions.add("soul_speed");

        return completions;
    }
}
