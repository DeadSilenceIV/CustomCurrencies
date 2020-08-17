package us.lynuxcraft.deadsilenceiv.customcurrencies;

import lombok.Getter;
import org.bukkit.OfflinePlayer;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.EconomyManager;

import java.util.List;

public class Economy{
    @Getter private String name;
    private EconomyManager economyManager;
    public Economy(String name) {
        this.name = name;
        economyManager = CustomCurrencies.getInstance().getEconomyManager();
    }

    public Account getAccount(OfflinePlayer offlinePlayer){
        return economyManager.getPlayerEconomyAccount(offlinePlayer,this);
    }

    public List<Account> getAccounts(){
        return economyManager.getAccountsByEconomy(this);
    }
}
