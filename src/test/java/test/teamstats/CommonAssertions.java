package test.teamstats;

import ui.PlayerPage;
import ui.PlayersTraditionalStatsPage;
import ui.TeamPage;
import utils.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonAssertions {
    static void checkTeamPlayer(int placing, TeamPage teamPage, PlayersTraditionalStatsPage playersTraditionalStatsPage, PlayerPage playerPage, String teamCity, String teamName) {
        assertTrue(teamPage.checkTeamPageOpened(teamCity, teamName));
        String playerName = playersTraditionalStatsPage.getPlayersTableColumnValue(placing, "PLAYERS");
        double ptsValue = playersTraditionalStatsPage.getPlayersPTSValue(placing);
        assertTrue(ptsValue >= 0, "PTS value " + ptsValue + " is incorrect in Teams Page stats for " + playerName);

        playersTraditionalStatsPage.clickOnPlayerLink(placing);
        Utils.selectSeasonTypeStats("Regular Season");
        double playerPagePTSValue = playerPage.getPtsValue();
        assertTrue(playerPagePTSValue >= 0, "PTS value " + ptsValue + " is incorrect in Players stats page for " + playerName);

        assertEquals(ptsValue, playerPagePTSValue,
                "PTS value in Team stats page was " + ptsValue + " which is not equal to " + playerName + " personal page value of " + playerPagePTSValue);
    }
}
