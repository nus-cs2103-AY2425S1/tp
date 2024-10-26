package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Reminder;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;

    @FXML
    private Label date;

    @FXML
    private Label description;



    /**
     * Creates a {@code ReminderCard} with the given {@code Reminder} to display.
     */
    public ReminderCard(Reminder reminder) {
        super(FXML);
        name.setText(reminder.personToMeet.toString());
        date.setText(reminder.reminderDate.toString());
        description.setText(reminder.reminderDescription);
    }
}
