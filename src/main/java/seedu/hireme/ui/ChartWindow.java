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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.commons.util.StringUtil;
import seedu.hireme.model.internshipapplication.Status;

/**
 * Controller for am chart page
 */
public class ChartWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(ChartWindow.class);
    private static final String FXML = "ChartWindow.fxml";
    private static final String countTitle = "Number of internship applications: ";
    @FXML
    private PieChart pieChart;
    @FXML
    private Label internshipCount;

    /**
     * Creates a new ChartWindow.
     *
     * @param root Stage to use as the root of the InsightsWindow.
     */
    public ChartWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new InsightsWindow.
     */
    public ChartWindow() {
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
        logger.fine("Showing chart page about the application.");

        update(insights);

        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Updates the pie chart with new chart data
     */
    public void update(Map<Status, Integer> insights) {
        int count = getInternshipCount(insights);
        internshipCount.setText(countTitle + count);

        List<PieChart.Data> data = new ArrayList<>();
        List<Map.Entry<Status, Integer>> sortedList = new ArrayList<>(insights.entrySet());
        sortedList.sort(Map.Entry.comparingByKey());

        int total = 0;
        for (Map.Entry<Status, Integer> entry: sortedList) {
            data.add(new PieChart.Data(entry.getKey().toString(), entry.getValue()));
            total += entry.getValue();
        }

        pieChart.setData(FXCollections.observableArrayList(data));
        pieChart.setTitle("Internship Application Chart");
        pieChart.setClockwise(true);
        pieChart.setLabelsVisible(count > 0);
        pieChart.setStartAngle(360);
        pieChart.setAnimated(false);
        pieChart.layout();

        final double totalApplications = total;
        /* Solution below adapted from
           https://stackoverflow.com/questions/35479375/display-additional-values-in-pie-chart
        */
        data.forEach(dataSegment -> {
                String stringToBeBound = StringUtil.getPercentageString(dataSegment.getPieValue(),
                        totalApplications);
                bindPieChartDataSegment(dataSegment, stringToBeBound);
            }
        );
    }

    /**
     * @param dataSegment The pie chart data segment that should have a label string bound to it.
     * @param stringToBeBound The string that needs to be bound to the data segment.
     */
    public void bindPieChartDataSegment(PieChart.Data dataSegment, String stringToBeBound) {
        /* Solution below adapted from
           https://stackoverflow.com/questions/35479375/display-additional-values-in-pie-chart
        */
        StringExpression exp = Bindings.concat(dataSegment.getName(), "  ", stringToBeBound);
        dataSegment.nameProperty().bind(exp);
    }

    /**
     * Returns true if the chart window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the chart window.
     */
    public void hide() {
        pieChart.getData().clear();
        getRoot().hide();
    }

    /**
     * Focuses on the chart window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    private int getInternshipCount(Map<Status, Integer> insights) {
        return insights.values().stream().reduce(0, Integer::sum);
    }
}
