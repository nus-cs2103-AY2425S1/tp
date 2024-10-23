package seedu.address.ui.calendar;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.schedule.Meeting;
import seedu.address.ui.PersonListPanel;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of meetings for the week.
 */
public class WeeklySchedulePanel extends UiPart<Region> {
    private static final String FXML = "WeeklyCalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private final ReadOnlyAddressBook addressBook;

    @FXML
    private ListView<ObservableList<Meeting>> weeklyCalendarView;

    /**
     * Creates a {@code WeeklySchedulePanel} with the given {@code ObservableList}.
     */
    public WeeklySchedulePanel(ObservableList<Meeting> calendarList, ReadOnlyAddressBook addressBook) {
        super(FXML);
        this.addressBook = addressBook;

        ObservableList<ObservableList<Meeting>> weeklyMeetingList = FXCollections.observableArrayList();
        for (int i = 1; i < 8; i++) {
            weeklyMeetingList.add(calendarList);
        }
        weeklyCalendarView.setItems(weeklyMeetingList);
        weeklyCalendarView.setCellFactory(listView -> new WeeklySchedulePanel.CalendarListViewCell());

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Meeting} using a {@code CalendarCard}.
     */
    class CalendarListViewCell extends ListCell<ObservableList<Meeting>> {
        @Override
        protected void updateItem(ObservableList<Meeting> meeting, boolean empty) {
            super.updateItem(meeting, empty);
            int i = getIndex() + 1;
            if (empty || meeting == null) {
                setGraphic(null);
                setText(null);
            } else {
                FilteredList<Meeting> dailySchedule = new FilteredList<>(meeting);
                dailySchedule.setPredicate(m -> m.getMeetingDate().getDayOfWeek().getValue() == i);
                setGraphic(new DailySchedulePanel(dailySchedule, addressBook, getIndex() + 1).getRoot());
            }
        }
    }

}
