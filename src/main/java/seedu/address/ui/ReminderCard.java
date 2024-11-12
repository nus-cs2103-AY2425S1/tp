package seedu.address.ui;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    private Label descriptionDate;

    @FXML
    private Label id;



    /**
     * Creates a {@code ReminderCard} with the given {@code Reminder} to display.
     */
    public ReminderCard(Reminder reminder, int reminderIndex) {
        super(FXML);
        id.setText(reminderIndex + ". ");
        name.setText(reminder.personToMeet.toString() + ":");
        LocalDate reminderDate = reminder.reminderDate;
        LocalDate today = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(today, reminderDate);
        descriptionDate.setText(reminder.reminderDescription + " (" + daysBetween + "d)");
    }
}
