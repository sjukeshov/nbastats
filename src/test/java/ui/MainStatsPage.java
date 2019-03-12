package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import utils.Utils;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainStatsPage {
    private static final String URL = "https://stats.nba.com/";
    private static final By PAGE_MAIN = By.cssSelector("body");
    public static final By DIV_MAIN_CONTENT = By.cssSelector("main");

    public static final By HEADER_POINTS = By.cssSelector("h2 a#leaders_daily_players__PTS");
    public static final By HEADER_REBOUNDS = By.cssSelector("h2 a#leaders_daily_players__REB");
    public static final By HEADER_ASSISTS = By.cssSelector("h2 a#leaders_daily_players__AST");
    public static final By HEADER_BLOCKS = By.cssSelector("h2 a#leaders_daily_players__BLK");
    public static final By HEADER_STEALS = By.cssSelector("h2 a#leaders_daily_players__STL");
    public static final By HEADER_TURNOVERS = By.cssSelector("h2 a#leaders_daily_players__TOV");
    public static final By HEADER_THREE_POINTERS_MADE = By.cssSelector("h2 a#leaders_daily_players__FG3M");
    public static final By HEADER_FREE_THROWS_MADE = By.cssSelector("h2 a#leaders_daily_players__FTM");
    public static final By HEADER_FANTASY_POINTS = By.cssSelector("h2 a#leaders_daily_players__NBA_FANTASY_PTS");

    public static final By LINK_STATS_HOME = By.cssSelector(".nav-container a[href='/']");
    public static final By LINK_PLAYERS = By.cssSelector(".nav-container a[href='/players/']");
    public static final By LINK_TEAMS = By.cssSelector(".nav-container a[href='/teams/']");
    public static final By LINK_ADVANCED = By.cssSelector(".nav-container a[href='/players/advanced-leaders/']");
    public static final By LINK_SCORES = By.cssSelector(".nav-container a[href='/scores/']");
    public static final By LINK_SCHEDULE = By.cssSelector(".nav-container a[href='/schedule/']");
    public static final By LINK_ALL_TIME_LEADERS = By.cssSelector(".nav-container a[href='/alltime/']");

    public static final By BLOCKS_STATS = By.cssSelector("section[aria-hidden='false'][ng-show='isPlayersDaily']");
    public static final By TABLES_STATS = By.cssSelector("[ng-show='isPlayersDaily'] table");

    private static final By SPAN_SEARCH_TEXTBOX = By.cssSelector("span.stats-search__icon-text");
    private static final By TEXTBOX_SEARCH = By.cssSelector("input.stats-search__input");
    private static final By LINK_SEARCH_RESULT = By.cssSelector("a.stats-search__link-anchor");

    private Utils utils = new Utils();

    public static void open() {
        Configuration.browserSize = "1600x1200";
        Selenide.open(URL);
    }

    public void clickOnElement(By linkHeader) {
        $(linkHeader).click();
    }

    public void checkScoresAreSortedProperly(By headerTable, int tableOrder, String tableName) {
        $(headerTable).shouldBe(visible).shouldHave(Condition.text(tableName));
        SelenideElement tableStats = $$(TABLES_STATS).get(tableOrder);
        assertTrue(isTableSortedByValues(tableStats), tableName + " table is not sorted");
    }

    private boolean isTableSortedByValues(SelenideElement table) {
        double previousValue = getStatsTableValue(1, table);
        double currentValue;
        for (int i=2; i<6; i++) {
            currentValue = getStatsTableValue(i, table);
            if (currentValue > previousValue) return false;
            previousValue=currentValue;
        }
        return true;
    }

    public int getXCoordinateOfPageCenter() {
        return $(MainStatsPage.PAGE_MAIN).getSize().getWidth() / 2;
    }

    private double getStatsTableValue(int placing, SelenideElement table) {
        List<Map<Integer, String>> userTable = utils.convertHeaderlessTableIntoHashMap(table);
        return Double.parseDouble(userTable.get(placing-1).get(2));
    }

    public void openPlayersPage(String playerName) {
        $(SPAN_SEARCH_TEXTBOX).click();
        $(TEXTBOX_SEARCH).setValue(playerName);
        $(LINK_SEARCH_RESULT).click();
    }

    public int getBlocksCount(By blocks) {
        return $$(blocks).size();
    }
}
