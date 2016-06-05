package org.ChrisYounkin.EndYearProject;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class SpawnEntities
  implements Listener
{
  private MainClass mainClass;

ArrayList<Entity> entityArray = new ArrayList<Entity>();
  
  public SpawnEntities(MainClass mainClass)
  {
    this.mainClass = mainClass;
  }
  
  public boolean getIfAllDead()
  {
    for (Entity entity : entityArray) {
      if (!entity.isDead()) {
        return false;
      }
    }
    return true;
  }
  
  public void spawnMobs(String mobString, Location location, int amount)
    throws Exception
  {
    for (Player tempPlayer : mainClass.getServer().getOnlinePlayers()) {
      if (tempPlayer.getGameMode() == GameMode.ADVENTURE)
      {
        MobClass tempMob = new MobClass(tempPlayer, mobString, location);
        for (int i = 0; i < amount; i++)
        {
          tempMob.spawnMob();
          entityArray.add(tempMob.getMobEntity());
        }
      }
    }
  }
  
  public void spawnTrainerNPC(Location location)
    throws Exception
  {
    Player player = null;
    for (Player tempPlayer : mainClass.getServer().getOnlinePlayers()) {
      player = tempPlayer;
    }
    NPCClass trainerNPC = new NPCClass(player, "player", location);
    trainerNPC.lookTwardsPlayerAtBegin(mainClass);
    trainerNPC.spawnNPC();
  }
}