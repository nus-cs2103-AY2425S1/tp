package seedu.address.ui.calendar;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.schedule.Meeting;
import seedu.address.ui.PersonListPanel;
import seedu.address.ui.UiPart;

import java.time.DayOfWeek;
import java.util.logging.Logger;

public class DailySchedulePanel extends UiPart<Region> {
    private static final String FXML = "DailyCalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private final FilteredList<Meeting> originalList;
    private final ReadOnlyAddressBook addressBook;

    @FXML
    private ListView<Meeting> dailyCalendarView;

    @FXML
    private Label dayLabel;

    public DailySchedulePanel(ObservableList<Meeting> calendarList, ReadOnlyAddressBook addressBook, int day) {
        super(FXML);
        this.addressBook = addressBook;
        dayLabel.setText(DayOfWeek.of(day).toString());
        originalList = (FilteredList<Meeting>) calendarList;
        dailyCalendarView.setItems(calendarList);
        dailyCalendarView.setCellFactory(listView -> new CalendarListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Meeting} using a {@code CalendarCard}.
     */
    class CalendarListViewCell extends ListCell<Meeting> {
        @Override
        protected void updateItem(Meeting meeting, boolean empty) {
            super.updateItem(meeting, empty);

            if (empty || meeting == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CalendarCard(meeting,
                        originalList.getSourceIndex(getIndex())+1,
                        addressBook).getRoot());
            }
        }
    }
}
