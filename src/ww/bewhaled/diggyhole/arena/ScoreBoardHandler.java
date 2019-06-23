package ww.bewhaled.diggyhole.arena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import ww.bewhaled.diggyhole.Main;

public class ScoreBoardHandler
{
    private Main plugin;
    private Arena arena;
    private ScoreboardManager man;
    private Scoreboard board;
    private Objective objSidebar;
    private Objective objName;

    public ScoreBoardHandler(Main pl, Arena ar)
    {
        this.plugin = pl;
        this.arena = ar;
        this.man = Bukkit.getScoreboardManager();
        this.board = this.man.getNewScoreboard();

        this.objSidebar = this.board.registerNewObjective("diamonds","dummy",
                    ChatColor.LIGHT_PURPLE + "Diamonds", RenderType.INTEGER);
        this.objSidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

        this.objName = this.board.registerNewObjective("diamondsTop","dummy",
                ChatColor.LIGHT_PURPLE + " Diamonds", RenderType.INTEGER);
        this.objName.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }

    public void SetScore(Player player, int points)
    {
        Score sideScore = this.objSidebar.getScore(ChatColor.GREEN + player.getName());
        sideScore.setScore(points);

        Score nameScore = this.objName.getScore(player.getName());
        nameScore.setScore(points);
    }

    public void SetScoreBoard(Player player)
    {
        player.setScoreboard(this.board);
    }

    public void RemoveScoreBoard(Player player)
    {
        player.setScoreboard(this.man.getNewScoreboard());
    }
}
