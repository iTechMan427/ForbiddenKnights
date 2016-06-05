package org.ChrisYounkin.EndYearProject;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class EventController
  implements Listener
{
  private MainClass mainClass;
  private PlayerTeleport playerTeleport;
  private KillAll killAll;
  
  public EventController(MainClass mainClass)
  {
    mainClass.getServer().getPluginManager().registerEvents(this, mainClass);
    
    this.mainClass = mainClass;
    playerTeleport = new PlayerTeleport(mainClass);
    killAll = new KillAll(mainClass);
  }
  
  @EventHandler
  public void onEntityCombust(EntityCombustEvent event)
  {
    event.setCancelled(true);
  }
  
  @EventHandler
  public void onEntityTarget(EntityTargetEvent event)
  {
    if ((event.getTarget() instanceof Player)) {
      event.setCancelled(false);
    } else {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onPlayerLogin(PlayerLoginEvent event)
  {
    final Player player = event.getPlayer();
    
    mainClass.getServer().getScheduler().scheduleSyncDelayedTask(mainClass, new Runnable()
    {
      public void run()
      {
        player.getInventory().clear();
        if (mainClass.getRunningLevel())
        {
          player.teleport(mainClass.getLevelLocation());
          if (player.getGameMode() == GameMode.SPECTATOR)
          {
            player.sendMessage(ChatColor.GREEN + "You have joined as a spectator.");
            player.sendMessage(ChatColor.GOLD + "Please wait for the game to end or type " + ChatColor.RED + "/hub" + ChatColor.GOLD + " to go back to the hub.");
          }
          else if (player.getGameMode() == GameMode.ADVENTURE)
          {
            player.sendMessage(ChatColor.GREEN + "You have joined as a knight.");
            player.sendMessage(ChatColor.GOLD + "Kill the monsters to win!  Or type " + ChatColor.RED + "/hub" + ChatColor.GOLD + " to go back to the hub.");
            String[] arrayOfString;
            int j = (arrayOfString = mainClass.getPlayerItemsString().split(" ")).length;
            for (int i = 0; i < j; i++)
            {
              String item = arrayOfString[i];
              player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.valueOf(item.split(",")[0].toUpperCase()), Integer.parseInt(item.split(",")[1])) });
            }
          }
        }
        else
        {
          player.spigot().respawn();
          player.setHealth(20.0D);
          player.setFoodLevel(20);
          player.setGameMode(GameMode.ADVENTURE);
          playerTeleport.teleportWithBorder(player, mainClass.getSpawnLocation(), 100.0D);
          if (!mainClass.isServerHub())
          {
            player.sendMessage(ChatColor.GREEN + "You have joined the game que.");
            player.sendMessage(ChatColor.GOLD + "Please wait for the game to start or type " + ChatColor.RED + "/hub" + ChatColor.GOLD + " to go back to the hub.");
          }
          if (mainClass.isServerHub())
          {
            player.sendMessage(ChatColor.GREEN + "You have joined the hub.");
            player.sendMessage(ChatColor.GOLD + "Have a look around or enter a portal to join a game.");
            player.sendMessage(ChatColor.GOLD + "You can type " + ChatColor.RED + "/spawn" + ChatColor.GOLD + " if you get lost.");
            player.sendMessage(ChatColor.WHITE + "Map Credit: Bohtauri");
            player.sendMessage(ChatColor.GRAY + "http://www.planetminecraft.com/project/world-first-11-100-accurate-forbidden-city");
            player.sendMessage(ChatColor.WHITE + "Developer Credit: Chris Younkin");
            player.sendMessage(ChatColor.GRAY + "https://www.spigotmc.org/resources/forbidden-knights.24319");
          }
        }
      }
    }, mainClass.getDelay());
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event)
  {
    Player player = event.getPlayer();
    if ((!mainClass.getRunningLevel()) && (mainClass.isServerHub())) {
      event.setQuitMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.YELLOW + " left the hub.");
    }
    if ((!mainClass.getRunningLevel()) && (!mainClass.isServerHub())) {
      event.setQuitMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.YELLOW + " left the game que.");
    }
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event)
  {
    Player player = event.getPlayer();
    if ((!mainClass.getRunningLevel()) && (mainClass.isServerHub())) {
      event.setJoinMessage(ChatColor.YELLOW + player.getDisplayName() + " joined the hub");
    }
    if ((mainClass.getRunningLevel()) && (player.getGameMode() == GameMode.SPECTATOR)) {
      event.setJoinMessage(ChatColor.YELLOW + player.getDisplayName() + " joined the game as a spectator");
    } else if ((mainClass.getRunningLevel()) && (player.getGameMode() == GameMode.ADVENTURE)) {
      event.setJoinMessage(ChatColor.YELLOW + player.getDisplayName() + " joined the game as a knight");
    }
    if ((!mainClass.getRunningLevel()) && (!mainClass.isServerHub())) {
      event.setJoinMessage(ChatColor.YELLOW + player.getDisplayName() + " joined the game que");
    }
  }
  
  public void whenReload()
  {
    mainClass.getServer().getScheduler().scheduleSyncDelayedTask(mainClass, new Runnable()
    {
      public void run()
      {
        for (Player player : mainClass.getServer().getOnlinePlayers())
        {
          player.spigot().respawn();
          player.setHealth(20.0D);
          player.setFoodLevel(20);
          player.getInventory().clear();
          player.setGameMode(GameMode.ADVENTURE);
        }
        killAll.removeEntities();
        playerTeleport.teleportAllWithBorder(mainClass.getSpawnLocation(), 100.0D);
      }
    }, mainClass.getDelay());
  }
  
  @EventHandler
  void onPlayerDeath(PlayerDeathEvent event)
  {
    final Player player = event.getEntity();
    mainClass.getServer().getScheduler().scheduleSyncDelayedTask(mainClass, new Runnable()
    {
      public void run()
      {
        player.spigot().respawn();
        if (mainClass.getRunningLevel()) {
          player.teleport(mainClass.getLevelLocation());
        }
      }
    }, mainClass.getDelay());
  }
}