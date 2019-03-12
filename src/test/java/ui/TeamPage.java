package ui;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TeamPage {
    private static final By SPANS_WIN_LOSSES = By.cssSelector(".stats-team-summary__value span");
    private static final By LABEL_TEAM_CITY = By.cssSelector(".stats-team-summary__city");
    private static final By LABEL_TEAM_NAME = By.cssSelector(".stats-team-summary__name");

    public int getWinsValue() {
        String winLostValue = $$(TeamPage.SPANS_WIN_LOSSES).get(0).getText();
        return Integer.parseInt(winLostValue.substring(0, winLostValue.indexOf("-")).trim());
    }

    public boolean checkTeamPageOpened (String teamCity, String teamName) {
        return $(LABEL_TEAM_CITY).getText().contains(teamCity) &&
                $(LABEL_TEAM_NAME).getText().contains(teamName);
    }
}
