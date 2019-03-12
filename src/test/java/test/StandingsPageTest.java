package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ui.StandingsPage;
import ui.TeamPage;
import utils.Utils;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 *  https://docs.google.com/spreadsheets/d/1O98qGYwbqhGLZoUuARdoLkbdouyQekho_NmzuKQIjOc/edit?pli=1#gid=0
 *  TS002 TC004
 */
class StandingsPageTest {
    private StandingsPage standingsPage;
    private TeamPage teamPage = new TeamPage();

    @BeforeEach
    void openMainPage() {
        standingsPage = new StandingsPage();
        standingsPage.open();
        Utils.selectSeasonTypeStats("Regular Season");
    }

    @DisplayName("Parametrized Test to check all eastern conference teams Wins value")
    @ParameterizedTest(name = "Checking team at #{index} place in East Conference")
    @MethodSource("teamsRange")
    void checkWinValueForEastTeam(int placing) {
        int winsValueFromStandingsTable = standingsPage.getEastTableWinsValue(placing);
        String teamName = standingsPage.eastTableColumnValue(placing, "TEAM");
        standingsPage.clickOnEastTeamPlacedIn(placing);

        int winsValueFromTeamPage = teamPage.getWinsValue();
        assertEquals(winsValueFromStandingsTable, winsValueFromTeamPage,
                "Wins value for " + teamName + " don't match in standings to team page");
    }

    static IntStream teamsRange() {
        return IntStream.range(1, 16);
    }

    @DisplayName("Parametrized Test to check all western conference teams Wins value")
    @ParameterizedTest(name = "Checking team at #{index} place in West Conference")
    @MethodSource("teamsRange")
    void checkWinValueForWestTeam(int placing) {
        int winsValueFromStandingsTable = standingsPage.getWestTableWinsValue(placing);
        String teamName = standingsPage.westTableColumnValue(placing, "TEAM");
        standingsPage.clickOnWestTeamPlacedIn(placing);

        int winsValueFromTeamPage = teamPage.getWinsValue();
        assertEquals(winsValueFromStandingsTable, winsValueFromTeamPage,
                "Wins value for " + teamName + " don't match in standings to team page");
    }
}
