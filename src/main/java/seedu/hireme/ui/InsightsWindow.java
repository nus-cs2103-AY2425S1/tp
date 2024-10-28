package seedu.hireme.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.commons.util.StringUtil;
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
     * Shows the insights window.
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
        List<Map.Entry<Status, Integer>> sortedList = new ArrayList<>(insights.entrySet());
        sortedList.sort(Map.Entry.comparingByKey());

        int total = 0;
        for (Map.Entry<Status, Integer> entry: sortedList) {
            data.add(new PieChart.Data(entry.getKey().toString(), entry.getValue()));
            total += entry.getValue();
        }

        pieChart.setData(FXCollections.observableArrayList(data));
        pieChart.setTitle("Internship Application Insights");
        pieChart.setClockwise(true);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(360);
        pieChart.setAnimated(false);
        pieChart.layout();

        final double totalApplications = total;
        data.forEach(dataSegment -> {
                String stringToBeBound = StringUtil.getPercentageString(dataSegment.getPieValue(), totalApplications);
                bindPieChartDataSegment(dataSegment, stringToBeBound);
            }
        );

        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * @param dataSegment The pie chart data segment that should have a label string bound to it.
     * @param stringToBeBound The string that needs to be bound to the data segment.
     */
    public void bindPieChartDataSegment(PieChart.Data dataSegment, String stringToBeBound) {
        StringExpression exp = Bindings.concat(dataSegment.getName(), "  ", stringToBeBound);
        dataSegment.nameProperty().bind(exp);
    }

    /**
     * Returns true if the insights window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the insights window.
     */
    public void hide() {
        pieChart.getData().clear();
        getRoot().hide();
    }

    /**
     * Focuses on the insights window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
