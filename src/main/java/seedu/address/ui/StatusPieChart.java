package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Panel containing the status distribution pie chart.
 */
public class StatusPieChart extends UiPart<Region> {

    private static final String FXML = "StatusPieChart.fxml";
    @FXML
    private VBox chartContainer;

    @FXML
    private Label chartTitle;

    @FXML
    private PieChart statusChart;

    /**
     * Creates a new StatusPieChart.
     * Initializes the chart with default settings by calling setupChart().
     */
    public StatusPieChart() {
        super(FXML);
        setupChart();
    }

    /**
     * Sets up the initial configuration of the pie chart.
     * This includes:
     * - Setting the chart title
     * - Configuring chart display properties (labels, legend, size)
     * - Setting up the chart container
     * - Applying custom styling
     */
    private void setupChart() {
        chartTitle.setText("CLIENT STATUS");
        statusChart.setTitle("");
        statusChart.setLabelsVisible(false);
        statusChart.setLegendVisible(true);
        statusChart.setLegendSide(Side.BOTTOM);

        statusChart.setPrefSize(200, 200);
        statusChart.setMinSize(180, 180);
        statusChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        chartContainer.setPadding(new Insets(2));

        statusChart.getStyleClass().add("status-chart");
    }

    /**
     * Updates the pie chart with new status counts.
     *
     * @param naCount Number of clients with NA status
     * @param nonUrgentCount Number of clients with NON_URGENT status
     * @param urgentCount Number of clients with URGENT status
     */
    public void updateChartData(int naCount, int nonUrgentCount, int urgentCount) {
        Platform.runLater(() -> {
            statusChart.getData().clear();

            PieChart.Data naData = new PieChart.Data("NA (" + naCount + ")", naCount);
            PieChart.Data nonUrgentData = new PieChart.Data("Non_Urgent (" + nonUrgentCount + ")", nonUrgentCount);
            PieChart.Data urgentData = new PieChart.Data("Urgent (" + urgentCount + ")", urgentCount);

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    naData, nonUrgentData, urgentData
            );

            statusChart.setData(pieChartData);

            naData.getNode().getStyleClass().add("data0");
            nonUrgentData.getNode().getStyleClass().add("data1");
            urgentData.getNode().getStyleClass().add("data2");

            statusChart.applyCss();
        });
    }
}
