package ww.bewhaled.diggyhole.arena;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ww.bewhaled.diggyhole.Util;

import java.util.Collection;

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
    private PlayerInventory inv;
    private Collection<PotionEffect> effects;
    private GameMode gameMode;
    private Location location;

    public DHPlayerSave(Player player)
    {
        this.inv = (PlayerInventory) Util.Deepclone(player.getInventory());
        this.effects = (Collection<PotionEffect>)Util.Deepclone(player.getActivePotionEffects());
        this.gameMode = player.getGameMode();
        this.location = player.getLocation();
    }

    public void LoadSave(Player player)
    {
        this.RemoveEffects(player);

        //Restore the player save state
        player.getInventory().setContents(this.inv.getContents());
        player.getInventory().setArmorContents(this.inv.getArmorContents());
        player.getInventory().setItemInOffHand(this.inv.getItemInOffHand());

        player.teleport(this.location);
        player.setGameMode(this.gameMode);
        player.addPotionEffects(this.effects);
    }

    public void RemoveEffects(Player player)
    {
        for(PotionEffect effect : player.getActivePotionEffects())
        {
            player.removePotionEffect(effect.getType());
        }
    }
}