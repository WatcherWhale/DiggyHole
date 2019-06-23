package ww.bewhaled.diggyhole.arena;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ww.bewhaled.diggyhole.Main;

import java.util.ArrayList;
import java.util.Random;

public class Arena
{
    private Main plugin;

    private String name;

    private Region region;
    private Location lobby;
    private Location arena;

    private ArrayList<DHPlayer> players;

    private boolean started = false;

    public Arena(Main pl, String name, Region region, Location lobby, Location arena)
    {
        this.plugin = pl;
        this.players = new ArrayList<>();

        this.name = name;
        this.region = region;
        this.lobby = lobby;
        this.arena = arena;
    }

    public Arena(Main pl, String name, Region region)
    {
        this.plugin = pl;
        this.players = new ArrayList<>();

        this.name = name;
        this.region = region;
    }

    public Arena(Main pl, YamlConfiguration config)
    {
        this.plugin = pl;
        this.players = new ArrayList<>();

        this.name = config.getString("name");
        this.region = (Region) config.get("region");
        this.lobby = (Location) config.get("lobby");
        this.arena = (Location) config.get("arena");
    }

    public void PlayerJoined(Player player)
    {
        DHPlayer dhp = new DHPlayer(player,this.name);
        players.add(dhp);

        player.teleport(this.arena);

        player.sendMessage(ChatColor.GREEN + "[Diggy Hole]" +
                ChatColor.WHITE + " Joined " + this.name + "!");

        if(this.players.size() == this.plugin.getConfig().getInt("MinPlayers"))
        {
            StartGame();
        }
    }

    public void StartGame()
    {
        started = true;

        BuildBlock();

        for(DHPlayer dhp : this.players)
        {
            dhp.getPlayer().teleport(this.arena);
        }
    }

    private void BuildBlock()
    {
        Random rand = new Random();
        for (int x = region.getMin()[0]; x < region.getMax()[0];x++)
        {
            for (int z = region.getMin()[2]; z < region.getMax()[2];z++)
            {
                for (int y = region.getMin()[1]; y < region.getMax()[1];y++)
                {
                    //Set the top to grass blocks
                    if(y == region.getMax()[1])
                    {
                        this.region.world.getBlockAt(x,y,z).setType(Material.GRASS_BLOCK);
                    }
                    else if(x == region.getMax()[0] || x == region.getMin()[0] ||
                            z == region.getMax()[2] || z == region.getMin()[2])
                    {
                        this.region.world.getBlockAt(x,y,z).setType(Material.STONE);
                    }
                    else
                    {
                        this.region.world.getBlockAt(x,y,z).setType(this.RandomMaterial(rand));
                    }
                }
            }
        }
    }

    private Material RandomMaterial(Random rand)
    {
        double r = rand.nextDouble();

        double diamond = this.plugin.getConfig().getDouble("chance.diamond")/100.0;
        double redstone = this.plugin.getConfig().getDouble("chance.redstone")/100.0 + diamond;
        double coal = this.plugin.getConfig().getDouble("chance.coal")/100.0 + redstone;
        double gold = this.plugin.getConfig().getDouble("chance.gold")/100.0 + coal;
        double emerald = this.plugin.getConfig().getDouble("chance.emerald")/100.0 + gold;
        double iron = this.plugin.getConfig().getDouble("chance.iron")/100.0 + emerald;
        double lapis = this.plugin.getConfig().getDouble("chance.lapis")/100.0 + iron;

        if(r <= diamond)
        {
            return Material.DIAMOND_ORE;
        }
        else if(r <= redstone)
        {
            return Material.REDSTONE_ORE;
        }
        else if(r <= coal)
        {
            return Material.COAL_ORE;
        }
        else if(r <= gold)
        {
            return Material.GOLD_ORE;
        }
        else if(r <= emerald)
        {
            return Material.EMERALD_ORE;
        }
        else if(r <= iron)
        {
            return Material.IRON_ORE;
        }
        else if(r <= lapis)
        {
            return Material.LAPIS_ORE;
        }

        return Material.STONE;
    }

    public void FinishGame(Player winner)
    {
        for(DHPlayer player : this.players)
        {
            if(player.getPlayer() != winner)
            {
                player.getPlayer().sendMessage(ChatColor.RED + winner.getName() + ChatColor.GOLD + " Won the game");
            }
            else
            {
                player.getPlayer().sendMessage(ChatColor.GOLD + "You won the game");
            }

            player.getPlayer().performCommand("spawn");
        }

        players.clear();
        started = false;
    }

    public void ScorePoint(Player player)
    {
        DHPlayer dhp = FindPlayer(player);
        dhp.setPoints(dhp.getPoints() + 1);

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 20.0F);
        player.sendMessage(ChatColor.AQUA + "+1 Diamond!");

        if(dhp.getPoints() == 5)
        {
            FinishGame(player);
        }
    }

    public void Speed(Player player)
    {
        PotionEffect effect = new PotionEffect(PotionEffectType.SPEED,this.plugin.getConfig().getInt("EffectTime"),
                                        5,true,false);
        player.addPotionEffect(effect);
    }

    public void Slow(Player player)
    {
        PotionEffect effect1 = new PotionEffect(PotionEffectType.SLOW,this.plugin.getConfig().getInt("EffectTime"),
                5,true,false);
        PotionEffect effect2 = new PotionEffect(PotionEffectType.SLOW_DIGGING,this.plugin.getConfig().getInt("EffectTime"),
                5,true,false);
        player.addPotionEffect(effect1);
        player.addPotionEffect(effect2);
    }

    public void Blind(Player player)
    {
        PotionEffect effect = new PotionEffect(PotionEffectType.BLINDNESS,this.plugin.getConfig().getInt("EffectTime"),
                5,true,false);
        player.addPotionEffect(effect);
    }

    public void Confuse(Player player)
    {
        PotionEffect effect = new PotionEffect(PotionEffectType.CONFUSION,this.plugin.getConfig().getInt("EffectTime"),
                5,true,false);
        player.addPotionEffect(effect);
    }

    public void RemoveEffects(Player player)
    {
        for(PotionEffect effect : player.getActivePotionEffects())
        {
            if(effect.getType() != PotionEffectType.NIGHT_VISION)
            {
                player.removePotionEffect(effect.getType());
            }
        }
    }

    public void FastMining(Player player)
    {
        PotionEffect effect = new PotionEffect(PotionEffectType.FAST_DIGGING,this.plugin.getConfig().getInt("EffectTime"),
                5,true,false);
        player.addPotionEffect(effect);
    }

    public DHPlayer FindPlayer(Player player)
    {
        for(DHPlayer dhp : players)
        {
            if(dhp.getPlayer() == player) return dhp;
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public Region getRegion() {
        return region;
    }

    public ArrayList<DHPlayer> getPlayers() {
        return players;
    }

    public boolean isStarted() {
        return started;
    }

    public Location getLobby() {
        return lobby;
    }

    public Location getArena() {
        return arena;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public void setArena(Location arena) {
        this.arena = arena;
    }
}
