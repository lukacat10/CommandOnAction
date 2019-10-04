package com.gmail.lukacat100.CommandOnAction.Events;

import com.gmail.lukacat100.CommandOnAction.Commands.InteractiveCommandsCommand;
import com.gmail.lukacat100.CommandOnAction.Configs.InteractiveCommands.ICConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class InteractiveCommandsListener implements Listener {
    private ICConfig config;
    private JavaPlugin plugin;
    public InteractiveCommandsListener(JavaPlugin plugin, ICConfig icConfig){
        this.plugin = plugin;
        config = icConfig;
    }
    @EventHandler
    public void Interaction(PlayerInteractEvent event){
        if(event.getClickedBlock() == null)
            return;
        if(event.getClickedBlock().isEmpty() || event.getClickedBlock().getType() == Material.AIR)
            return;

        Location blockLocation = event.getClickedBlock().getLocation();
        Map<Player, String> playerCommands = InteractiveCommandsCommand.playerQueuedCommands;
        Player player = event.getPlayer();

        if(playerCommands.containsKey(player)){
            event.setCancelled(true);
            try {
                config.addLocationCommand(blockLocation, playerCommands.get(player));
                player.sendMessage("Successfuly added '/" + playerCommands.get(player) + "'! Click the block to execute the command!");
                player.sendMessage("To remove the command, simply break the block!");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }finally {
                playerCommands.remove(player);
            }
            return;
        }
        if(event.getHand() == null || !event.getHand().name().equals("HAND"))
            return;
        if(config.containsLocation(blockLocation)){
            List<String> commands = config.get(blockLocation);
            String playerName = player.getName();
            playerName = playerName.replace(" ", "");
            for (String cmd :
                    commands) {
                String command = cmd.replace("@p", playerName);
                player.performCommand(command);
            }
        }
    }
    @EventHandler
    public void ICDestroyed(BlockBreakEvent event){
        if(event.isCancelled())
            return;

        Location blockLocation = event.getBlock().getLocation();

        if(config.containsLocation(blockLocation)){
            try {
                config.removeLocation(blockLocation);
                event.getPlayer().sendMessage("IC Removed!");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
