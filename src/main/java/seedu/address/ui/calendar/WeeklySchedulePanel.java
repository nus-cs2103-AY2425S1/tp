package seedu.address.ui.calendar;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
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
 * Panel containing the list of meetings for the week.
 */
public class WeeklySchedulePanel extends UiPart<Region> {
    private static final String FXML = "WeeklyCalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private final ObservableList<Person> personList;

    @FXML
    private ListView<ObservableList<Meeting>> weeklyCalendarView;

    @FXML
    private Label weekHeader;

    /**
     * Creates a {@code WeeklySchedulePanel} with the given {@code ObservableList}.
     */
    public WeeklySchedulePanel(ObservableList<ObservableList<Meeting>> dailyScheduleOfWeek,
                               ObservableList<Person> personList, ObservableValue<String> dateToDisplay) {
        super(FXML);
        this.personList = personList;

        weekHeader.textProperty().bind(dateToDisplay);
        weeklyCalendarView.setItems(dailyScheduleOfWeek);
        weeklyCalendarView.setCellFactory(listView -> new WeeklySchedulePanel.CalendarListViewCell());
    }

    public void updateSchedulePanel() {
        weeklyCalendarView.refresh();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Meeting} using a {@code CalendarCard}.
     */
    class CalendarListViewCell extends ListCell<ObservableList<Meeting>> {
        @Override
        protected void updateItem(ObservableList<Meeting> schedule, boolean empty) {
            super.updateItem(schedule, empty);
            int i = getIndex() + 1;
            if (empty || schedule == null) {
                setGraphic(null);
                setText(null);
            } else {
                FilteredList<Meeting> dailySchedule = new FilteredList<>(schedule);
                dailySchedule.setPredicate(m -> m.getMeetingDate().getDayOfWeek().getValue() == i);
                setGraphic(new DailySchedulePanel(dailySchedule, personList, getIndex() + 1).getRoot());
            }
        }
    }

}
