package tuteez.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import tuteez.commons.core.LogsCenter;
import tuteez.model.person.Person;
import tuteez.model.person.TelegramUsername;
import tuteez.model.person.lesson.Lesson;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final int REFRESH_TIME = 60;

    public final Person person;
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Timeline refreshTimeline;
    private Lesson lastDisplayedLesson = null;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label telegram;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label nextLesson;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        setTelegramUsernameText(person);
        setAddressText(person);
        setEmailText(person);
        setTags(person);
        setNextLesson(person);
        startRefreshTimeline();
    }

    /**
     * Starts the timeline that checks the lesson status every second and updates if necessary.
     */
    private void startRefreshTimeline() {
        if (refreshTimeline != null) {
            refreshTimeline.stop();
        }
        refreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(REFRESH_TIME), event -> refreshNextLesson())
        );
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }

    /**
     * Refreshes the next lesson if there is a change.
     */
    private void refreshNextLesson() {
        Lesson currentLesson = person.nextLessonBasedOnCurrentTime();
        if (currentLesson != lastDisplayedLesson) {
            logger.info(String.format("Next lesson for %s updated to: %s",
                    person.getName().fullName,
                    currentLesson.getDayAndTime()));
            setNextLesson(person);
            lastDisplayedLesson = currentLesson; // Update to keep track of the last displayed lesson
        }
    }

    /**
     * Stops the refresh timeline for updating the next lesson.
     * This method should be called when the {@code PersonCard} is no longer needed.
     */
    public void stopRefreshTimeline() {
        if (refreshTimeline != null) {
            refreshTimeline.stop();
        }
    }

    /**
     * Sets the address label text if an address exists for the {@code Person}.
     * Hides the address label if no address is present.
     *
     * @param person The {@code Person} whose address is to be displayed.
     */
    private void setAddressText(Person person) {
        assert(person != null);
        if (person.getAddress().value != null) {
            address.setText(person.getAddress().value);
            address.setVisible(true);
        } else {
            address.setVisible(false);
        }
    }

    /**
     * Sets the email label text if an email exists for the {@code Person}.
     * Hides the email label if no email is present.
     *
     * @param person The {@code Person} whose email is to be displayed.
     */
    private void setEmailText(Person person) {
        assert(person != null);
        if (person.getEmail().value != null) {
            email.setText(person.getEmail().value);
            email.setVisible(true);
        } else {
            email.setVisible(false);
        }
    }

    /**
     * Sets the Telegram username label text if a Telegram username exists for the {@code Person}.
     * Hides the Telegram label if no username is present.
     *
     * @param person The {@code Person} whose Telegram username is to be displayed.
     */
    private void setTelegramUsernameText(Person person) {
        assert(person != null);
        TelegramUsername username = person.getTelegramUsername();
        if (username != null && username.telegramUsername != null && !username.telegramUsername.isEmpty()) {
            telegram.setText("@" + username.telegramUsername);
            telegram.setVisible(true);
        } else {
            telegram.setVisible(false);
        }
    }

    /**
     * Displays the next lesson scheduled for the {@code Person}, based on the current time.
     * If no lessons are scheduled, hides the lessons flow pane.
     *
     * @param person The {@code Person} whose next lesson is to be displayed.
     */
    private void setNextLesson(Person person) {
        assert(person != null);
        Lesson studentNextLesson = person.nextLessonBasedOnCurrentTime();
        if (studentNextLesson != null) {
            nextLesson.setText(studentNextLesson.getDayAndTime());
        } else {
            nextLesson.managedProperty().bind(nextLesson.visibleProperty());
            nextLesson.setVisible(false);
        }
    }

    /**
     * Sets the tags associated with the {@code Person} in the tags flow pane.
     * Sorts the tags alphabetically for consistent ordering.
     *
     * @param person The {@code Person} whose tags are to be displayed.
     */
    private void setTags(Person person) {
        assert(person != null);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
