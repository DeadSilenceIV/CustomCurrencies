package us.lynuxcraft.deadsilenceiv.customcurrencies.managers.yml;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import us.lynuxcraft.deadsilenceiv.customcurrencies.CustomCurrencies;

import java.util.ArrayList;
import java.util.List;

public class ConfigStorageFile{
    private FileConfiguration config;
    @Getter private List<String> loadedEconomies;
    public ConfigStorageFile(FileConfiguration config) {
        this.config = config;
        loadedEconomies = new ArrayList<>();
        loadEconomies();
    }

    private void loadEconomies(){
        List<String> list = config.getStringList("currencies");
        for(String ecoName : list){
            if(!loadedEconomies.contains(ecoName)){
                loadedEconomies.add(ecoName);
            }
        }
    }

    private void sendWarning(String message){
        CustomCurrencies.sendMessage(Bukkit.getConsoleSender(),"&4&lWARNING "+message,true);
    }
}
