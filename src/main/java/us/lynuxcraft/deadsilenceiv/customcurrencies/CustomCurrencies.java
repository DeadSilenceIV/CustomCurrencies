package us.lynuxcraft.deadsilenceiv.customcurrencies;

import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import us.lynuxcraft.deadsilenceiv.customcurrencies.commands.Deposit;
import us.lynuxcraft.deadsilenceiv.customcurrencies.commands.MainCmd;
import us.lynuxcraft.deadsilenceiv.customcurrencies.hooks.PlaceHolderExpansion;
import us.lynuxcraft.deadsilenceiv.customcurrencies.listeners.DataHandler;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.EconomyManager;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.TaskManager;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.yml.ConfigStorageFile;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.yml.DataStorageFile;

public class CustomCurrencies extends JavaPlugin{
    @Getter private static CustomCurrencies instance;
    @Getter private static String prefix = "&8[&c&lCustom&4&lCurrencies&8]";
    @Getter private ConfigStorageFile configStorageFile;
    @Getter private DataStorageFile dataStorageFile;
    @Getter private EconomyManager economyManager;
    @Getter private TaskManager taskManager;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadManagers();
        loadListeners();
        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null){
            new PlaceHolderExpansion().register();
        }
        loadCommands();
    }

    private void loadManagers(){
        configStorageFile = new ConfigStorageFile(getConfig());
        dataStorageFile = new DataStorageFile();
        economyManager = new EconomyManager();
        economyManager.load();
        taskManager = new TaskManager();
    }

    private void loadListeners(){
        getServer().getPluginManager().registerEvents(new DataHandler(),this);
    }

    private void loadCommands(){
        getServer().getPluginCommand("customcurrencies").setExecutor(new MainCmd());
        getServer().getPluginCommand("deposit").setExecutor(new Deposit());
    }

    @Override
    public void onDisable() {
        taskManager.getSaveTask().stopTask();
        for(OfflinePlayer offlinePlayer : getServer().getOfflinePlayers()){
            dataStorageFile.save(offlinePlayer);
        }
    }

    public static void sendMessage(CommandSender sender, String message, boolean prefix){
        String pref = (prefix) ? getPrefix()+" " : "";
        sender.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', pref+message));
    }

    public void reloadPlugin(){
        onDisable();
        reloadConfig();
        loadManagers();
        loadListeners();
        loadCommands();
    }
}
