package com.gmail.lukacat100.CommandOnAction;

import com.gmail.lukacat100.CommandOnAction.Commands.InteractiveCommandsCommand;
import com.gmail.lukacat100.CommandOnAction.Configs.InteractiveCommands.ICConfig;
import com.gmail.lukacat100.CommandOnAction.Events.InteractiveCommandsListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private ICConfig icConfig;
    @Override
    public void onEnable() {
        PluginCommand ic = this.getCommand("interactivecommands");
        if(ic != null)
            ic.setExecutor(new InteractiveCommandsCommand());
        icConfig = new ICConfig(this);
        getServer().getPluginManager().registerEvents(new InteractiveCommandsListener(this, icConfig), this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("interactivecommands")){

        }
        return super.onCommand(sender, command, label, args);
    }
}
