package ww.bewhaled.diggyhole.arena;

import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import ww.bewhaled.diggyhole.Main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class Arena
{
    private Main plugin;

    private String name;

    private Region region;
    private Location lobby;
    private Location arena;

    private HashMap<String,DHPlayer> players;
    private ScoreBoardHandler scoreboard;

    private boolean started = false;

    private int taskID;
    private int secondsLeft;
    private boolean blockBuilt;

    private ArrayList<Double> chances;

    //region Constructors

    public Arena(Main pl, String name, Region region, Location lobby, Location arena)
    {
        this.plugin = pl;
        this.players = new HashMap<>();

        this.name = name;
        this.region = region;
        this.lobby = lobby;
        this.arena = arena;

        this.scoreboard = new ScoreBoardHandler(pl,this);
    }

    public Arena(Main pl, String name, Region region)
    {
        this.plugin = pl;
        this.players = new HashMap<>();

        this.name = name;
        this.region = region;

        this.scoreboard = new ScoreBoardHandler(pl,this);
    }

    public Arena(Main pl, YamlConfiguration config)
    {
        this.plugin = pl;
        this.players = new HashMap<>();

        this.name = config.getString("name");
        this.lobby = (Location) config.get("lobby");
        this.arena = (Location) config.get("arena");

        ArrayList<Object> max = (ArrayList<Object>)config.get("region.max");
        ArrayList<Object> min = (ArrayList<Object>)config.get("region.min");

        int[] maxArr = new int[3];
        int[] minArr = new int[3];

        for(int i = 0; i < maxArr.length; i++)
        {
            maxArr[i] = (int)max.get(i);
            minArr[i] = (int)min.get(i);
        }

        this.region = new Region(this.arena.getWorld(),minArr,maxArr);

        this.scoreboard = new ScoreBoardHandler(pl,this);
    }

    //endregion

    //region Game Handling

    public void PlayerJoined(Player player)
    {
        if(this.started)
        {
            player.sendMessage(ChatColor.GREEN + "[Diggy Hole]" +
                    ChatColor.RED + " Game already started!");
            return;
        }

        DHPlayer dhp = new DHPlayer(player,this.name);
        players.put(player.getName(),dhp);

        this.scoreboard.SetScoreBoard(player);
        this.scoreboard.SetScore(player,dhp.getPoints());

        player.teleport(this.arena);

        player.sendMessage(ChatColor.GREEN + "[Diggy Hole]" +
                ChatColor.WHITE + " Joined " + this.name + "!");

        this.PreparePlayer(player);

        if(this.players.size() == this.plugin.getConfig().getInt("MinPlayers"))
        {
            this.StartCountDown();
        }
    }

    public void PlayerLeft(Player player)
    {
        DHPlayer dhp = players.get(player.getName());
        dhp.RevertBack();

        this.scoreboard.RemoveScoreBoard(player);

        players.remove(player.getName());

        player.sendMessage(ChatColor.GREEN + "[Diggy Hole]" +
                ChatColor.WHITE + " You left the game!");

        if(this.players.size() < this.plugin.getConfig().getInt("MinPlayers"))
        {
            if(!this.started)
            {
                StopCountDown(false);
            }
            else if(this.players.size() == 1)
            {
                FinishGame((Player)this.players.values().toArray()[0]);
            }
            else if(this.players.size() == 0)
            {
                FinishGame(null);
            }
        }
    }

    private void StartCountDown()
    {
        this.blockBuilt = false;
        this.BuildWall();

        for(DHPlayer dhp : this.players.values())
        {
            Player player = dhp.getPlayer();
            player.teleport(this.arena);
        }

        this.secondsLeft = this.plugin.getConfig().getInt("Countdown");

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        this.taskID = scheduler.scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            @Override
            public void run()
            {
                if(secondsLeft == 0)
                {
                    Broadcast(ChatColor.GOLD + "DIIIIGGG!!!");
                    StopCountDown(true);
                    StartGame();
                }
                else if(secondsLeft <= 5 && !blockBuilt)
                {
                    BuildBlock();
                    started = true;
                }
                else if(secondsLeft%5 == 0 || secondsLeft <= 5)
                {
                    Broadcast(ChatColor.GREEN + Integer.toString(secondsLeft) + "...");
                }

                secondsLeft--;
            }
        },0L,20L);

    }

    private void StopCountDown(boolean safe)
    {
        if(!safe)
        {
            this.Broadcast(ChatColor.GREEN + "[Diggy Hole]" + ChatColor.RED + "Countdown Cancelled!");

            for(DHPlayer player : this.players.values())
            {
                player.getPlayer().teleport(this.lobby);
            }
        }

        Bukkit.getServer().getScheduler().cancelTask(this.taskID);
    }

    private void StartGame()
    {
        this.RemoveWall();
        this.started = true;


        ItemStack pick = getPickaxe();

        for(DHPlayer dhp : this.players.values())
        {
            Player player = dhp.getPlayer();

            this.PreparePlayer(player);
            player.getInventory().setItemInMainHand(pick);

            //player.teleport(this.arena);
        }
    }

    public void FinishGame(Player winner)
    {
        if(winner != null)
        {
            for (DHPlayer player : this.players.values())
            {
                if (player.getPlayer() != winner)
                {
                    player.getPlayer().sendMessage(ChatColor.RED + winner.getName() + ChatColor.GOLD + " Won the game");
                }
                else
                {
                    player.getPlayer().sendMessage(ChatColor.GOLD + "You won the game");
                }
    
                player.RevertBack();
                this.scoreboard.RemoveScoreBoard(player.getPlayer());
            }
        }
        else
        {
            for (DHPlayer player : this.players.values())
            {
                player.RevertBack();
            }
        }
    
        this.players.clear();
        this.started = false;
    }

    //endregion

    //region Build

    private void BuildBlock()
    {
        this.CalculateChances();

        Random rand = new Random();
        for (int x = region.getMin()[0]; x <= region.getMax()[0];x++)
        {
            for (int z = region.getMin()[2]; z <= region.getMax()[2];z++)
            {
                for (int y = region.getMin()[1]; y <= region.getMax()[1] + 1;y++)
                {
                    //Set the top to grass blocks
                    if(y == region.getMax()[1] + 1)
                    {
                        this.region.getWorld().getBlockAt(x,y,z).setType(Material.GRASS_BLOCK);
                    }
                    else if(x == region.getMax()[0] || x == region.getMin()[0] ||
                            z == region.getMax()[2] || z == region.getMin()[2])
                    {
                        this.region.getWorld().getBlockAt(x,y,z).setType(Material.STONE);
                    }
                    else
                    {
                        this.region.getWorld().getBlockAt(x,y,z).setType(this.RandomMaterial(rand));
                    }
                }
            }
        }

        this.blockBuilt = true;
    }

    private void BuildWall()
    {
        for (int x = region.getMin()[0] - 1; x <= region.getMax()[0] + 1;x++)
        {
            for (int z = region.getMin()[2] - 1; z <= region.getMax()[2] + 1;z++)
            {
                for (int y = region.getMin()[1]; y <= region.getMax()[1];y++)
                {
                    if(x == region.getMin()[0] - 1 || x == region.getMax()[0] + 1)
                    {
                        this.region.getWorld().getBlockAt(x,y,z).setType(Material.BARRIER);
                    }
                    else if(z == region.getMin()[2] - 1 || z == region.getMax()[2] + 1)
                    {
                        this.region.getWorld().getBlockAt(x,y,z).setType(Material.BARRIER);
                    }
                }
            }
        }
    }

    private void RemoveWall()
    {
        for (int x = region.getMin()[0] - 1; x <= region.getMax()[0] + 1;x++)
        {
            for (int z = region.getMin()[2] - 1; z <= region.getMax()[2] + 1;z++)
            {
                for (int y = region.getMin()[1]; y <= region.getMax()[1];y++)
                {
                    if(x == region.getMin()[0] - 1 || x == region.getMax()[0] + 1)
                    {
                        this.region.getWorld().getBlockAt(x,y,z).setType(Material.AIR);
                    }
                    else if(z == region.getMin()[2] - 1 || z == region.getMax()[2] + 1)
                    {
                        this.region.getWorld().getBlockAt(x,y,z).setType(Material.AIR);
                    }
                }
            }
        }
    }

    private Material RandomMaterial(Random rand)
    {
        double r = rand.nextDouble();

        if(r <= chances.get(0))
        {
            return Material.DIAMOND_ORE;
        }
        else if(r <= chances.get(1))
        {
            return Material.REDSTONE_ORE;
        }
        else if(r <= chances.get(2))
        {
            return Material.COAL_ORE;
        }
        else if(r <= chances.get(3))
        {
            return Material.GOLD_ORE;
        }
        else if(r <= chances.get(4))
        {
            return Material.EMERALD_ORE;
        }
        else if(r <= chances.get(5))
        {
            return Material.IRON_ORE;
        }
        else if(r <= chances.get(6))
        {
            return Material.LAPIS_ORE;
        }

        return Material.STONE;
    }

    private void CalculateChances()
    {
        chances = new ArrayList<>();

        int winPoints = this.plugin.getConfig().getInt("WinPoints");
        int players = this.players.size();
        int volume = this.region.getVolume();

        double dOffset = this.plugin.getConfig().getDouble("chance.diamondOffset");

        double diamond = (winPoints*players + dOffset)/((double)volume);
        double redstone = this.plugin.getConfig().getDouble("chance.redstone")/100.0 + diamond;
        double coal = this.plugin.getConfig().getDouble("chance.coal")/100.0 + redstone;
        double gold = this.plugin.getConfig().getDouble("chance.gold")/100.0 + coal;
        double emerald = this.plugin.getConfig().getDouble("chance.emerald")/100.0 + gold;
        double iron = this.plugin.getConfig().getDouble("chance.iron")/100.0 + emerald;
        double lapis = this.plugin.getConfig().getDouble("chance.lapis")/100.0 + iron;

        chances.add(diamond);
        chances.add(redstone);
        chances.add(coal);
        chances.add(gold);
        chances.add(emerald);
        chances.add(iron);
        chances.add(lapis);
    }

    //endregion

    //region Effects

    public void ScorePoint(Player player)
    {
        DHPlayer dhp = FindPlayer(player);
        dhp.setPoints(dhp.getPoints() + 1);

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 20.0F);
        player.sendMessage(ChatColor.AQUA + "+1 Diamond!");

        this.scoreboard.SetScore(player,dhp.getPoints());

        if(dhp.getPoints() == this.plugin.getConfig().getInt("WinPoints"))
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
            player.removePotionEffect(effect.getType());
        }

        PotionEffect effect = new PotionEffect(PotionEffectType.NIGHT_VISION,Integer.MAX_VALUE,
                255,true,false);
        player.addPotionEffect(effect);
    }

    public void FastMining(Player player)
    {
        PotionEffect effect = new PotionEffect(PotionEffectType.FAST_DIGGING,this.plugin.getConfig().getInt("EffectTime"),
                5,true,false);
        player.addPotionEffect(effect);
    }

    //endregion

    private void Broadcast(String message)
    {
        for(DHPlayer player : players.values())
        {
            player.getPlayer().sendMessage(message);
        }
    }

    private void PreparePlayer(Player player)
    {
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();

        PotionEffect effect = new PotionEffect(PotionEffectType.NIGHT_VISION,Integer.MAX_VALUE,
                255,true,false);
        player.addPotionEffect(effect);
    }

    //region Getters and Setters

    private ItemStack getPickaxe()
    {
        ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE,1);

        ItemMeta meta = pick.getItemMeta();
        meta.addEnchant(Enchantment.DIG_SPEED,1000,true);
        meta.addEnchant(Enchantment.DURABILITY,1000,true);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Fast Pickaxe");

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "Find them diamonds!");
        lore.add(ChatColor.GREEN + "Stop looking start digging!");

        meta.setLore(lore);

        pick.setItemMeta(meta);

        return pick;
    }

    public DHPlayer FindPlayer(Player player)
    {
        return this.players.get(player.getName());
    }

    public String getName() {
        return name;
    }

    public Region getRegion()
    {
        return region;
    }

    public Collection<DHPlayer> getPlayers() {
        return players.values();
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

    //endregion
}
