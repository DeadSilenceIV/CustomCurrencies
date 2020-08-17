package us.lynuxcraft.deadsilenceiv.customcurrencies.managers.yml;

import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import us.lynuxcraft.deadsilenceiv.customcurrencies.Account;
import us.lynuxcraft.deadsilenceiv.customcurrencies.CustomCurrencies;
import us.lynuxcraft.deadsilenceiv.customcurrencies.Economy;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.EconomyManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataStorageFile{
    @Getter private Map<UUID,YamlConfiguration> configs;
    @Getter private File folder;
    private CustomCurrencies plugin;
    public DataStorageFile() {
        configs = new HashMap<>();
        plugin = CustomCurrencies.getInstance();
        folder = new File(plugin.getDataFolder()+File.separator+"data");
        if(!folder.exists()){folder.mkdirs();}
        load();
    }

    private void load(){
        File[] files = folder.listFiles();
        if(files != null) {
            for (File file : files) {
                if (getFileExtension(file.getName()).equals(".yml")) {
                    String uuid = removeExtension(file.getName());
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                    configs.put(UUID.fromString(uuid),config);
                }
            }
        }
    }

    public void save(OfflinePlayer offlinePlayer){
        EconomyManager economyManager = plugin.getEconomyManager();
        File file = new File(folder.getPath()+File.separator+offlinePlayer.getUniqueId()+".yml");
        if(!file.exists()){create(file);}
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for(Economy economy : economyManager.getEconomies()){
            Account account = economy.getAccount(offlinePlayer);
            if(account == null){
                account = new Account(economy,offlinePlayer,0.0);
                economyManager.registerAccount(account);
            }
            config.set("accounts."+economy.getName()+".balance",account.getBalance());
        }
        save(file,config);
    }


    private void create(File file){
        try{
            file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void save(File file,YamlConfiguration config){
        try {
            config.save(file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String removeExtension(String fileName) {
        if (fileName.indexOf(".") > 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            return fileName;
        }
    }

    private String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return fileName.substring(lastIndexOf);
    }
}
