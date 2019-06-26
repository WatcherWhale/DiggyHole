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
            }
            else
            {
                this.plugin.getConfig().load(this.file);
            }
    
            this.SetDefaults();
            this.SaveConfig();
        }
        catch(Exception e)
        {
            //send message to server
        }
    }

    public void SetDefaults()
    {
        FileConfiguration config = this.plugin.getConfig();
    
        if(!config.isSet("MinPlayers")) config.set("MinPlayers",4);
        if(!config.isSet("Countdown"))  config.set("Countdown",15);
        if(!config.isSet("EffectTime")) config.set("EffectTime",500);
        if(!config.isSet("WinPoints"))  config.set("WinPoints",10);

        if(!config.isSet("chance.diamondMultiplier")) config.set("chance.diamondMultiplier",2.0);
        if(!config.isSet("chance.chance.coal")) config.set("chance.coal",1.0);
        if(!config.isSet("chance.iron"))        config.set("chance.iron",1.5);
        if(!config.isSet("chance.gold"))        config.set("chance.gold",1.0);
        if(!config.isSet("chance.emerald"))     config.set("chance.emerald",0.3);
        if(!config.isSet("chance.redstone"))    config.set("chance.redstone",0.5);
        if(!config.isSet("chance.lapis"))       config.set("chance.lapis",2.0);
        if(!config.isSet("chance.magma"))       config.set("chance.magma",0.3);
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
