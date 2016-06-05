package org.ChrisYounkin.EndYearProject;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class GameController
  implements Listener
{
  private MainClass mainClass;
  private PlayerFail playerFail;
  private SpawnEntities spawnEntities;
  private PlayerTeleport playerTeleport;
  private TextRunner runText;
  private KillAll killAll;
  private EndGame endGame;
  private PlayerModifier playerModifier;
  
  public GameController(MainClass mainClass)
  {
    this.mainClass = mainClass;
    
    playerTeleport = new PlayerTeleport(mainClass);
    spawnEntities = new SpawnEntities(mainClass);
    killAll = new KillAll(mainClass);
    playerFail = new PlayerFail(mainClass);
    endGame = new EndGame(mainClass);
    playerModifier = new PlayerModifier(mainClass);
    
    removeEntities();
  }
  
  public String getPlayerNames(Player player)
  {
    if (mainClass.getServer().getOnlinePlayers().size() > 1) {
      return "Knights";
    }
    return player.getDisplayName();
  }
  
  public void healPlayers()
  {
    playerModifier.healPlayers();
  }
  
  public void clearPlayerInventories()
  {
    playerModifier.clearPlayerInventories();
  }
  
  public void giveItemsToPlayers(String item)
  {
    playerModifier.giveItemsToPlayers(item.split(",")[0], Integer.parseInt(item.split(",")[1]));
  }
  
  public void chapterEndRunner(String text)
  {
    endGame.chapterEndRunner(text);
  }
  
  public void textRunner(String[] args, String name)
  {
    runText = new TextRunner(mainClass);
    runText.runText(playerFail, args, name);
  }
  
  public boolean getTextDone()
  {
    return runText.getTextDone();
  }
  
  public void teleportWithBorder(Player player, Location location, double borderSize)
  {
    playerTeleport.teleportWithBorder(player, location, borderSize);
  }
  
  public void teleportAllWithBorder(Location location, double borderSize)
  {
    playerTeleport.teleportAllWithBorder(location, borderSize);
  }
  
  public boolean getIfAllDead()
  {
    return spawnEntities.getIfAllDead();
  }
  
  public void spawnMobs(String mobString, Location location, int amount)
    throws Exception
  {
	spawnEntities.spawnMobs(mobString, location, amount);
  }
  
  public void spawnTrainerNPC(Location location)
    throws Exception
  {
	spawnEntities.spawnTrainerNPC(location);
  }
  
  public int removeEntities()
  {
    return killAll.removeEntities();
  }
  
  public boolean getFailed()
  {
    return playerFail.getFailed();
  }
}