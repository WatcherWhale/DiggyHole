package ww.bewhaled.diggyhole;

import org.bukkit.configuration.file.YamlConfiguration;
import ww.bewhaled.diggyhole.arena.Arena;

import java.io.*;
import java.util.ArrayList;

public class ConfigHandler
{
    Main plugin;
    File file;

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
                this.Copy(this.plugin.getResource("config.yml"), this.file);

            }

            this.plugin.getConfig().load(this.file);
        }
        catch(Exception e)
        {
            //send message to server
        }
    }

    private void Copy(InputStream in, File file)
    {
        try
        {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        }
        catch (Exception e)
        {
            //e.printStackTrace();
        }
    }

    public void SaveArena(Arena ar)
    {
        try
        {
            File arenaDir = new File(this.plugin.getDataFolder() + "/arenas");
            if(!arenaDir.exists()) arenaDir.mkdir();

            File newConfig = new File(this.plugin.getDataFolder() + "/" + ar.getName() + ".yml");

            if(!newConfig.exists())
            {
                newConfig.createNewFile();
            }

            YamlConfiguration conf  = new YamlConfiguration();

            conf.set("name",ar.getName());
            conf.set("region",ar.getRegion());
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
