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
import us.lynuxcraft.deadsilenceiv.customcurrencies.utils.Sound;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Deposit implements CommandExecutor{
    private EconomyManager economyManager;
    public Deposit() {
        economyManager = CustomCurrencies.getInstance().getEconomyManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;
            if (args.length >= 1) {
                OfflinePlayer player = Arrays.stream(Bukkit.getOfflinePlayers()).filter(offlinePlayer -> offlinePlayer.getName().equals(args[0])).findFirst().orElse(null);
                if (player != null) {
                    if (p == player) {
                        CustomCurrencies.sendMessage(sender, "&cYou can't do this&6!", false);
                        return true;
                    }
                    if (args.length >= 2) {
                        Economy economy = economyManager.getEconomyByName(args[1]);
                        if (economy != null) {
                            Account destinyAccount = economy.getAccount(player);
                            Account senderAccount = economy.getAccount(p);
                            if (args.length >= 3) {
                                Double value;
                                try {
                                    value = Double.parseDouble(args[2]);
                                } catch (NumberFormatException e) {
                                    CustomCurrencies.sendMessage(sender, "&cInvalid amount&6!", false);
                                    return true;
                                }
                                if (senderAccount.getBalance() >= value) {
                                    senderAccount.withdraw(value);
                                    destinyAccount.deposit(value);
                                    DecimalFormat format = new DecimalFormat("#.##");
                                    CustomCurrencies.sendMessage(sender, "&f" + format.format(value) + "&6$ &ahave been sent to " + player.getName() + "'s &e" + economy.getName() + " &aaccount&6!", false);
                                    p.playSound(p.getLocation(), Sound.ARROW_HIT.bukkitSound(), 100F, 1F);
                                    CustomCurrencies.sendMessage(sender, "&f" + format.format(value) + "&6$ &ahave been added to your &e" + economy.getName() + " &aaccount&6!", false);
                                    return true;
                                } else {
                                    CustomCurrencies.sendMessage(sender, "&cYou don't have this amount in your &e" + economy.getName() + " &caccount&6!", false);
                                    return true;
                                }
                            }
                        } else {
                            CustomCurrencies.sendMessage(sender, "&cThe declared currency was not found&6!", false);
                            return true;
                        }
                    }
                } else {
                    CustomCurrencies.sendMessage(sender, "&cThe player was not found&6!", false);
                    return true;
                }
            }
        }
        return true;
    }
}
