package seedu.address.ui.calendar;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.schedule.Meeting;
import seedu.address.ui.PersonListPanel;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of meetings for the week.
 */
public class WeeklySchedulePanel extends UiPart<Region> {
    private static final String FXML = "WeeklyCalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Meeting> weeklyCalendarView;

    /**
     * Creates a {@code WeeklySchedulePanel} with the given {@code ObservableList}.
     */
    public WeeklySchedulePanel(ObservableList<Meeting> calendarList) {
        super(FXML);
        weeklyCalendarView.setItems(calendarList);
        weeklyCalendarView.setCellFactory(listView -> new WeeklySchedulePanel.CalendarListViewCell());
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
                setGraphic(new CalendarCard(meeting, getIndex() + 1).getRoot());
            }
        }
    }

}
