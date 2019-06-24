package ww.bewhaled.diggyhole;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ww.bewhaled.diggyhole.arena.Arena;

import java.io.*;
import java.util.ArrayList;

public class ConfigHandler
{
    private Main plugin;
    private File file;

    public ConfigHandler(Main pl)
    {
        this.plugin = pl;
        this.file = new File(this.plugin.getDataFolder() + "/config.yml");

        try
        {
            if (!this.file.exists())
            {

                //Create the folder if it does not exist
                if (!this.plugin.getDataFolder().exists()) this.plugin.getDataFolder().mkdir();

                //Create the file
                this.file.createNewFile();
                this.plugin.getConfig().load(this.file);
                this.SetDefaults();
                this.SaveConfig();
            }
            else
            {
                this.plugin.getConfig().load(this.file);
            }
        }
        catch(Exception e)
        {
            //send message to server
        }
    }

    public void SetDefaults()
    {
        FileConfiguration config = this.plugin.getConfig();

        config.set("MinPlayers",4);
        config.set("Countdown",15);
        config.set("EffectTime",500);
        config.set("WinPoints",10);

        config.set("chance.diamondOffset",2.0);
        config.set("chance.coal",1.0);
        config.set("chance.iron",1.5);
        config.set("chance.gold",1.0);
        config.set("chance.emerald",0.3);
        config.set("chance.redstone",0.5);
        config.set("chance.lapis",2.0);
    }

    public void SaveConfig()
    {
        try
        {
            this.plugin.getConfig().save(this.file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void SaveArena(Arena ar)
    {
        try
        {
            File arenaDir = new File(this.plugin.getDataFolder() + "/arenas");
            if(!arenaDir.exists()) arenaDir.mkdir();

            File newConfig = new File(arenaDir.getPath() + "/" + ar.getName() + ".yml");

            if(!newConfig.exists())
            {
                newConfig.createNewFile();
            }

            YamlConfiguration conf  = new YamlConfiguration();

            conf.set("name",ar.getName());

            conf.set("region.max",ar.getRegion().getMax());
            conf.set("region.min",ar.getRegion().getMin());

            conf.set("lobby",ar.getLobby());
            conf.set("arena",ar.getArena());

            conf.save(newConfig);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public YamlConfiguration[] GetArenaConfigs()
    {
        try
        {
            File arenaDir = new File(this.plugin.getDataFolder() + "/arenas");
            if (!arenaDir.exists()) arenaDir.mkdir();

            ArrayList<YamlConfiguration> arenas = new ArrayList<>();

            for(File conf : arenaDir.listFiles())
            {
                YamlConfiguration config = new YamlConfiguration();
                config.load(conf);

                arenas.add(config);
            }

            return arenas.toArray(new YamlConfiguration[]{});

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return new YamlConfiguration[]{};
    }
}
