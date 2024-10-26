package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
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
    private ListView<Participation> attendanceListView;

    /**
     * Creates a {@code AttendanceContainer} with the given list of participation list
     * to display the attendance of all tutorials.
     */
    public AttendanceContainer(ObservableList<Participation> participationList) {
        super(FXML);
        this.participationList = participationList;
        participationList.addListener((ListChangeListener<Participation>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    FXCollections.sort(participationList, Comparator.comparing(Participation::getTutorialSubject));
                }
        }});

        attendanceListView.setItems(participationList);
        attendanceListView.setCellFactory(listView -> new AttendanceListViewCell());

        setParticipationListViewStyle();
        setDisplayDate();
        setTutorial();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class AttendanceListViewCell extends ListCell<Participation> {
        @Override
        protected void updateItem(Participation participation, boolean empty) {
            super.updateItem(participation, empty);

            if (empty || participation == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AttendanceCard(participation.getTutorialSubject(),
                        participation.getAttendanceList()).getRoot());
            }
        }
    }

    private void setDisplayDate() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM");

        week.setText("week " + today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
        dateRange.setText(startOfWeek.format(formatter) + " ~ " + endOfWeek.format(formatter));
    }

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

    private void setParticipationListViewStyle() {
        attendanceListView.setFixedCellSize(25);
        attendanceListView.setPrefHeight(25 * participationList.size());
    }
}
