package ww.bewhaled.diggyhole.arena;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class DHPlayer
{
    private Player player;
    private int points;
    private String arena;

    private DHPlayerSave saveState;


    public DHPlayer(Player player, String arena)
    {
        this.player = player;
        this.arena = arena;
        this.points = 0;

        this.saveState = new DHPlayerSave(player);
    }

    public void RevertBack()
    {
        saveState.LoadSave(this.player);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getArena() {
        return arena;
    }

    public void setArena(String arena) {
        this.arena = arena;
    }
}

class DHPlayerSave
{
    private GameMode gameMode;
    private Location location;
    
    private ItemStack[] contents;
    private ItemStack[] armor;
    private float exp;
    
    private double[] health;
    private int food;
    private PotionEffect[] effects;

    public DHPlayerSave(Player player)
    {
        this.contents = player.getInventory().getContents();
        this.armor = player.getInventory().getArmorContents();
        
        this.effects = player.getActivePotionEffects().toArray(new PotionEffect[]{});
        this.gameMode = player.getGameMode();
        this.location = player.getLocation();
        
        this.exp = player.getLevel() + player.getExp();
        
        this.health = new double[] {player.getHealth(),player.getHealthScale()};
        this.food = player.getFoodLevel();
        
    }

    public void LoadSave(Player player)
    {
        this.RemoveEffects(player);
        
        //Restore the player save state
        player.getInventory().setContents(this.contents);
        player.getInventory().setArmorContents(this.armor);
        player.updateInventory();

        player.teleport(this.location);
        player.setGameMode(this.gameMode);
        
        for(PotionEffect pe : this.effects)
        {
            player.addPotionEffect(pe);
        }
        
        int levels = (int)Math.floor(this.exp);
        player.setLevel(levels);
        player.setExp(this.exp - levels);
        
        player.setHealth(this.health[0]);
        player.setHealthScale(this.health[1]);
        
        player.setFoodLevel(this.food);
        
    }

    public void RemoveEffects(Player player)
    {
        for(PotionEffect effect : player.getActivePotionEffects())
        {
            player.removePotionEffect(effect.getType());
        }
    }
}