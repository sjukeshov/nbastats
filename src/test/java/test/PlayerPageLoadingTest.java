package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ui.MainStatsPage;
import ui.PlayerPage;
import utils.Utils;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 *  https://docs.google.com/spreadsheets/d/1O98qGYwbqhGLZoUuARdoLkbdouyQekho_NmzuKQIjOc/edit?pli=1#gid=0
 *  TS002 TC008
 */
class PlayerPageLoadingTest {
    private static final Utils utils = new Utils();
    private static final int EXPECTED_LOAD_TIME = 4000;
    private MainStatsPage mainStatsPage = new MainStatsPage();
    private PlayerPage playerPage = new PlayerPage();

    @BeforeEach
    void openMainPage() {
        MainStatsPage.open();
    }

    @DisplayName("Parametrized Test to check load time of greatest NBA players")
    @ParameterizedTest(name="Run {index}: playerName={0}, playerSurname={1}")
    @MethodSource("checkPlayersPageLoadingTimeParameters")
    void checkPlayersPageLoadingTime(String playerName, String playerSurname) {
        mainStatsPage.openPlayersPage(playerName + " " + playerSurname);
        assertEquals(playerName, playerPage.getPlayerName(), "Name in caption doesn't fit");
        assertEquals(playerSurname, playerPage.getPlayerSurname(), "Surname in caption doesn't fit");
        assertTrue(utils.getLoadtime() < EXPECTED_LOAD_TIME, "Load time of the page was " + utils.getLoadtime() + " ms which is too long");
    }

    static Stream<Arguments> checkPlayersPageLoadingTimeParameters() {
        return Stream.of(
                Arguments.of("Michael", "Jordan"),
                Arguments.of("Kobe", "Bryant"),
                Arguments.of("Magic", "Johnson")
        );
    }
}
