package org.ChrisYounkin.EndYearProject;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.Listener;

public class GameRunner
  implements Listener
{
  private WaveRunner waveRunner;
  private MainClass mainClass;
  private int waveNum = 1;
  
  public GameRunner(MainClass mainClass)
    throws Exception
  {
    this.mainClass = mainClass;
    
    Location location = mainClass.stringToLocation(mainClass.getConfig().getString("Location"));
    waveRunner = new WaveRunner(mainClass, location, mainClass.getConfig().getString("Items"));
    
    nextWave(1);
  }
  
  private void nextWave(int myWaveNum)
    throws Exception
  {
    String text = mainClass.getConfig().getString("Text_" + Integer.toString(myWaveNum));
    String textName = mainClass.getConfig().getString("TextName_" + Integer.toString(myWaveNum));
    String mobType;
    if (mainClass.getConfig().getString("MobType_" + Integer.toString(myWaveNum)).equals("none")) {
      mobType = "zombie";
    } else {
      mobType = mainClass.getConfig().getString("MobType_" + Integer.toString(myWaveNum));
    }
    String mobLocation;
    if (mainClass.getConfig().getString("MobLocation_" + Integer.toString(myWaveNum)).equals("none")) {
      mobLocation = "0 0 0";
    } else {
      mobLocation = mainClass.getConfig().getString("MobLocation_" + Integer.toString(myWaveNum));
    }
    String mobAmount;
    if (mainClass.getConfig().getString("MobAmount_" + Integer.toString(myWaveNum)).equals("none")) {
      mobAmount = "0";
    } else {
      mobAmount = mainClass.getConfig().getString("MobAmount_" + Integer.toString(myWaveNum));
    }
    wave(text, textName, mobType, mobLocation, mobAmount);
  }
  
  public void wave(String textString, String textName, String mobType, String mobLocationString, String mobAmountString)
    throws Exception
  {
    String[] text = textString.split(" - ");
    Location mobLocation = mainClass.stringToLocation(mobLocationString);
    int mobAmount = Integer.parseInt(mobAmountString);
    
    waveRunner.runWave(text, textName, mobType, mobLocation, mobAmount);
    
    mainClass.getServer().getScheduler().scheduleSyncRepeatingTask(mainClass, new Runnable()
    {
      private boolean waveComplete = false;
      
      public void run()
      {
        if ((!waveRunner.getFailed()) && (!waveComplete) && (waveRunner.getWaveComplete())) {
          try
          {
            waveComplete = true;
            waveRunner.setWaveComplete(false);
            try
            {
              waveNum += 1;
              GameRunner.this.nextWave(waveNum);
            }
            catch (Exception e)
            {
              String text = ChatColor.GREEN + "Gongrats! " + ChatColor.WHITE + " You Have Completed the Game!";
              waveRunner.endChapter(text);
            }
            return;
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