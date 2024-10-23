package seedu.address.ui;

import java.util.Map;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * The Bar Chart window. Displays a Bar Chart of the number of students in each class.
 */
public class BarChartWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(BarChartWindow.class);
    private static final String FXML = "BarChartWindow.fxml";
    private static Map<String, Integer> distributionOfMonthsPaid;

    @FXML
    private BarChart<String, Number> barChart;

    /**
     * Creates a new BarChartWindow.
     *
     * @param root Stage to use as the root of the PBarhartWindow.
     */
    public BarChartWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new BarChartWindow.
     */
    public BarChartWindow() {
        this(new Stage());
    }

    public static void setData(Map<String, Integer> data) {
        distributionOfMonthsPaid = data;
    }

    @FXML
    private void initialize() {
        barChart.setTitle("Number of students who paid in each month");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : distributionOfMonthsPaid.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        barChart.getData().add(series);
    }

    /**
     * Shows the Bar Chart window.
     */
    public void show() {
        logger.fine("Showing Pi Chart window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the Bar Chart window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Closes the Bar Chart window.
     */
    public void close() {
        getRoot().close();
    }

    /**
     * Hides the Bar Chart window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the Bar Chart window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
