package org.ChrisYounkin.EndYearProject;

import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PlayerTeleport
  implements Listener
{
  private MainClass mainClass;
  private WorldBorder worldBorder;
  
  public PlayerTeleport(MainClass mainClass)
  {
    this.mainClass = mainClass;
    worldBorder = mainClass.getServer().getWorld("world").getWorldBorder();
  }
  
  public void teleportWithBorder(Player player, Location location, double borderSize)
  {
    if (!mainClass.isServerHub()) {
      worldBorder.setSize(1000000.0D);
    }
    player.teleport(location);
    if (!mainClass.isServerHub())
    {
      worldBorder.setCenter(location);
      worldBorder.setSize(borderSize);
    }
  }
  
  public void teleportAllWithBorder(Location location, double borderSize)
  {
    if (!mainClass.isServerHub()) {
      worldBorder.setSize(1000000.0D);
    }
    for (Player player : mainClass.getServer().getOnlinePlayers()) {
      player.teleport(location);
    }
    if (!mainClass.isServerHub())
    {
      worldBorder.setCenter(location);
      worldBorder.setSize(borderSize);
    }
  }
}