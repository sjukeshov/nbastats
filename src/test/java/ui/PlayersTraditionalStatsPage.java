package ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import utils.Utils;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PlayersTraditionalStatsPage {
    private static final String URL = "https://stats.nba.com/players/traditional";
    private static final By SPAN_SEARCH_TEXTBOX = By.cssSelector("span.stats-search__icon-text");
    private static final By TEXTBOX_SEARCH = By.cssSelector("input.stats-search__input");
    private static final By LINK_SEARCH_RESULT = By.cssSelector("a.stats-search__link-anchor");
    private static final By LINK_OPTIONS_COMBO_BOX = By.cssSelector("a[ng-class='{ active: showBeta }']");
    private static final By LINK_COMBO_BOX_OPTION_PLAYERS = By.cssSelector("a[data-type-beta='players']");
    private static final By LINK_TO_PLAYER_PROFILE = By.cssSelector("td.first a");
    private static final By TABLES_PLAYERS_STATS = By.cssSelector("[rows='datasets.PlayersSeasonTotals.rows'] div table");
    private List<Map<String, String>> userTable;

    private Utils utils = new Utils();

    public void open() {
        Selenide.open(URL);
    }

    public void openURL(String teamStatsURL) {
        Selenide.open(teamStatsURL);
    }

    public void openTeamStats(String teamCity, String teamName) {
        $(SPAN_SEARCH_TEXTBOX).click();
        $(TEXTBOX_SEARCH).setValue(teamCity + " " + teamName);
        $(LINK_SEARCH_RESULT).click();
    }

    public void openPlayersTraditionalStats() {
        if(!$(LINK_OPTIONS_COMBO_BOX).getText().equals("Players")) {
            $(LINK_OPTIONS_COMBO_BOX).click();
            $(LINK_COMBO_BOX_OPTION_PLAYERS).click();
        }
    }

    public void clickOnPlayerLink(int placing) {
        SelenideElement table = $$(TABLES_PLAYERS_STATS).get(0);
        table.$$(LINK_TO_PLAYER_PROFILE).get(placing - 1).click();
    }

    public String getPlayersTableColumnValue(int order, String columnName) {
        return userTable.get(order-1).get(columnName);
    }

    public int getPlayersCount() {
        //I had to increment size cause if I want to start from 1 not 0 than count should be like that
        return userTable.size() + 1;
    }

    public double getPlayersPTSValue(int order) {
        return Double.parseDouble(getPlayersTableColumnValue(order, "PTS"));
    }

    public void readPlayerTable() {
        userTable = utils.convertTableIntoMap($$(TABLES_PLAYERS_STATS).get(0));
    }
}
