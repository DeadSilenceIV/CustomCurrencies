package us.lynuxcraft.deadsilenceiv.customcurrencies.managers;

import lombok.Getter;
import us.lynuxcraft.deadsilenceiv.customcurrencies.tasks.SaveTask;

public class TaskManager{
    @Getter private SaveTask saveTask;
    public TaskManager() {
        saveTask = new SaveTask();
    }
}
