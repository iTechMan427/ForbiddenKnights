package org.ChrisYounkin.EndYearProject;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.Listener;

public class WaveRunner
  implements Listener
{
  private MainClass mainClass;
  private GameController levelController;
  private boolean isWaveComplete;
  
  public WaveRunner(MainClass mainClass, Location location, String items)
    throws Exception
  {
    this.mainClass = mainClass;
    
    mainClass.setRunningLevel(true);
    levelController = new GameController(mainClass);
    mainClass.setItemsString(items);
    
    levelController.teleportAllWithBorder(location, 100.0D);
    mainClass.setLevelLocation(location);
    levelController.clearPlayerInventories();
    levelController.healPlayers();
    
    String[] playerItems = items.split(" ");
    String[] arrayOfString1;
    int j = (arrayOfString1 = playerItems).length;
    for (int i = 0; i < j; i++)
    {
      String item = arrayOfString1[i];
      levelController.giveItemsToPlayers(item);
    }
    if (mainClass.getIfTrainingFromConfig()) {
      levelController.spawnTrainerNPC(new Location(mainClass.getServer().getWorld("world"), -1660.5D, 48.0D, 0.0D));
    }
  }
  
  public boolean getWaveComplete()
  {
    return isWaveComplete;
  }
  
  public void setWaveComplete(boolean isWaveComplete)
  {
    this.isWaveComplete = isWaveComplete;
  }
  
  public void endChapter(String text)
  {
    levelController.chapterEndRunner(text);
  }
  
  public boolean getFailed()
  {
    return levelController.getFailed();
  }
  
  public void runWave(String[] text, String textName, final String mobString, final Location mobLocation, final int mobAmount)
    throws Exception
  {
    levelController.textRunner(text, ChatColor.BLUE + textName + ": " + ChatColor.WHITE);
    
    mainClass.getServer().getScheduler().scheduleSyncRepeatingTask(mainClass, new Runnable()
    {
      boolean textDone = false;
      boolean allDead = false;
      
      public void run()
      {
        if (!levelController.getFailed()) {
          try
          {
            if ((levelController.getTextDone()) && (!textDone))
            {
              textDone = true;
              if (mobAmount > 0)
              {
                mainClass.getServer().broadcastMessage(ChatColor.GOLD + "Kill all the " + mobString.toLowerCase() + "s!");
                levelController.spawnMobs(mobString, mobLocation, mobAmount);
              }
            }
            if ((levelController.getIfAllDead()) && (textDone) && (!allDead))
            {
              allDead = true;
              isWaveComplete = true;
            }
          }
          catch (Exception e)
          {
            mainClass.getServer().broadcastMessage(ChatColor.RED + "Unknown Error");
          }
        }
      }
    }, 0L, 20L);
  }
}