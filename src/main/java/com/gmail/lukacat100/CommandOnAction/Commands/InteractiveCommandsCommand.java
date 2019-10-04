package com.gmail.lukacat100.CommandOnAction.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class InteractiveCommandsCommand implements CommandExecutor {
    public static Map<Player, String> playerQueuedCommands = new HashMap<Player, String>();
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("Not a player");
            return false;
        }
        Player player = (Player) commandSender;
        if(strings.length == 0) {
            commandSender.sendMessage("Bad length");
            return false;
        }
        if(strings[0].equalsIgnoreCase("add") && strings.length >= 2){
            StringBuilder commandToAdd = new StringBuilder("");
            for(int i = 1; i < strings.length; i++){
                commandToAdd.append(strings[i]);
                if(i != strings.length - 1)
                    commandToAdd.append(" ");
            }
            String finalCommand = commandToAdd.toString().startsWith("/") ? commandToAdd.substring(1) : commandToAdd.toString();
            playerQueuedCommands.put(player, finalCommand);
            commandSender.sendMessage("Command set: '" + finalCommand + "'! Press the block to add the command to it.");
            commandSender.sendMessage("Use '/" + s + " exit' to exit edit mode.");
            return true;
        }
        if(strings[0].equalsIgnoreCase("exit")  && strings.length == 1){
            playerQueuedCommands.remove(player);
            player.sendMessage("Exited edit mode!");
        }
        return false;
    }
}
