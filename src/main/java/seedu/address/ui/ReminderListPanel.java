package seedu.address.ui;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of reminders.
 */
public class ReminderListPanel extends UiPart<Region> {
    private static final String FXML = "ReminderListPanel.fxml";
    private static final int NUMBER_OF_DUMMY_REMINDERS = 5;

    @FXML
    private ListView<Integer> reminderListView;

    /**
     * Creates a {@code ReminderListPanel} with the given {@code ObservableList}.
     */
    public ReminderListPanel() {
        super(FXML);
        // Create a list of integers from 1 to NUMBER_OF_DUMMY_REMINDERS
        reminderListView.setItems(FXCollections.observableArrayList(
                IntStream.rangeClosed(1, NUMBER_OF_DUMMY_REMINDERS)
                        .boxed()
                        .collect(Collectors.toList())
        ));
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Reminder} using a {@code ReminderCard}.
     */
    class ReminderListViewCell extends ListCell<Integer> {
        @Override
        protected void updateItem(Integer index, boolean empty) {
            super.updateItem(index, empty);

            if (empty || index == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReminderCard(index).getRoot());
            }
        }
    }
}
