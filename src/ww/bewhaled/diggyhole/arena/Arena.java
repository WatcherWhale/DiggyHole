package ww.bewhaled.diggyhole.arena;

import org.bukkit.Material;
import ww.bewhaled.diggyhole.Main;

import java.util.ArrayList;
import java.util.Random;

public class Arena
{
    private Main plugin;

    private String name;
    private Region region;

    private ArrayList<DHPlayer> players;

    private boolean started = false;

    public Arena(Main pl, String name, Region region)
    {
        this.plugin = pl;
        this.name = name;
        this.region = region;
        this.players = new ArrayList<>();
    }

    public void StartGame()
    {
        started = true;

        //Teleport
    }

    private void BuildBlock()
    {
        Random rand = new Random();
        for (int x = region.getMin().getX(); x < region.getMax().getX();x++)
        {
            for (int z = region.getMin().getZ(); z < region.getMax().getZ();z++)
            {
                for (int y = region.getMin().getY(); y < region.getMax().getY();y++)
                {
                    //Set the top to grass blocks
                    if(y == region.getMax().getY())
                    {
                        this.region.world.getBlockAt(x,y,z).setType(Material.GRASS_BLOCK);
                    }
                    else if(x == region.getMax().getX() || x == region.getMin().getX() ||
                            z == region.getMax().getZ() || z == region.getMin().getZ())
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

}
