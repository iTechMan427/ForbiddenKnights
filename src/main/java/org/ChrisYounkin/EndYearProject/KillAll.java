package org.ChrisYounkin.EndYearProject;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;

public class KillAll
  implements Listener
{
  private MainClass mainClass;
  
  public KillAll(MainClass mainClass)
  {
    this.mainClass = mainClass;
  }
  
  public int removeEntities()
  {
    int count = 0;
    for (Entity entity : mainClass.getServer().getWorld("world").getEntities()) {
      if (entity.getType() != EntityType.PLAYER)
      {
        entity.remove();
        count++;
      }
      else if (entity.hasMetadata("NPC"))
      {
        entity.remove();
        count++;
      }
    }
    return count;
  }
}