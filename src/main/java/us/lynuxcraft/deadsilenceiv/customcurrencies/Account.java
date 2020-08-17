package us.lynuxcraft.deadsilenceiv.customcurrencies;

import lombok.Getter;
import org.bukkit.OfflinePlayer;

public class Account{
    @Getter private Economy economy;
    @Getter private OfflinePlayer owner;
    @Getter private Double balance;
    public Account(Economy economy, OfflinePlayer owner,Double balance) {
        this.economy = economy;
        this.owner = owner;
        this.balance = balance;
    }

    public EconomyResponse deposit(Double value){
        balance = balance+value;
        return new EconomyResponse(this,value);
    }

    public EconomyResponse withdraw(Double value){
        balance = balance-value;
        if(balance < 0)balance = 0.0;
        return new EconomyResponse(this,-value);
    }
}
