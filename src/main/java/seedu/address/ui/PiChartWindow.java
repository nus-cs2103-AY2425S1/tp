package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;
import seedu.address.commons.core.LogsCenter;

public class PiChartWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(PiChartWindow.class);
    private static final String FXML = "PiChartWindow.fxml";
    private static Map<String, Integer> numOfStudentsInEachClass;

    @FXML
    private PieChart piChart;

    /**
     * Creates a new PiChartWindow.
     *
     * @param root Stage to use as the root of the PiChartWindow.
     */
    public PiChartWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new PiChartWindow.
     */
    public PiChartWindow() {
        this(new Stage());
    }

    public static void setData(Map<String, Integer> data) {
        numOfStudentsInEachClass = data;
    }

    @FXML
    private void initialize() {
        piChart.setTitle("Number of students in each class");
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : numOfStudentsInEachClass.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        piChart.setData(pieChartData);
    }

    /**
     * Shows the PiChart window.
     */
    public void show() {
        logger.fine("Showing Pi Chart window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the PiChart window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the PiChart window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the PiChart window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}