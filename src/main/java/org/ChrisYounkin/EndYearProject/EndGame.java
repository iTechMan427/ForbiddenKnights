package org.ChrisYounkin.EndYearProject;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class EndGame
  implements Listener
{
  private MainClass mainClass;
  private PlayerTeleport playerTeleport;
  private Location tempLocation;
  
  public EndGame(MainClass mainClass)
  {
    this.mainClass = mainClass;
    playerTeleport = new PlayerTeleport(mainClass);
  }
  
  public void chapterEndRunner(final String text)
  {
    mainClass.getServer().getScheduler().scheduleSyncDelayedTask(mainClass, new Runnable()
    {

	public void run()
      {
        mainClass.setRunningLevel(false);
        for (Player player : mainClass.getServer().getOnlinePlayers())
        {
          player.setGameMode(GameMode.ADVENTURE);
          tempLocation = new Location(player.getWorld(), -1246.5D, 48.0D, 0.0D);
          player.sendMessage(text);
          player.setHealth(20.0D);
          player.setFoodLevel(20);
          player.getInventory().clear();
          mainClass.sendToServer(player, "hub");
        }
        playerTeleport.teleportAllWithBorder(tempLocation, 100.0D);
      }
    }, mainClass.getDelay());
  }
}