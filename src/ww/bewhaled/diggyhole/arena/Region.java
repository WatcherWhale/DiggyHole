package ww.bewhaled.diggyhole.arena;

import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.World;

public class Region
{
    int[] max,min;
    int height, width, length;
    World world;

    public Region(com.sk89q.worldedit.regions.Region region, World world)
    {
        this.length = region.getLength();
        this.height = region.getHeight();
        this.width = region.getWidth();
        this.max = new int[]{region.getMaximumPoint().getBlockX(),
                region.getMaximumPoint().getBlockY(),region.getMaximumPoint().getBlockZ()};
        this.min = new int[]{region.getMinimumPoint().getBlockX(),
                region.getMinimumPoint().getBlockY(),region.getMinimumPoint().getBlockZ()};

        this.world =  world;
    }

    public Region(World world, int[] min, int[] max, int width, int height, int length)
    {
        this.length = length;
        this.height = height;
        this.width = width;
        this.max = max;
        this.min = min;
        this.world = world;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int[] getMax() {
        return max;
    }

    public void setMax(int[] max) {
        this.max = max;
    }

    public int[] getMin() {
        return min;
    }

    public void setMin(int[] min) {
        this.min = min;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getVolume()
    {
        return this.width * this.height * this.length;
    }
}
