package us.lynuxcraft.deadsilenceiv.customcurrencies.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import us.lynuxcraft.deadsilenceiv.customcurrencies.Account;
import us.lynuxcraft.deadsilenceiv.customcurrencies.CustomCurrencies;
import us.lynuxcraft.deadsilenceiv.customcurrencies.Economy;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.EconomyManager;
import us.lynuxcraft.deadsilenceiv.customcurrencies.utils.FormatUtils;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlaceHolderExpansion extends PlaceholderExpansion {
    private EconomyManager economyManager;
    public PlaceHolderExpansion(){
        economyManager = CustomCurrencies.getInstance().getEconomyManager();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "cmc";
    }

    @Override
    public String getAuthor() {
        return "DeadSilenceIV";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        for(Economy economy : economyManager.getEconomies()) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            DecimalFormat commasFormat = new DecimalFormat("#,###.00");
            if(identifier.equals(economy.getName()+"_balance")) {
                return ""+Double.parseDouble(decimalFormat.format(economy.getAccount(p).getBalance()).replaceAll(",","."));
            }
            if(identifier.equals(economy.getName()+"_balance_commas")){
                return commasFormat.format(economy.getAccount(p).getBalance());
            }
            if(identifier.equals(economy.getName()+"_balance_formatted")){
                return FormatUtils.formatValue(economy.getAccount(p).getBalance().floatValue());
            }
            if(identifier.contains(economy.getName()+"_top_balance_")){
                String[] splitted = identifier.split("_");
                for(int i = 0; i<= splitted.length-1;i++){
                    String string = splitted[i];
                    System.out.println(string);
                }
                Integer position;
                try {
                    position = Integer.parseInt(splitted[3]);
                }catch (NumberFormatException e){
                    return null;
                }
                if(position <= 0){
                    return null;
                }
                Comparator<Account> comparator = Comparator.comparing(Account::getBalance);
                List<Account> accountList = economy.getAccounts().stream().sorted(comparator.reversed()).collect(Collectors.toList());
                if(accountList.size() >= position) {
                    if (splitted.length == 4) {
                        return ""+Double.parseDouble(decimalFormat.format(accountList.get(position - 1).getBalance()).replaceAll(",","."));
                    }
                    if (splitted.length == 5) {
                        if (splitted[4].equals("commas")) {
                            return commasFormat.format(accountList.get(position - 1).getBalance());
                        }
                        if (splitted[4].equals("formatted")) {
                            return FormatUtils.formatValue(accountList.get(position - 1).getBalance().floatValue());
                        }
                    }
                }
            }
            if(identifier.contains(economy.getName()+"_top_player_")){
                String[] splitted = identifier.split("_");
                Integer position;
                try {
                    position = Integer.parseInt(splitted[3]);
                }catch (NumberFormatException e){
                    return null;
                }
                if(position <= 0){
                    return null;
                }
                Comparator<Account> comparator = Comparator.comparing(Account::getBalance);
                List<Account> accountList = economy.getAccounts().stream().sorted(comparator.reversed()).collect(Collectors.toList());
                if(accountList.size() >= position) {
                    return accountList.get(position-1).getOwner().getName();
                }
            }
            if(identifier.equals(economy.getName()+"_top_rank")){
                Comparator<Account> comparator = Comparator.comparing(Account::getBalance);
                List<Account> accountList = economy.getAccounts().stream().sorted(comparator.reversed()).collect(Collectors.toList());
                if(!accountList.isEmpty()) {
                    for (int i = 0; i <= accountList.size() - 1; i++) {
                        Account account = accountList.get(i);
                        if(account.getOwner().equals(p)){
                            return ""+(i+1);
                        }
                    }
                }
            }
        }
        return null;
    }
}
