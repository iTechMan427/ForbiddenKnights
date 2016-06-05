package org.ChrisYounkin.EndYearProject;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PlayerFail
  implements Listener
{
  private MainClass mainClass;
  private boolean failed = false;
  private KillAll killAll;
  private PlayerTeleport playerTeleport;
  
  public PlayerFail(MainClass mainClass)
  {
    this.mainClass = mainClass;
    killAll = new KillAll(mainClass);
    playerTeleport = new PlayerTeleport(mainClass);
    playerFailCheck();
  }
  
  public boolean getFailed()
  {
    return failed;
  }
  
  public void runFail()
  {
    failed = true;
    mainClass.setRunningLevel(false);
    killAll.removeEntities();
    for (Player player : mainClass.getServer().getOnlinePlayers())
    {
      if (mainClass.getIfTrainingFromConfig()) {
        player.sendMessage(ChatColor.WHITE + "Sorry, you have failed. " + ChatColor.RED + " Try Again...");
      } else if (!mainClass.getIfTrainingFromConfig()) {
        player.sendMessage(ChatColor.RED + "Mwahahahaha! " + ChatColor.WHITE + " You though you could win.");
      }
      player.setHealth(20.0D);
      player.setFoodLevel(20);
      player.getInventory().clear();
      player.setGameMode(GameMode.ADVENTURE);
      mainClass.sendToServer(player, "hub");
    }
    playerTeleport.teleportAllWithBorder(new Location(mainClass.getServer().getWorld("world"), -1246.5D, 48.0D, 0.0D), 100.0D);
  }
  
  public int getAdventurePlayers()
  {
    int count = 0;
    for (Player player : mainClass.getServer().getOnlinePlayers()) {
      if (player.getGameMode() == GameMode.ADVENTURE) {
        count++;
      }
    }
    return count;
  }
  
  public void playerFailCheck()
  {
    mainClass.getServer().getScheduler().scheduleSyncRepeatingTask(mainClass, new Runnable()
    {
      boolean ranFailed = false;
      
      public void run()
      {
        if (mainClass.getRunningLevel()) {
          if ((!ranFailed) && (getAdventurePlayers() == 0) && (!failed))
          {
            ranFailed = true;
            runFail();
          }
          else
          {
            for (Player player : mainClass.getServer().getOnlinePlayers())
            {
              if ((!ranFailed) && (player.getHealth() < 0.5D) && (!failed) && (getAdventurePlayers() > 1) && (player.getGameMode() == GameMode.ADVENTURE))
              {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.GREEN + "You have died and are now a spectator.");
                player.sendMessage(ChatColor.GOLD + "Please wait for the game to end or type " + ChatColor.RED + "/hub" + ChatColor.GOLD + " to go back to the hub.");
              }
              if ((!ranFailed) && (player.getHealth() < 0.5D) && (!failed) && (getAdventurePlayers() == 1) && (player.getGameMode() == GameMode.ADVENTURE))
              {
                ranFailed = true;
                runFail();
              }
            }
          }
        }
      }
    }, 0L, 1L);
  }
}