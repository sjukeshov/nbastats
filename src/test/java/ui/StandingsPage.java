package ui;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import utils.Utils;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class StandingsPage {
    private static final String URL = "https://stats.nba.com/standings/";
    private static final By TABLE_STANDINGS_EAST = By.cssSelector("[data-title='East'] .nba-stat-table__overflow table");
    private static final By TABLE_STANDINGS_WEST = By.cssSelector("[data-title='West'] .nba-stat-table__overflow table");
    private static final By LINKS_EAST_TEAM_PAGE = By.cssSelector("[data-title='East'] .nba-stat-table__overflow a");
    private static final By LINKS_WEST_TEAM_PAGE = By.cssSelector("[data-title='West'] .nba-stat-table__overflow a");
    private Utils utils = new Utils();

    public void open() {
        Selenide.open(URL);
    }

    public void clickOnEastTeamPlacedIn(int placing) {
        $$(StandingsPage.LINKS_EAST_TEAM_PAGE).get(placing - 1).click();
    }

    public void clickOnWestTeamPlacedIn(int placing) {
        $$(StandingsPage.LINKS_WEST_TEAM_PAGE).get(placing - 1).click();
    }

    public String westTableColumnValue(int placing, String columnName) {
        return getColumnValue(placing, columnName, TABLE_STANDINGS_WEST);
    }

    public String eastTableColumnValue(int placing, String columnName) {
        return getColumnValue(placing, columnName, TABLE_STANDINGS_EAST);
    }

    public int getEastTableWinsValue(int placing) {
        return Integer.parseInt(eastTableColumnValue(placing, "W"));
    }

    public int getWestTableWinsValue(int placing) {
        return Integer.parseInt(westTableColumnValue(placing, "W"));
    }

    private String getColumnValue(int placing, String columnName, By tablePath) {
        List<Map<String, String>> userTable = utils.convertTableIntoMap($(tablePath));
        return userTable.get(placing-1).get(columnName);
    }
}
