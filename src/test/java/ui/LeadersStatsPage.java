package ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import utils.Utils;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;

public class LeadersStatsPage {
    private static final String URL = "https://stats.nba.com/leaders/";
    private static final By TABLE_LEADERS_STATS = By.cssSelector("[rows='playerStats'] .nba-stat-table table");
    private static final By LINK_TO_PLAYER_PROFILE = By.cssSelector("td.player a");
    private Utils utils = new Utils();
    private List<Map<String, String>> userTable;

    public void open() {
        Selenide.open(URL);
        Utils.selectSeasonTypeStats("Regular Season");
    }

    public String getPlayersTableColumnValue(int order, String columnName) {
        return userTable.get(order-1).get(columnName);
    }

    public void readPlayerTable() {
        userTable = utils.convertTableIntoMap($(TABLE_LEADERS_STATS));
    }

    public double getPlayerTableDoubleValue(int order, String column) {
        return Double.parseDouble(getPlayersTableColumnValue(order, column));
    }

    public void clickOnPlayerLink(int placing) {
        SelenideElement table = $(TABLE_LEADERS_STATS);
        table.$$(LINK_TO_PLAYER_PROFILE).get(placing - 1).click();
    }
}
