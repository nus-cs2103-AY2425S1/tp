package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.AddressBookStatistics;

import java.util.logging.Logger;

public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsPanel.class);
    private AddressBookStatistics addressBookStatistics;

    @FXML
    private GridPane statisticsGridPane;

    public StatisticsPanel(AddressBookStatistics addressBookStatistics) {
        super(FXML);
        this.addressBookStatistics = addressBookStatistics;
        updateStatistics();
    }

    public void updateStatistics() {
        statisticsGridPane.getChildren().clear(); // Clear existing charts

        // Add initial bar chart with data
        BarChart<Number, String> barChart = createBarChart("Address Book Statistics",
                addressBookStatistics);
        statisticsGridPane.add(barChart, 0, 1); // Add at column 0, row 1

        // Add mock additional charts to demonstrate scrollable, grid layout
        for (int i = 2; i <= 6; i++) { // Mock 5 additional charts
            BarChart<Number, String> mockChart = createBarChart("Chart " + i, addressBookStatistics);
            int column = (i - 1) % 2; // Alternate between 0 and 1 for 2-column layout
            int row = (i - 1) / 2 + 1; // Increase row every 2 charts
            statisticsGridPane.add(mockChart, column, row);
        }
    }

    private BarChart<Number, String> createBarChart(String title, AddressBookStatistics stats) {
        CategoryAxis yAxis = new CategoryAxis();
        NumberAxis xAxis = new NumberAxis();
//        xAxis.setLabel("Categories");
//        yAxis.setLabel("Values");

        BarChart<Number, String> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);
        barChart.setPrefWidth(400);

        XYChart.Series<Number, String> series = new XYChart.Series<>();
//        series.setName("Data Series");

        series.getData().add(new XYChart.Data<>(stats.getTotalSalesRevenue(), "Sales Revenue"));
        series.getData().add(new XYChart.Data<>(stats.getTotalPurchaseExpense(), "Purchase Expense"));
        series.getData().add(new XYChart.Data<>(stats.getTotalPropertiesBought(), "Properties Bought"));
        series.getData().add(new XYChart.Data<>(stats.getTotalPropertiesSold(), "Properties Sold"));
        series.getData().add(new XYChart.Data<>(stats.getTotalPersons(), "Total Persons"));


        barChart.getData().add(series);

        // Apply CSS class for shadow effect
        barChart.getStyleClass().add("chart-container");

        // Set responsive scaling (50% of width or 2 columns)
        barChart.setMaxWidth(Double.MAX_VALUE); // Allows it to grow with grid column width
        GridPane.setHgrow(barChart, javafx.scene.layout.Priority.ALWAYS);

        return barChart;
    }

}