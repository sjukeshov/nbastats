package utils;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.By;
import ui.MainStatsPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.WebDriverRunner.url;

public class Utils {
    private List<Map<String, String>> userTable = null;
    private static final By COMBOBOX_SEASON_TYPE = By.cssSelector("select[name='SeasonType']");

    public long getLoadtime() {
        return executeJavaScript("return performance.timing.loadEventEnd - performance.timing.navigationStart;");
    }

    public boolean isMainContentCentralized(int centerOfPage) {
        $(MainStatsPage.DIV_MAIN_CONTENT).should(exist).shouldBe(visible);
        int centerOfMainContent = getCenterOfMainContent();
        return (centerOfPage - 1) <= centerOfMainContent && (centerOfPage + 1) >= centerOfMainContent;
    }

    private int getCenterOfMainContent() {
        int xCoordinateOfContent = $(MainStatsPage.DIV_MAIN_CONTENT).getLocation().getX();
        int contentWidth = $(MainStatsPage.DIV_MAIN_CONTENT).getSize().getWidth();
        return xCoordinateOfContent + contentWidth / 2;
    }

    public List<Map<Integer, String>> convertHeaderlessTableIntoHashMap(SelenideElement tableElement) {
        List<Map<Integer, String>> userHeaderlessTable = new ArrayList<>();
            // iterate through all rows and add their content to table array
            ElementsCollection rowsCollection = tableElement.$$("tbody tr");
            for (SelenideElement rowElement : rowsCollection) {
                int columnIndex = 0;
                ElementsCollection tdElements = rowElement.$$("td");
                HashMap<Integer, String> row = new HashMap<>();
                for (SelenideElement tdElement : tdElements) {
                    row.put(columnIndex, tdElement.getText());
                    columnIndex++;
                }
                userHeaderlessTable.add(row);
            }
        return userHeaderlessTable;
    }

    public List<Map<String, String>> convertTableIntoMap(SelenideElement tableElement) {
        if(userTable == null) {
            userTable = new ArrayList<>();
            // get column names of table from table headers
            ArrayList<String> columnNames = new ArrayList<>();
            ElementsCollection headerElements = tableElement.$$("thead tr th");
            for (SelenideElement headerElement : headerElements) {
                columnNames.add(headerElement.getText());
            }

            // iterate through all rows and add their content to table array
            ElementsCollection rowsCollection = tableElement.$$("tbody tr");

            for (SelenideElement rowElement : rowsCollection) {
                int columnIndex = 0;
                ElementsCollection tdElements = rowElement.$$("td");
                HashMap<String, String> row = new HashMap<>();
                for (SelenideElement tdElement : tdElements) {
                    row.put(columnNames.get(columnIndex), tdElement.getText());
                    columnIndex++;
                }
                userTable.add(row);
            }
        }
        return userTable;
    }

    public static void selectSeasonTypeStats(String seasonType) {
        //I had to add try to catch StaleElementReferenceException which is coming 1 out of 5 test runs even if this combobox is in place
        try {
            if (!$(COMBOBOX_SEASON_TYPE).getSelectedText().contains(seasonType)) {
                $(COMBOBOX_SEASON_TYPE).selectOptionContainingText(seasonType);
            }
        }
        catch(ElementNotFound error){
            System.out.println("Season Type combobox stale again: " + error);
        }
    }

    public static String getCurrentURL() {
        return url();
    }
}
