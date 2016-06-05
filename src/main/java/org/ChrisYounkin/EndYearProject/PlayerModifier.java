package org.ChrisYounkin.EndYearProject;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PlayerModifier
  implements Listener
{
  MainClass mainClass;
  
  public PlayerModifier(MainClass mainClass)
  {
    this.mainClass = mainClass;
  }
  
  public void healPlayers()
  {
    for (Player player : mainClass.getServer().getOnlinePlayers())
    {
      player.setHealth(20.0D);
      player.setFoodLevel(20);
    }
  }
  
  public void clearPlayerInventories()
  {
    for (Player player : mainClass.getServer().getOnlinePlayers()) {
      player.getInventory().clear();
    }
  }
  
  public void giveItemsToPlayers(String item, int amount)
  {
    for (Player player : mainClass.getServer().getOnlinePlayers()) {
      player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.getMaterial(item.toUpperCase()), amount) });
    }
  }
}