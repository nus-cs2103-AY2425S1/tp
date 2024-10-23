package seedu.address.ui;

import static seedu.address.model.util.ContactType.PERSON;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.util.ContactType;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String PERSON_FXML = "PersonListCard.fxml";
    private static final String STUDENT_FXML = "StudentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(ContactType contactType, Person person, int displayedIndex) {
        super(getFxml(contactType));
        this.person = person;
        id.setText(String.valueOf(displayedIndex));
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Factory method for constructing a {@code PersonCard}
     */
    public static PersonCard of(Person person, int displayedIndex) {
        if (person instanceof Student student) {
            return new StudentCard(student, displayedIndex);
        }
        return new PersonCard(PERSON, person, displayedIndex);
    }

    public static String getFxml(ContactType contactType) {
        switch(contactType) {
        case STUDENT:
            return STUDENT_FXML;
        default:
            return PERSON_FXML;
        }
    }
}
