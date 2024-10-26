package seedu.hireme.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.model.internshipapplication.Status;

/**
 * Controller for am insights page
 */
public class InsightsWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(InsightsWindow.class);
    private static final String FXML = "InsightsWindow.fxml";
    @FXML
    private PieChart pieChart;

    /**
     * Creates a new InsightsWindow.
     *
     * @param root Stage to use as the root of the InsightsWindow.
     */
    public InsightsWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new InsightsWindow.
     */
    public InsightsWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show(Map<Status, Integer> insights) {
        logger.fine("Showing insights page about the application.");
        List<PieChart.Data> data = new ArrayList<>();
        for (Map.Entry<Status, Integer> entry: insights.entrySet()) {
            data.add(new PieChart.Data(entry.getKey().toString(), entry.getValue()));
        }
        pieChart.setData(FXCollections.observableArrayList(data));
        pieChart.setTitle("Internship Application Insights");
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(10);
        pieChart.setLabelsVisible(false);
        pieChart.setStartAngle(360);
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        pieChart.getData().clear();
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
