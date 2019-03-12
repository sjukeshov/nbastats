package test.teamstats;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.IntStream;

import ui.PlayerPage;
import ui.PlayersTraditionalStatsPage;
import ui.TeamPage;
import utils.Utils;

/**
 *  https://docs.google.com/spreadsheets/d/1O98qGYwbqhGLZoUuARdoLkbdouyQekho_NmzuKQIjOc/edit?pli=1#gid=0
 *  TS002 TC005
 */
class WarriorsPlayersStatsPageTest {
    private static PlayersTraditionalStatsPage playersTraditionalStatsPage = new PlayersTraditionalStatsPage();
    private static PlayerPage playerPage = new PlayerPage();
    private static TeamPage teamPage = new TeamPage();
    private static final String TEAM_CITY = "GOLDEN STATE";
    private static final String TEAM_NAME = "WARRIORS";
    private static String teamstatsURL;

    @BeforeAll
    static void openMainPage() {
        playersTraditionalStatsPage.open();
        //we don't need to keep search and open stats for team so following steps were added into BeforeAll also
        playersTraditionalStatsPage.openTeamStats(TEAM_CITY, TEAM_NAME);
        playersTraditionalStatsPage.openPlayersTraditionalStats();
        Utils.selectSeasonTypeStats("Regular Season");
        playersTraditionalStatsPage.readPlayerTable();
        teamstatsURL = Utils.getCurrentURL();
    }

    @BeforeEach
    void openTeamPage() {
        playersTraditionalStatsPage.openURL(teamstatsURL);
    }

    @DisplayName("Parametrized Test to check average points per game for every Golden State Warriors player")
    @ParameterizedTest(name = "Checking player #{index} stats from Golden State Warriors")
    @MethodSource("playersRange")
    void checkPTSValueForEveryPlayerInTheTeam(int placing) {
        CommonAssertions.checkTeamPlayer(placing, teamPage, playersTraditionalStatsPage, playerPage, TEAM_CITY, TEAM_NAME);
    }

    static IntStream playersRange() {
        return IntStream.range(1, playersTraditionalStatsPage.getPlayersCount());
    }
}
