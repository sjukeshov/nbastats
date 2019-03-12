package test.teamstats;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ui.PlayerPage;
import ui.PlayersTraditionalStatsPage;
import ui.TeamPage;
import utils.Utils;

import java.util.stream.IntStream;

/**
 *  https://docs.google.com/spreadsheets/d/1O98qGYwbqhGLZoUuARdoLkbdouyQekho_NmzuKQIjOc/edit?pli=1#gid=0
 *  TS002 TC006
 */
class RocketsPlayersStatsPageTest {
    private static PlayersTraditionalStatsPage playersTraditionalStatsPage = new PlayersTraditionalStatsPage();
    private static PlayerPage playerPage = new PlayerPage();
    private static TeamPage teamPage = new TeamPage();
    private static final String TEAM_CITY = "HOUSTON";
    private static final String TEAM_NAME = "ROCKETS";
    private static String teamStatsURL;

    @BeforeAll
    static void openMainPage() {
        playersTraditionalStatsPage.open();
        //we don't need to keep search and open stats for team so following steps were added into BeforeAll also
        playersTraditionalStatsPage.openTeamStats(TEAM_CITY, TEAM_NAME);
        playersTraditionalStatsPage.openPlayersTraditionalStats();
        Utils.selectSeasonTypeStats("Regular Season");
        playersTraditionalStatsPage.readPlayerTable();
        teamStatsURL = Utils.getCurrentURL();
    }

    @BeforeEach
    void openTeamPage() {
        playersTraditionalStatsPage.openURL(teamStatsURL);
    }

    @DisplayName("Parametrized Test to check average points per game for every Houston Rockets player")
    @ParameterizedTest(name = "Checking player #{index} stats from Houston Rockets")
    @MethodSource("playersRange")
    void checkPTSValueForEveryPlayerInTheTeam(int placing) {
        CommonAssertions.checkTeamPlayer(placing, teamPage, playersTraditionalStatsPage, playerPage, TEAM_CITY, TEAM_NAME);
    }

    static IntStream playersRange() {
        return IntStream.range(1, playersTraditionalStatsPage.getPlayersCount());
    }
}
