package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Reminder;

/**
 * Panel containing the list of reminders.
 */
public class ReminderListDisplay extends UiPart<Region> {
    private static final String FXML = "ReminderListDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Reminder> reminderListView;

    /**
     * Creates a {@code ReminderListDisplay} with the given {@code ObservableList}.
     */
    public ReminderListDisplay(ObservableList<Reminder> reminderListPersons) {
        super(FXML);

        reminderListView.setItems(reminderListPersons);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Reminder} using a {@code ReminderCard}.
     */
    class ReminderListViewCell extends ListCell<Reminder> {
        @Override
        protected void updateItem(Reminder reminder, boolean empty) {
            super.updateItem(reminder, empty);

            if (empty || reminder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReminderCard(reminder).getRoot());
            }
        }
    }

}

