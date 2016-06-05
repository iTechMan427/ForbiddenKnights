package org.ChrisYounkin.EndYearProject;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public class NPCClass
  implements Listener
{
  private NPC myNPC;
  private Player owner;
  private String mob;
  private Location location;
  private String name;
  private String nameColor;
  private int amount = 1;
  public NPCClass(Player owner, String mob)
  {
    this.owner = owner;
    this.mob = mob;
  }
  
  public NPCClass(Player owner, String mob, Location location)
  {
    this.owner = owner;
    this.mob = mob;
    this.location = location;
  }
  
  public Entity getNPCEntity()
  {
    return myNPC.getEntity();
  }

public void spawnNPC()
    throws Exception
  {
    for (int i = 0; i < amount; i++)
    {
      if (name != null)
      {
        if (nameColor == null) {
          myNPC = CitizensAPI.getNPCRegistry().createNPC(EntityType.valueOf(mob.toUpperCase()), 
            ChatColor.WHITE + name);
        } else {
          myNPC = CitizensAPI.getNPCRegistry().createNPC(EntityType.valueOf(mob.toUpperCase()), 
            ChatColor.valueOf(nameColor.toUpperCase()) + name);
        }
      }
      else {
        myNPC = CitizensAPI.getNPCRegistry().createNPC(EntityType.valueOf(mob.toUpperCase()), 
          mob.substring(0, 1).toUpperCase() + mob.substring(1).toLowerCase());
      }
      
      if (location == null)
      {
        if (owner.getEyeLocation().getDirection().getY() > 0.0D) {
          myNPC.spawn(owner.getTargetBlock((Set<Material>) null, 1000000000).getLocation());
        } else {
          myNPC.spawn(owner.getTargetBlock((Set<Material>) null, 1000000000).getLocation()
            .add(new Location(owner.getWorld(), 0.0D, 1.0D, 0.0D)));
        }
      }
      else {
        myNPC.spawn(location);
      }
      myNPC.getTrait(SentryTrait.class).getInstance().RespawnDelaySeconds = Integer.valueOf(0);
    }
  }
  
  public void lookTwardsPlayerAtBegin(MainClass mainClass)
  {
    myNPC.faceLocation(owner.getLocation());
  }
}