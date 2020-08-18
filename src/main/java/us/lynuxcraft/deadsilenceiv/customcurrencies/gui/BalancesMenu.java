package us.lynuxcraft.deadsilenceiv.customcurrencies.gui;

import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import us.lynuxcraft.deadsilenceiv.customcurrencies.Account;
import us.lynuxcraft.deadsilenceiv.customcurrencies.CustomCurrencies;
import us.lynuxcraft.deadsilenceiv.customcurrencies.Economy;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.EconomyManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BalancesMenu {
    private OfflinePlayer player;
    private EconomyManager economyManager;
    private List<Account> accounts;
    @Getter private List<BalancesPage> pages;
    List<HumanEntity> viewersChangingPage;
    public BalancesMenu(Player player) {
        this.player = player;
        economyManager = CustomCurrencies.getInstance().getEconomyManager();
        accounts = new ArrayList<>();
        pages = new ArrayList<>();
        viewersChangingPage = new ArrayList<>();
        for(Economy economy : economyManager.getEconomies()){
            accounts.add(economy.getAccount(player));
        }
        loadPages();
    }

    public void open(Player player,Integer pageNumber){
        Optional<BalancesPage> page  = pages.stream().filter(balancesPage -> balancesPage.getId().equals(pageNumber)).findFirst();
        page.ifPresent(balancesPage -> balancesPage.open(player));
    }

    private void loadPages(){
        Integer amountOfPages = (int)Math.ceil((double)accounts.size()/(double)45);
        if(amountOfPages > 0) {
            Integer reference = 1;
            for (int i = 1; i <= amountOfPages; i++) {
                BalancesPage balancesPage = new BalancesPage(this,i);
                for (int a = reference; a <= accounts.size(); a++) {
                    Account account = accounts.get(a - 1);
                    balancesPage.addAccount(account);
                    reference++;
                    if ((reference - 1)%45==0) {
                        break;
                    }
                }
                pages.add(balancesPage);
            }
        }else{
            BalancesPage balancesPage = new BalancesPage(this,1);
            pages.add(balancesPage);
        }
        for(BalancesPage balancesPage : pages){balancesPage.load();}
    }

    public BalancesPage getViewerPage(HumanEntity humanEntity){
        for(BalancesPage balancesPage : pages){
            if(balancesPage.getInventory().getViewers().contains(humanEntity))return balancesPage;
        }
        return null;
    }
    public List<HumanEntity> getAllPageViewers(){
        List<HumanEntity> humanEntities = new ArrayList<>();
        for(BalancesPage balancesPage : pages){
            humanEntities.addAll(balancesPage.getInventory().getViewers());
        }
        return humanEntities;
    }

    public boolean isViewerChangingPage(HumanEntity humanEntity){
        return viewersChangingPage.contains(humanEntity);
    }
}
