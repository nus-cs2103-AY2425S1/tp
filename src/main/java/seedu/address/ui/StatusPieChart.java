package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * Panel containing the status distribution pie chart.
 */
public class StatusPieChart extends UiPart<Region> {

    private static final String FXML = "StatusPieChart.fxml";

    private static final String NONE_COLOR = "#009E60";
    private static final String NON_URGENT_COLOR = "#c46210";
    private static final String URGENT_COLOR = "#FF2400";

    @FXML
    private VBox chartContainer;

    @FXML
    private Label chartTitle;

    @FXML
    private PieChart statusChart;

    public StatusPieChart() {
        super(FXML);
        setupChart();
    }

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

        // Add default style classes to ensure proper initialization
        statusChart.getStyleClass().add("status-chart");
    }

    /**
     * Updates the pie chart with new status counts.
     *
     * @param noneCount Number of persons with NONE status
     * @param nonUrgentCount Number of persons with NON_URGENT status
     * @param urgentCount Number of persons with URGENT status
     */
    public void updateChartData(int noneCount, int nonUrgentCount, int urgentCount) {
        Platform.runLater(() -> {
            statusChart.getData().clear();

            // Create data pieces
            PieChart.Data noneData = new PieChart.Data("None (" + noneCount + ")", noneCount);
            PieChart.Data nonUrgentData = new PieChart.Data("Non-Urgent (" + nonUrgentCount + ")", nonUrgentCount);
            PieChart.Data urgentData = new PieChart.Data("Urgent (" + urgentCount + ")", urgentCount);

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    noneData, nonUrgentData, urgentData
            );

            statusChart.setData(pieChartData);

            // Apply styles to pie slices first
            noneData.getNode().setStyle("-fx-pie-color: " + NONE_COLOR + ";");
            nonUrgentData.getNode().setStyle("-fx-pie-color: " + NON_URGENT_COLOR + ";");
            urgentData.getNode().setStyle("-fx-pie-color: " + URGENT_COLOR + ";");

            // Add style classes for CSS styling
            noneData.getNode().getStyleClass().add("data0");
            nonUrgentData.getNode().getStyleClass().add("data1");
            urgentData.getNode().getStyleClass().add("data2");

            // Force a style refresh
            statusChart.applyCss();
        });
    }
}