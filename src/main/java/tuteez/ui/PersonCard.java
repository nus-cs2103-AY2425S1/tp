package tuteez.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import tuteez.commons.core.LogsCenter;
import tuteez.commons.util.UiUtil;
import tuteez.model.person.Person;
import tuteez.model.person.lesson.Lesson;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    public final Person person;
    private final Logger logger = LogsCenter.getLogger(getClass());
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
        UiUtil.setNameText(name, person);
        UiUtil.setPhoneText(phone, person);
        UiUtil.setTelegramUsernameText(telegram, person);
        UiUtil.setAddressText(address, person);
        UiUtil.setEmailText(email, person);
        UiUtil.setTags(tags, person);
        setNextLesson(person);
    }

    /**
     * Refreshes the next lesson if there is a change.
     */
    public void refreshNextLesson() {
        Lesson currentLesson = person.nextLessonBasedOnCurrentTime();
        if (currentLesson != lastDisplayedLesson) {
            logger.info(String.format("Next lesson for %s updated to: %s",
                    person.getName().fullName,
                    currentLesson.getDayAndTime()));
            setNextLesson(person);
            lastDisplayedLesson = currentLesson;
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
}
