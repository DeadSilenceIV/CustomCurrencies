package us.lynuxcraft.deadsilenceiv.customcurrencies.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.lynuxcraft.deadsilenceiv.customcurrencies.Account;
import us.lynuxcraft.deadsilenceiv.customcurrencies.CustomCurrencies;
import us.lynuxcraft.deadsilenceiv.customcurrencies.Economy;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.EconomyManager;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class MainCmd implements CommandExecutor{
    private CustomCurrencies plugin;
    private EconomyManager economyManager;
    public MainCmd() {
        plugin = CustomCurrencies.getInstance();
        economyManager = plugin.getEconomyManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        Player p = null;
        if(sender instanceof Player){
            p = (Player)sender;
        }
        if(sender.hasPermission("customcurrencies.admin")){
            if (args.length >= 1) {
                if (args[0].equals("add")) {
                    if (args.length >= 2) {
                        OfflinePlayer player = Arrays.stream(Bukkit.getOfflinePlayers()).filter(offlinePlayer -> offlinePlayer.getName().equals(args[1])).findFirst().orElse(null);
                        if (player != null) {
                            if (args.length >= 3) {
                                Economy economy = economyManager.getEconomyByName(args[2]);
                                if (economy != null) {
                                    Account account = economy.getAccount(player);
                                    if (args.length >= 4) {
                                        Double value;
                                        try {
                                            value = Double.parseDouble(args[3]);
                                        } catch (NumberFormatException e) {
                                            CustomCurrencies.sendMessage(sender, "&cInvalid amount&6!", true);
                                            return true;
                                        }
                                        account.deposit(value);
                                        DecimalFormat format = new DecimalFormat("#.##");
                                        CustomCurrencies.sendMessage(sender, "&f" + format.format(value) + "&6$ &ahave been sent to " + player.getName() + "'s &e" + economy.getName() + " &aaccount&6!", true);
                                        return true;
                                    }
                                } else {
                                    CustomCurrencies.sendMessage(sender, "&cThe declared currency was not found&6!", true);
                                    return true;
                                }
                            }
                        } else {
                            CustomCurrencies.sendMessage(sender,"&cThe player was not found&6!",true);
                            return true;
                        }
                    }
                }
                if (args[0].equals("remove")) {
                    if (args.length >= 2) {
                        OfflinePlayer player = Arrays.stream(Bukkit.getOfflinePlayers()).filter(offlinePlayer -> offlinePlayer.getName().equals(args[1])).findFirst().orElse(null);
                        if (player != null) {
                            if (args.length >= 3) {
                                Economy economy = economyManager.getEconomyByName(args[2]);
                                if (economy != null) {
                                    Account account = economy.getAccount(player);
                                    if (args.length >= 4) {
                                        Double value;
                                        try {
                                            value = Double.parseDouble(args[3]);
                                        } catch (NumberFormatException e) {
                                            CustomCurrencies.sendMessage(sender,"&cInvalid amount&6!",true);
                                            return true;
                                        }
                                        account.withdraw(value);
                                        DecimalFormat format = new DecimalFormat("#.##");
                                        CustomCurrencies.sendMessage(sender,"&f-"+format.format(value)+"&6$ &ahave been removed from "+player.getName()+"'s &e"+economy.getName()+" &aaccount&6!",true);
                                        return true;
                                    }
                                } else {
                                    CustomCurrencies.sendMessage(sender,"&cThe declared currency was not found&6!",true);
                                    return true;
                                }
                            }
                        } else {
                            CustomCurrencies.sendMessage(sender,"&cThe player was not found&6!",true);
                            return true;
                        }
                    }
                }
                if(args[0].equalsIgnoreCase("reload")){
                    CustomCurrencies.sendMessage(sender, "&7The config was reloaded correctly&6!",true);
                    plugin.reloadPlugin();
                    return true;
                }
                if(args[0].equalsIgnoreCase("list")){
                    sendEconomiesList(sender);
                    return true;
                }
            }
            sendHelp(sender);
        }
        return true;
    }

    private void sendEconomiesList(CommandSender sender){
        StringBuilder list = new StringBuilder();
        List<Economy> economies = economyManager.getEconomies();
        if(!economies.isEmpty()) {
            for (int i = 0; i <= economies.size() - 1; i++) {
                Economy economy = economyManager.getEconomies().get(i);
                if (i == 0) {
                    list = new StringBuilder("&8[&7" + economy.getName());
                    if(economies.size() == 1){
                        list.append("&8]");
                    }
                    continue;
                }
                list.append("&8,&7").append(economy.getName());
                if (i == economies.size() - 1) {
                    list.append("&8]");
                }
            }
        }
        plugin.sendMessage(sender,"&cAvailable currencies&4: "+list,true);
    }

    private void sendHelp(CommandSender sender){
        plugin.sendMessage(sender, "&f&m-------------&7=&8[&c&lCustom&4&lCurrencies&8]&7=&f&m-------------",false);
        plugin.sendMessage(sender, "&8- &4/&ccmc reload&f- &8(&7Reload the plugin&8)",false);
        plugin.sendMessage(sender, "&8- &4/&ccmc list&f- &8(&7See the list of currencies&8)",false);
        plugin.sendMessage(sender, "&8- &4/&ccmc add &4<&cplayer&4> &4<&ccurrency&4> &4<&camount&4>&f- &8(&7Add value to a player economy account&8)",false);
        plugin.sendMessage(sender, "&8- &4/&ccmc remove &4<&cplayer&4> &4<&ccurrency&4> &4<&camount&4>&f- &8(&7Remove value from a player economy account&8)",false);
        plugin.sendMessage(sender, "&8- &4/&cdeposit &4<&cplayer&4> &4<&ccurrency&4> &4<&camount&4>&f- &8(&7Deposit to a player economy account&8)",false);
        plugin.sendMessage(sender, "&8- &4/&cbalances &f- &8(&7See all your balances&8)",false);
        plugin.sendMessage(sender, "&f&m----------------------------------------------",false);
    }
}
