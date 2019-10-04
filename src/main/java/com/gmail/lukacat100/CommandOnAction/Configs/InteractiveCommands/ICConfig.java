package com.gmail.lukacat100.CommandOnAction.Configs.InteractiveCommands;

import com.gmail.lukacat100.CommandOnAction.Configs.CustomConfig;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class ICConfig {
    private JavaPlugin plugin;
    private CustomConfig config;
    private Map<Location,  List<String>> locationCommandMap = null;
    public ICConfig(JavaPlugin plugin){
        this.plugin = plugin;
        this.config = new CustomConfig(plugin, "InteractiveCommands.yml");
        try {
            this.config.reloadCustomConfig();
            List<Map<?, ?>> storedMapList = this.config.getCustomConfig().getMapList("iclist");
            Map<Location, List<String>> storedMap = null;
            if(storedMapList.size() == 1)
                locationCommandMap = (Map<Location, List<String>>) storedMapList.get(0);
            if(locationCommandMap == null){
                plugin.getLogger().log(Level.SEVERE, "[COOOOOOL] Stored map not recognized type. Creating a new one!");
                locationCommandMap = new HashMap<Location, List<String>>();
                setMapList();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    public void addLocationCommand(Location location, String command) throws UnsupportedEncodingException {
        List<String> commandList = null;
        if(!containsLocation(location))
            commandList = new ArrayList<>();
        else
            commandList = locationCommandMap.get(location);
        commandList.add(command);
        locationCommandMap.put(location, commandList);

        setMapList();


    }

    public void removeLocation(Location location) throws UnsupportedEncodingException {
        locationCommandMap.remove(location);

        setMapList();


    }

    private void setMapList() throws UnsupportedEncodingException {
        List<Map<Location, List<String>>> listToAdd = new ArrayList<Map<Location, List<String>>>();
        listToAdd.add(locationCommandMap);
        config.getCustomConfig().set("iclist", listToAdd);
        config.saveCustomConfig();
    }

    public boolean containsLocation(Location location){
        return locationCommandMap.containsKey(location);
    }

    public List<String> get(Location location){
        return locationCommandMap.get(location);
    }

}
