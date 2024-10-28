package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.participation.Participation;

/**
 * An UI component for displaying information related to attendance.
 */
public class AttendanceContainer extends UiPart<Region> {

    private static final String FXML = "AttendanceContainer.fxml";

    public final ObservableList<Participation> participationList;

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

        //listener to trigger UI update when participationList changes
        participationList.addListener((ListChangeListener<Participation>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    setAttendanceList();
                }
            }
        });

        setAttendanceList();
        setDisplayDate();
    }

    /**
     * Creates {@code AttendanceCard} for each participation in the list and adds it to
     * attendanceList to be displayed in the UI.
     */
    private void setAttendanceList() {
        if (participationList.isEmpty()) {
            setEmptyParticipationPlaceholder();
        } else {
            attendanceList.getChildren().clear();
            participationList.forEach(participation -> attendanceList.getChildren()
                    .add(new AttendanceCard(participation.getTutorialSubject(),
                            participation.getAttendanceList()).getRoot()));
        }
    }

    /**
     * Sets the label to display the current week with the date range.
     */
    private void setDisplayDate() {
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

}
