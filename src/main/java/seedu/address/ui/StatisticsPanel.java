package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.AddressBookStatistics;

import java.util.logging.Logger;

public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsPanel.class);

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private void initialize() {
        // Hardcoded data for the bar chart
        populateBarChart();
    }

    private void populateBarChart() {
        // Create a series for the bar chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sample Data");

        // Hardcoded data
        series.getData().add(new XYChart.Data<>("Category 1", 23));
        series.getData().add(new XYChart.Data<>("Category 2", 15));
        series.getData().add(new XYChart.Data<>("Category 3", 45));
        series.getData().add(new XYChart.Data<>("Category 4", 30));
        series.getData().add(new XYChart.Data<>("Category 5", 12));

        // Add the series to the bar chart
        barChart.getData().add(series);
    }

    @FXML
    private VBox statisticsVBox; // Use an appropriate layout here

    public StatisticsPanel(AddressBookStatistics addressBookStatistics) {
        super(FXML);
        // TODO: Initialize statistics view here with data from the addressBookStatistics
    }
}
