package tuteez.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import tuteez.model.person.Person;
import tuteez.model.person.TelegramUsername;
import tuteez.model.person.lesson.Lesson;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;
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
    private FlowPane lessons;

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
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        setNextLesson(person);
    }

    /**
     * Sets the address label text if an address exists for the {@code Person}.
     * Hides the address label if no address is present.
     *
     * @param person The {@code Person} whose address is to be displayed.
     */
    private void setAddressText(Person person) {
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
        Lesson nextLesson = person.nextLessonBasedOnCurrentTime();
        if (nextLesson != null) {
            lessons.getChildren().add(new Label(person.nextLessonBasedOnCurrentTime().getDayAndTime()));
        } else {
            lessons.setVisible(false);
        }

    }
}
