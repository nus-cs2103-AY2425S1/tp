package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
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

    private final Person[] chartData;

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
        populateChart();
    }

    /**
     * Populates the BarChart with data from the Person array.
     */
    private void populateChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Tutor Hours Chart");

        // Iterate over the Person array to create chart data entries.
        for (Person person : chartData) {
            Name name = person.getName();
            int hours = person.getHours().getHoursInt(); // Assuming getHours() returns Hours object with getValue().

            // Add each person's name and hours as a bar in the chart.
            series.getData().add(new XYChart.Data<>(name.fullName, hours));
        }

        // Clear previous data and add the new series to the chart.
        barChart.getData().clear();
        barChart.getData().add(series);
    }
}
