package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.IntStream;
import ui.LeadersStatsPage;
import ui.PlayerPage;
import utils.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *  https://docs.google.com/spreadsheets/d/1O98qGYwbqhGLZoUuARdoLkbdouyQekho_NmzuKQIjOc/edit?pli=1#gid=0
 *  TS002 TC007
 */
class LeadersStatsPageTest {
    private static LeadersStatsPage leadersStatsPage = new LeadersStatsPage();
    private PlayerPage playerPage = new PlayerPage();

    @BeforeAll
    static void beforeAll() {
        leadersStatsPage.open();
        leadersStatsPage.readPlayerTable();
    }

    @BeforeEach
    void openMainPage() {
        leadersStatsPage.open();
    }

    @DisplayName("Parametrized Test to check NBA leaders main stats")
    @ParameterizedTest(name = "Checking player number {index} in NBA")
    @MethodSource("playersRange")
    void checkLeadersStats(int placing) {
        String playerName = leadersStatsPage.getPlayersTableColumnValue(placing, "PLAYER");

        double ptsValue = leadersStatsPage.getPlayerTableDoubleValue(placing, "PTS");
        assertTrue(ptsValue > 0, "PTS value " + ptsValue + " is incorrect in Leaders Page stats for " + playerName);
        double astValue = leadersStatsPage.getPlayerTableDoubleValue(placing, "AST");
        assertTrue(astValue > 0, "AST value " + astValue + " is incorrect in Leaders Page stats for " + playerName);
        double rebValue = leadersStatsPage.getPlayerTableDoubleValue(placing, "REB");
        assertTrue(rebValue > 0, "REB value " + rebValue + " is incorrect in Leaders Page stats for " + playerName);

        leadersStatsPage.clickOnPlayerLink(placing);
        Utils.selectSeasonTypeStats("Regular Season");

        double playerPagePTSValue = playerPage.getPtsValue();
        assertTrue(playerPagePTSValue > 0, "PTS value " + playerPagePTSValue + " is incorrect at " + playerName + " personal page stats");
        double playerPageASTValue = playerPage.getAstValue();
        assertTrue(playerPageASTValue > 0, "AST value " + playerPageASTValue + " is incorrect at " + playerName + " personal page stats");
        double playerPageREBValue = playerPage.getRebValue();
        assertTrue(playerPageREBValue > 0, "REB value " + playerPageREBValue + " is incorrect at " + playerName + " personal page stats");

        assertEquals(ptsValue, playerPagePTSValue,
                "PTS value in Leaders stats page was " + ptsValue + " which is not equal to " + playerName + " personal page value of " + playerPagePTSValue);
        assertEquals(astValue, playerPageASTValue,
                "AST value in Leaders stats page was " + astValue + " which is not equal to " + playerName + " personal page value of " + playerPageASTValue);
        assertEquals(rebValue, playerPageREBValue,
                "REB value in Leaders stats page was " + rebValue + " which is not equal to " + playerName + " personal page value of " + playerPageREBValue);
    }

    static IntStream playersRange() {
        return IntStream.range(1, 4);
    }
}
