package ww.bewhaled.diggyhole.arena;

import com.sk89q.worldedit.math.BlockVector3;

public class Region
{
    BlockVector3 max,min;
    int height, width;

    public Region(com.sk89q.worldedit.regions.Region region)
    {
        this.height = region.getHeight();
        this.width = region.getWidth();
        this.max = region.getMaximumPoint();
        this.min = region.getMinimumPoint();
    }

    public Region(BlockVector3 min,BlockVector3 max,int width, int height)
    {
        this.height = height;
        this.width = width;
        this.max = max;
        this.min = min;
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
}
