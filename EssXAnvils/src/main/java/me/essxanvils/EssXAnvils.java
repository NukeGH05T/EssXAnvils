package me.essxanvils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EssXAnvils extends JavaPlugin {

    public static EssXAnvils plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        InitializeConfig();
        RegisterCommands();
        Bukkit.getPluginManager().registerEvents(new AnvilListener(), plugin);
    }

    private void InitializeConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    private void RegisterCommands() {
        getCommand("essentialsxanvils").setExecutor(new ReloadCommand());
        getCommand("essentialsxanvils").setTabCompleter(new TabComplete());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
