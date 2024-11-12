package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.participation.Participation;

/**
 * An UI component that displays information related to attendance.
 */
public class AttendanceContainer extends UiPart<Region> {

    private static final String FXML = "AttendanceContainer.fxml";

    private final Logger logger = LogsCenter.getLogger(AttendanceContainer.class);

    private final ObservableList<Participation> participationList;

    @FXML
    private Label tutorial;
    @FXML
    private Label week;
    @FXML
    private Label dateRange;
    @FXML
    private VBox attendanceList;

    /**
     * Creates a {@code AttendanceContainer} with the given list of participation
     * to display the attendance of all tutorials.
     */
    public AttendanceContainer(ObservableList<Participation> participationList) {
        super(FXML);
        this.participationList = participationList;

        setAttendanceList();
        setWeeklyDateRange();
        addListener();
        logger.info("Successfully created attendance container");
    }

    /**
     * Creates {@code AttendanceCard} for each participation in the list and adds it to
     * attendanceList to be displayed in the UI.
     */
    private void setAttendanceList() {
        logger.info("Setting display for the list of participation");
        if (participationList.isEmpty()) {
            logger.info("No participation to display");
            setEmptyParticipationPlaceholder();
        } else {
            attendanceList.getChildren().clear();
            participationList.stream().sorted(Comparator.comparing(Participation::getTutorialSubject))
                    .forEach(participation -> attendanceList.getChildren()
                    .add(new AttendanceCard(participation.getTutorialSubject(),
                            participation.getAttendanceList()).getRoot()));
            logger.info("Successfully set the display for all participation");
        }
    }

    /**
     * Sets the label to display the current week with the date range.
     */
    private void setWeeklyDateRange() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM");

        week.setText("week " + today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
        dateRange.setText(startOfWeek.format(formatter) + " ~ " + endOfWeek.format(formatter));
    }

    /**
     * Sets the attendanceList to show a message if there is no participation for the student.
     */
    private void setEmptyParticipationPlaceholder() {
        Label label = new Label("Not enrolled in any tutorial");
        label.setStyle("-fx-background-color: #F3F8FB; -fx-background-radius: 5; -fx-padding: 10 15");

        HBox emptyState = new HBox(label);
        emptyState.setAlignment(Pos.CENTER);

        attendanceList.getChildren().clear();
        attendanceList.getChildren().add(emptyState);
    }

    /**
     * Adds listener to participationList to update attendanceList whenever participation is added or removed
     */
    private void addListener() {
        participationList.addListener((ListChangeListener<Participation>) change -> {
            logger.info("Change observed in participation list: " + change);
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    setAttendanceList();
                }
            }
        });
    }

}
