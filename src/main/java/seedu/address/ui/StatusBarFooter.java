package seedu.address.ui;

import javafx.application.Platform;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import seedu.address.model.person.ReminderManager;

public class StatusBarFooter extends UiPart<GridPane> {

    private static final String FXML = "StatusBarFooter.fxml";
    private static final String REMINDER_ICON = "/images/reminder.png";
    private static final double DEFAULT_ICON_SIZE = 20;

    @FXML
    private Label upcomingReminder;

    @FXML
    private ImageView reminderIcon;

    private final ReminderManager reminderManager;

    public StatusBarFooter(ReminderManager reminderManager) {
        super(FXML);
        this.reminderManager = reminderManager;

        reminderIcon.setFitHeight(DEFAULT_ICON_SIZE);
        reminderIcon.setFitWidth(DEFAULT_ICON_SIZE);

        Platform.runLater(this::initializeReminderIcon);
        startReminderRotation();
    }

    private void initializeReminderIcon() {
        try {
            Image image = new Image(getClass().getResourceAsStream(REMINDER_ICON));
            reminderIcon.setImage(image);

            HBox parentHBox = (HBox) reminderIcon.getParent();
            reminderIcon.fitHeightProperty().bind(parentHBox.heightProperty().multiply(0.8));

            reminderIcon.setPreserveRatio(true);
            reminderIcon.setSmooth(false);

        } catch (Exception e) {
            System.err.println("Could not load reminder icon: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void startReminderRotation() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    String nextReminder = reminderManager.getNextReminder();
                    if (nextReminder != null && !nextReminder.isEmpty()) {
                        upcomingReminder.setText(nextReminder);
                    } else {
                        upcomingReminder.setText("No upcoming reminders");
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
