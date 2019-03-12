package test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.logging.LogType;
import ui.MainStatsPage;
import utils.Utils;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.getWebDriverLogs;
import static org.junit.jupiter.api.Assertions.*;
/**
 *  https://docs.google.com/spreadsheets/d/1O98qGYwbqhGLZoUuARdoLkbdouyQekho_NmzuKQIjOc/edit?pli=1#gid=0
 *  TS001 TC001 TC002 TC003
 */
class MainStatsPageTest {
    private MainStatsPage mainStatsPage = new MainStatsPage();
    private static final Utils utils = new Utils();

    @BeforeEach
    void openMainPage() {
        MainStatsPage.open();
    }
/**
 *  https://docs.google.com/spreadsheets/d/1O98qGYwbqhGLZoUuARdoLkbdouyQekho_NmzuKQIjOc/edit?pli=1#gid=0
 *  TS001 TC001
 */
    @DisplayName("Parametrized Test to check for console errors from browser")
    @ParameterizedTest(name="Run {index}: linkHeader={0}")
    @MethodSource("linksAndTitlesParameters")
    void checkConsoleErrorsWarnings(By linkHeader) {
        mainStatsPage.clickOnElement(linkHeader);
        List<String> errorlogs = getWebDriverLogs(LogType.BROWSER, Level.SEVERE);
        if (!errorlogs.isEmpty())
            fail(errorlogs.toString());
    }
/**
 *  https://docs.google.com/spreadsheets/d/1O98qGYwbqhGLZoUuARdoLkbdouyQekho_NmzuKQIjOc/edit?pli=1#gid=0
 *  TS001 TC002
 */
    @DisplayName("Parametrized Test to check if main panel is located in center of the page, page titles and load time")
    @ParameterizedTest(name="Run {index}: linkHeader={0}, expectedTitle={1}")
    @MethodSource("linksAndTitlesParameters")
    void checkTitleLoadTimeAndCentralizedContent(By linkHeader, String title) {
        int xCoordinateOfPageCenter = mainStatsPage.getXCoordinateOfPageCenter();
        int expectedLoadTime = 6000;
        mainStatsPage.clickOnElement(linkHeader);
        assertTrue(Selenide.title().contains(title));
        assertTrue(utils.getLoadtime() < expectedLoadTime, "Load time of the page was " + utils.getLoadtime() + " ms which is too long");
        assertTrue(utils.isMainContentCentralized(xCoordinateOfPageCenter));
    }

    static Stream<Arguments> linksAndTitlesParameters() {
        return Stream.of(
                Arguments.of(MainStatsPage.LINK_STATS_HOME, "NBA.com/Stats | NBA Stats"),
                Arguments.of(MainStatsPage.LINK_PLAYERS, "NBA.com/Stats | Season Leaders"),
                Arguments.of(MainStatsPage.LINK_TEAMS, "NBA.com/Stats | Teams"),
                Arguments.of(MainStatsPage.LINK_SCORES, "NBA.com/Stats | League Scoreboard By Day"),
                Arguments.of(MainStatsPage.LINK_SCHEDULE, "NBA.com/Stats | League Schedule")
                //I had to disable following checks cause NBA change All Time Leaders into All Star 2019 and took out Advanced Leaders link
                //Arguments.of(MainStatsPage.LINK_ADVANCED, "NBA.com/Stats | Players Advanced Leaders"),
                //Arguments.of(MainStatsPage.LINK_ALL_TIME_LEADERS, "NBA.com/Stats | All Time Leaders")
        );
    }
/**
 *  https://docs.google.com/spreadsheets/d/1O98qGYwbqhGLZoUuARdoLkbdouyQekho_NmzuKQIjOc/edit?pli=1#gid=0
 *  TS001 TC003
 */
    @Test
    @DisplayName("Test to check if Home Panels are in place and have stats in right order")
    void checkStatsHomePanelsExistence() {
        assertEquals(mainStatsPage.getBlocksCount(MainStatsPage.BLOCKS_STATS),9);
        assertEquals(mainStatsPage.getBlocksCount(MainStatsPage.TABLES_STATS),9);

        //I'm not using parametrized test here cause there is no need to navigate to a different URL, all data is on 1 page
        mainStatsPage.checkScoresAreSortedProperly(MainStatsPage.HEADER_POINTS, 0, "Points");
        mainStatsPage.checkScoresAreSortedProperly(MainStatsPage.HEADER_REBOUNDS, 1, "Rebounds");
        mainStatsPage.checkScoresAreSortedProperly(MainStatsPage.HEADER_ASSISTS, 2, "Assists");
        mainStatsPage.checkScoresAreSortedProperly(MainStatsPage.HEADER_BLOCKS, 3, "Blocks");
        mainStatsPage.checkScoresAreSortedProperly(MainStatsPage.HEADER_STEALS, 4, "Steals");
        mainStatsPage.checkScoresAreSortedProperly(MainStatsPage.HEADER_TURNOVERS, 5, "Turnovers");
        mainStatsPage.checkScoresAreSortedProperly(MainStatsPage.HEADER_THREE_POINTERS_MADE, 6, "Three Pointers Made");
        mainStatsPage.checkScoresAreSortedProperly(MainStatsPage.HEADER_FREE_THROWS_MADE, 7, "Free Throws Made");
        mainStatsPage.checkScoresAreSortedProperly(MainStatsPage.HEADER_FANTASY_POINTS, 8, "Fantasy Points");
    }


}
