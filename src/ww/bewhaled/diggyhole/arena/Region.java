package ww.bewhaled.diggyhole.arena;

import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.World;

public class Region
{
    BlockVector3 max,min;
    int height, width, length;
    World world;

    public Region(com.sk89q.worldedit.regions.Region region, World world)
    {
        this.length = region.getLength();
        this.height = region.getHeight();
        this.width = region.getWidth();
        this.max = region.getMaximumPoint();
        this.min = region.getMinimumPoint();

        this.world =  world;
    }

    public Region(World world, BlockVector3 min, BlockVector3 max, int width, int height, int length)
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

    public BlockVector3 getMax() {
        return max;
    }

    public void setMax(BlockVector3 max) {
        this.max = max;
    }

    public BlockVector3 getMin() {
        return min;
    }

    public void setMin(BlockVector3 min) {
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
