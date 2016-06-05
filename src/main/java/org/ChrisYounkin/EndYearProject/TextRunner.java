package org.ChrisYounkin.EndYearProject;

import org.bukkit.event.Listener;

public class TextRunner
  implements Listener
{
  private MainClass mainClass;
  private boolean isTextDone;
  
  public boolean getTextDone()
  {
    return isTextDone;
  }
  
  public TextRunner(MainClass mainClass)
  {
    this.mainClass = mainClass;
  }
  
  public void runText(final PlayerFail playerFail, final String[] args, final String name)
  {
    mainClass.getServer().getScheduler().scheduleSyncRepeatingTask(mainClass, new Runnable()
    {
      int seconds = 0;
      int count = 0;
      
      public void run()
      {
        if (!playerFail.getFailed())
        {
          seconds += 1;
          if ((seconds == 3) && (count < args.length))
          {
            mainClass.getLogger().info(name + args[count]);
            if (args[count].equals("")) {
              mainClass.getServer().broadcastMessage("");
            } else {
              mainClass.getServer().broadcastMessage(name + args[count]);
            }
            count += 1;
            seconds = 0;
          }
          if (count >= args.length) {
            mainClass.getServer().getScheduler().scheduleSyncDelayedTask(mainClass, new Runnable()
            {
              public void run()
              {
                isTextDone = true;
              }
            }, mainClass.getDelay());
          }
        }
      }
    }, 0L, 20L);
  }
}