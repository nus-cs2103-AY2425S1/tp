package seedu.address.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import seedu.address.model.person.ReminderManager;

/**
 * A UI component that displays the status bar footer with reminders.
 * Automatically updates when reminders change.
 */
public class StatusBarFooter extends UiPart<GridPane> {

    private static final String FXML = "StatusBarFooter.fxml";
    private static final String REMINDER_ICON = "/images/reminder.png";
    private static final double DEFAULT_ICON_SIZE = 20;

    @FXML
    private Label upcomingReminder;

    @FXML
    private ImageView reminderIcon;

    private final ReminderManager reminderManager;

    /**
     * Constructs a StatusBarFooter that displays reminders with an icon.
     *
     * @param reminderManager The ReminderManager that provides the reminders to display.
     */
    public StatusBarFooter(ReminderManager reminderManager) {
        super(FXML);
        this.reminderManager = reminderManager;

        reminderIcon.setFitHeight(DEFAULT_ICON_SIZE);
        reminderIcon.setFitWidth(DEFAULT_ICON_SIZE);

        Platform.runLater(this::initializeReminderIcon);
        setupReminderBinding();
    }

    /**
     * Loads and configures the reminder icon.
     */
    private void initializeReminderIcon() {
        try {
            Image image = new Image(getClass().getResourceAsStream(REMINDER_ICON));
            reminderIcon.setImage(image);

            HBox parentHBox = (HBox) reminderIcon.getParent();
            reminderIcon.fitHeightProperty().bind(parentHBox.heightProperty().multiply(0.8));

            reminderIcon.setPreserveRatio(true);
            reminderIcon.setSmooth(true);

        } catch (Exception e) {
            System.err.println("Could not load reminder icon: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sets up binding between the reminder manager and the UI label.
     */
    private void setupReminderBinding() {
        reminderManager.currentReminderProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                upcomingReminder.setText(newValue);
            });
        });

        // Set initial value
        upcomingReminder.setText(reminderManager.currentReminderProperty().get());
    }
}