package ui;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class PlayerPage {
    private static final By SPAN_PLAYER_PTS_STAT = By.cssSelector(".player-stats__pts span");
    private static final By SPAN_PLAYER_AST_STAT = By.cssSelector(".player-stats__ast span");
    private static final By SPAN_PLAYER_REB_STAT = By.cssSelector(".player-stats__reb span");
    private static final By divPlayerName = By.cssSelector(".player-summary__first-name");
    private static final By divPlayerSurname = By.cssSelector(".player-summary__last-name");

    public double getPtsValue () {
        return Double.parseDouble($(SPAN_PLAYER_PTS_STAT).getText());
    }

    public double getAstValue () {
        return Double.parseDouble($(SPAN_PLAYER_AST_STAT).getText());
    }

    public double getRebValue () {
        return Double.parseDouble($(SPAN_PLAYER_REB_STAT).getText());
    }

    public String getPlayerName () {
        return $(divPlayerName).getText();
    }

    public String getPlayerSurname () {
        return $(divPlayerSurname).getText();
    }
}
