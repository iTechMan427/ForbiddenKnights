package org.ChrisYounkin.EndYearProject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass
  extends JavaPlugin
  implements Listener
{

  private StartGame startGame;
  private EventController eventController;
  private boolean runningLevel;
  private Location levelLocation;
  private String playerItemsString;
  
  public void onEnable()
  {
    getLogger().info("Enabling EndYearProject by ChrisYounkin");
    getConfig().options().copyDefaults(true);
    saveConfig();
    
    startGame = new StartGame(this);
    startGame.enoughPlayers();
    eventController = new EventController(this);
    eventController.whenReload();
    
    runningLevel = false;
    levelLocation = null;
    playerItemsString = null;
    
    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
  }
  
  public void onDisable()
  {
    getLogger().info("Disabling EndYearProject by ChrisYounkin");
  }
  
  public void sendToServer(Player player, String server)
  {
    ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
    DataOutputStream outputStream = new DataOutputStream(byteArrayStream);
    try
    {
      outputStream.writeUTF("Connect");
      outputStream.writeUTF(server);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    player.sendPluginMessage(this, "BungeeCord", byteArrayStream.toByteArray());
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    Player player = (Player)sender;
    if ((sender instanceof Player))
    {
      if (label.equalsIgnoreCase("game")) {
        if (isServerHub()) {
          sendToServer(player, args[0]);
        } else {
          sender.sendMessage("Unknown command. Type \"/help\" for help.");
        }
      }
      if (label.equalsIgnoreCase("hub")) {
        if (!isServerHub()) {
          sendToServer(player, "hub");
        } else {
          sender.sendMessage(ChatColor.RED + "You are already at the hub.");
        }
      }
    }
    return true;
  }
  
  public Location stringToLocation(String location)
  {
    double x = Double.parseDouble(location.split(" ")[0]);
    double y = Double.parseDouble(location.split(" ")[1]);
    double z = Double.parseDouble(location.split(" ")[2]);
    
    return new Location(getServer().getWorld("world"), x, y, z);
  }
  
  public void setItemsString(String playerItemsString)
  {
    this.playerItemsString = playerItemsString;
  }
  
  public String getItemsString()
  {
    return playerItemsString;
  }
  
  public boolean getIfTrainingFromConfig()
  {
    return Boolean.parseBoolean(getConfig().getString("Training Level"));
  }
  
  public boolean isServerHub()
  {
    return Boolean.parseBoolean(getConfig().getString("Server Hub"));
  }
  
  public int getPlayersNeededToStart()
  {
    return Integer.parseInt(getConfig().getString("Players Needed to Start"));
  }
  
  public int getDelay()
  {
    return Integer.parseInt(getConfig().getString("Delay"));
  }
  
  public Location getSpawnLocation()
  {
    return new Location(getServer().getWorld("world"), -1246.5D, 48.0D, 0.0D);
  }
  
  public Location getLevelLocation()
  {
    return levelLocation;
  }
  
  public void setLevelLocation(Location levelLocation)
  {
    this.levelLocation = levelLocation;
  }
  
  public boolean getRunningLevel()
  {
    return runningLevel;
  }
  
  public void setRunningLevel(boolean runningLevel)
  {
    this.runningLevel = runningLevel;
  }
  
  public String getPlayerItemsString()
  {
    return playerItemsString;
  }
}