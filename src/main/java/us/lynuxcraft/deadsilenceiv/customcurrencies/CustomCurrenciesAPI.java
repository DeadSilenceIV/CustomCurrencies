package us.lynuxcraft.deadsilenceiv.customcurrencies;

import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.EconomyManager;

public class CustomCurrenciesAPI{
    private static CustomCurrencies plugin = CustomCurrencies.getInstance();

    public static Economy getEconomyByName(String concurrencyEconomy){
        return plugin.getEconomyManager().getEconomyByName(concurrencyEconomy);
    }

    public static EconomyManager getEconomyManager(){
        return plugin.getEconomyManager();
    }
}
