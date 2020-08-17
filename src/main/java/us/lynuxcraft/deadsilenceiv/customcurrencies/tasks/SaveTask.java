package us.lynuxcraft.deadsilenceiv.customcurrencies.tasks;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import us.lynuxcraft.deadsilenceiv.customcurrencies.CustomCurrencies;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.yml.DataStorageFile;

public class SaveTask extends BukkitRunnable{
    private Integer taskId;
    private DataStorageFile dataStorageFile;
    public SaveTask() {
        dataStorageFile = CustomCurrencies.getInstance().getDataStorageFile();
        taskId = runTaskTimerAsynchronously(CustomCurrencies.getInstance(),0L,3000L).getTaskId();
    }

    @Override
    public void run() {
        for(OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()){
            dataStorageFile.save(offlinePlayer);
        }
    }

    public void stopTask(){
        if(taskId != null) Bukkit.getScheduler().cancelTask(taskId);
    }
}
