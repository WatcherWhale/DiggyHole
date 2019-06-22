package ww.bewhaled.diggyhole;

import org.bukkit.configuration.InvalidConfigurationException;

import java.io.*;

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
}
