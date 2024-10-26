package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
                    setTutorial();
                }
            }
        });

        setAttendanceList();
        setDisplayDate();
        setTutorial();
    }

    /**
     * Creates {@code AttendanceCard} for each participation in the list and adds it to
     * attendanceList to be displayed in the UI.
     */
    private void setAttendanceList() {
        attendanceList.getChildren().clear();
        participationList.forEach(participation -> attendanceList.getChildren()
                .add(new AttendanceCard(participation.getTutorialSubject(),
                        participation.getAttendanceList()).getRoot()));
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
     * Sets the label to display the tutorials taken by the student.
     */
    private void setTutorial() {
        StringBuilder tutorials = new StringBuilder();
        for (int i = 0; i < participationList.size(); i++) {
            tutorials.append(participationList.get(i).getTutorial().getSubject());
            if (i != participationList.size() - 1) {
                tutorials.append(" | ");
            }
        }

        if (tutorials.isEmpty()) {
            tutorial.setText("Not enrolled in any tutorial");
        } else {
            tutorial.setText(tutorials.toString());
        }
    }

}
