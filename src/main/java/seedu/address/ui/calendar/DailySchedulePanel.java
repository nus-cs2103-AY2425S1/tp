package seedu.address.ui.calendar;

import java.time.DayOfWeek;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Meeting;
import seedu.address.ui.PersonListPanel;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of meetings for each day.
 */
public class DailySchedulePanel extends UiPart<Region> {
    private static final String FXML = "DailyCalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private final FilteredList<Meeting> originalList;
    private final ObservableList<Person> personList;

    @FXML
    private ListView<Meeting> dailyCalendarView;

    @FXML
    private Label dayLabel;

    /**
     * Creates a {@code DailySchedulePanel} with the given {@code ObservableList}.
     */
    public DailySchedulePanel(ObservableList<Meeting> calendarList, ObservableList<Person> personList, int day) {
        super(FXML);
        this.personList = personList;
        originalList = (FilteredList<Meeting>) calendarList;

        dayLabel.setText(DayOfWeek.of(day).toString());

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
                        originalList.getSourceIndex(getIndex()) + 1,
                        personList).getRoot());
            }
        }
    }
}
