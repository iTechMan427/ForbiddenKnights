package org.ChrisYounkin.EndYearProject;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class MobClass
  implements Listener
{
  private Entity entity;
  private Player owner;
  private String mob;
  private Location location;
  private int amount = 1;
  public MobClass(Player owner, String mob)
  {
    this.owner = owner;
    this.mob = mob;
  }
  
  public MobClass(Player owner, String mob, Location location)
  {
    this.owner = owner;
    this.mob = mob;
    this.location = location;
  }
  
  public Entity getMobEntity()
  {
    return entity;
  }
  
public void spawnMob()
    throws Exception
  {
    for (int i = 0; i < amount; i++)
    {
      if (location == null)
      {
        if (owner.getEyeLocation().getDirection().getY() > 0.0D) {
          entity = owner.getWorld().spawnEntity(owner.getTargetBlock((Set<Material>) null, 1000000000).getLocation(), EntityType.valueOf(mob.toUpperCase()));
        } else {
          entity = owner.getWorld().spawnEntity(owner.getTargetBlock((Set<Material>) null, 1000000000).getLocation().add(new Location(owner.getWorld(), 0.0D, 1.0D, 0.0D)), EntityType.valueOf(mob.toUpperCase()));
        }
      }
      else {
        entity = owner.getWorld().spawnEntity(location, EntityType.valueOf(mob.toUpperCase()));
      }
      setTamed();
    }
  }
  
  public void setTamed()
  {
    if (entity.getType() == EntityType.WOLF)
    {
      Wolf wolf = (Wolf)entity;
      wolf.setTamed(true);
      wolf.setOwner(owner);
    }
    if (entity.getType() == EntityType.OCELOT)
    {
      Ocelot ocelot = (Ocelot)entity;
      ocelot.setTamed(true);
      ocelot.setOwner(owner);
    }
    if (entity.getType() == EntityType.HORSE)
    {
      Horse horse = (Horse)entity;
      horse.setTamed(true);
      horse.setOwner(owner);
      horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
      owner.getInventory().addItem(new ItemStack[] { new ItemStack(Material.LEASH) });
    }
  }
}