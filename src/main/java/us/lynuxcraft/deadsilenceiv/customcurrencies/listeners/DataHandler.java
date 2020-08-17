package us.lynuxcraft.deadsilenceiv.customcurrencies.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import us.lynuxcraft.deadsilenceiv.customcurrencies.CustomCurrencies;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.yml.DataStorageFile;

public class DataHandler implements Listener{
    private DataStorageFile dataStorageFile;
    public DataHandler() {
        dataStorageFile = CustomCurrencies.getInstance().getDataStorageFile();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        dataStorageFile.save(e.getPlayer());
    }
}
