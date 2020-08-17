package us.lynuxcraft.deadsilenceiv.customcurrencies.managers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import us.lynuxcraft.deadsilenceiv.customcurrencies.Account;
import us.lynuxcraft.deadsilenceiv.customcurrencies.CustomCurrencies;
import us.lynuxcraft.deadsilenceiv.customcurrencies.Economy;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.yml.ConfigStorageFile;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.yml.DataStorageFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EconomyManager{
    private ConfigStorageFile configStorageFile;
    private DataStorageFile dataStorageFile;
    @Getter private List<Economy> economies;
    private List<Account> accounts;
    public EconomyManager() {
        CustomCurrencies plugin = CustomCurrencies.getInstance();
        configStorageFile = plugin.getConfigStorageFile();
        dataStorageFile = plugin.getDataStorageFile();
        economies = new ArrayList<>();
        accounts = new ArrayList<>();
    }

    public void load(){
        for(String ecoName : configStorageFile.getLoadedEconomies()){
            Economy economy = new Economy(ecoName);
            economies.add(economy);
        }
        for(UUID uuid : dataStorageFile.getConfigs().keySet()){
            YamlConfiguration config = dataStorageFile.getConfigs().get(uuid);
            ConfigurationSection section = config.getConfigurationSection("accounts");
            for(String economy : section.getKeys(false)){
                if(configStorageFile.getLoadedEconomies().contains(economy)){
                    Account account = new Account(getEconomyByName(economy),Bukkit.getOfflinePlayer(uuid),section.getDouble(economy+".balance"));
                    accounts.add(account);
                }
            }
        }
    }

    public void registerAccount(Account account){
        accounts.add(account);
    }


    public Account getPlayerEconomyAccount(OfflinePlayer offlinePlayer,Economy economy){
        for(Account account : accounts){
            if(account.getOwner().equals(offlinePlayer) && account.getEconomy().equals(economy))return account;
        }
        return null;
    }

    public List<Account> getAccountsByEconomy(Economy economy){
        List<Account> accounts = new ArrayList<>();
        for(Account account : this.accounts){
            if(account.getEconomy().equals(economy))accounts.add(account);
        }
        return accounts;
    }
    public Economy getEconomyByName(String name){
        for(Economy economy : economies){
            if(economy.getName().equals(name))return economy;
        }
        return null;
    }
}
