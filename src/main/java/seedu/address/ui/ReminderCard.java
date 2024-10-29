package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderCard.fxml";

    // Dummy data arrays for testing
    private static final String[] DUMMY_NAMES = {
        "Alex Yeoh",
        "Bernice Yu",
        "Charlotte Oliveiro",
        "David Li",
        "Irfan Ibrahim"
    };

    private static final String[] DUMMY_DESCRIPTIONS = {
        "Follow up on proposal",
        "Schedule meeting",
        "Send contract",
        "Review documents",
        "Call regarding project"
    };

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

    /**
     * Creates a {@code ReminderCard} with dummy data using the given index.
     */
    public ReminderCard(int displayedIndex) {
        super(FXML);

        // Use modulo to cycle through dummy data arrays
        int dummyIndex = (displayedIndex - 1) % DUMMY_NAMES.length;

        id.setText(displayedIndex + ". ");
        personName.setText(DUMMY_NAMES[dummyIndex]);
        description.setText(DUMMY_DESCRIPTIONS[dummyIndex]);

        // Generate a dummy future time (current time + random hours between 1-48)
        int randomHours = (dummyIndex * 12 + 1) % 48;
        String dummyTime = java.time.LocalDateTime.now()
                .plusHours(randomHours)
                .format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        time.setText(dummyTime);
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
