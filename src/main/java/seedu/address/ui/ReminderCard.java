package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.reminder.Reminder;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderCard.fxml";

    public final Reminder reminder;

    @FXML
    private HBox reminderCardPane;
    @FXML
    private Label id;
    @FXML
    private Label personName;
    @FXML
    private Label description;
    @FXML
    private Label time;
    @FXML
    private Label date;

    /**
     * Creates a {@code ReminderCard} with dummy data using the given index.
     */
    public ReminderCard(Reminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        personName.setText(reminder.getPersonName());
        description.setText(reminder.getDescription().description);

        // Creates a more readable format for the date and time
        String dateString = reminder.getDateTime().toLocalDate().toString(); // "2021-12-21"
        String timeString = reminder.getDateTime().toLocalTime().toString(); // "23:59:00"
        time.setText(timeString);
        date.setText(dateString);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ReminderCard)) {
            return false;
        }

        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                && personName.getText().equals(card.personName.getText())
                && description.getText().equals(card.description.getText());
    }
}
