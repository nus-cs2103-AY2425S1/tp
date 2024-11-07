package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * The ChartWindow. Provides the basic application layout containing
 * a bar chart that displays the hours of each person.
 */
public class ChartWindow extends UiPart<Region> {
    private static final String FXML = "ChartWindow.fxml";

    private static final Logger logger = LogsCenter.getLogger(ChartWindow.class);

    private static final int BAR_WIDTH = 50; // Width of each bar in pixels
    private static final int MAX_BARS_BEFORE_SCROLL = 30; // Threshold for enabling scrolling

    private final Person[] chartData;

    @FXML
    private ScrollPane chartScrollPane;

    @FXML
    private BarChart<String, Number> barChart;

    /**
     * Constructor for ChartWindow.
     *
     * @param chartData Array of Person objects to be displayed in the chart.
     */
    public ChartWindow(Person[] chartData) {
        super(FXML);
        this.chartData = chartData;
        configureScrollPane();
        populateChart();
    }

    /**
     * Configures the scroll pane to allow horizontal scrolling if necessary.
     */
    private void configureScrollPane() {
        int chartWidth;

        // Set width dynamically based on the number of bars
        if (chartData.length > MAX_BARS_BEFORE_SCROLL) {
            chartWidth = BAR_WIDTH * chartData.length;
        } else {
            // Set a minimum width for the chart when there are fewer bars
            chartWidth = 500; // Adjust this value as needed
        }

        barChart.setMinWidth(chartWidth);
        barChart.setPrefWidth(chartWidth);

        chartScrollPane.setContent(barChart);
        chartScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    /**
     * Populates the BarChart with data from the Person array.
     */
    private void populateChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Tutor Hours Chart");

        // Iterate over the Person array to create chart data entries.
        for (int i = 0; i < chartData.length; i++) {
            Person person = chartData[i];
            Name name = person.getName();
            int hours = person.getHours().getHoursInt();

            // Create a unique label using the index or an ID
            String uniqueLabel = name.fullName + " (" + i + ")";

            // Add each person's unique label and hours to the chart
            series.getData().add(new XYChart.Data<>(uniqueLabel, hours));
        }

        // Clear previous data and add the new series to the chart.
        barChart.getData().clear();
        barChart.getData().add(series);
    }
}
