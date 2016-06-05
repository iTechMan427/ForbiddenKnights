package org.ChrisYounkin.EndYearProject;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class StartGame
  implements Listener
{
  private MainClass mainClass;
  
  public StartGame(MainClass mainClass)
  {
    this.mainClass = mainClass;
  }
  
  public void enoughPlayers()
  {
    mainClass.getServer().getScheduler().scheduleSyncRepeatingTask(mainClass, new Runnable()
    {
      public void run()
      {
        try
        {
          if (!mainClass.getRunningLevel()) {
            for (Player player : mainClass.getServer().getOnlinePlayers())
            {
              player.setFoodLevel(20);
              player.setHealth(20.0D);
              player.getInventory().clear();
            }
          }
          if (!mainClass.isServerHub())
          {
            if ((mainClass.getServer().getOnlinePlayers().size() > 0) && (mainClass.getServer().getOnlinePlayers().size() < mainClass.getPlayersNeededToStart()) && (!mainClass.getRunningLevel())) {
              mainClass.getServer().broadcastMessage(ChatColor.GREEN + "There must be at least " + mainClass.getPlayersNeededToStart() + " players to start the game.");
            }
            if ((mainClass.getServer().getOnlinePlayers().size() >= mainClass.getPlayersNeededToStart()) && (!mainClass.getRunningLevel())) {
              startGame();
            }
          }
        }
        catch (Exception e)
        {
          mainClass.getServer().broadcastMessage(ChatColor.RED + "Unknown Error");
        }
      }
    }, 0L, 600L);
  }
  
  public void startGame()
    throws Exception
  {
    mainClass.getServer().broadcastMessage(ChatColor.GRAY + "Starting...");
    mainClass.getServer().getScheduler().scheduleSyncDelayedTask(mainClass, new Runnable()
    {
      public void run()
      {
        try
        {
          new GameRunner(mainClass);
        }
        catch (Exception e)
        {
          mainClass.getServer().broadcastMessage(ChatColor.RED + "Unknown Error");
        }
      }
    }, mainClass.getDelay());
  }
}