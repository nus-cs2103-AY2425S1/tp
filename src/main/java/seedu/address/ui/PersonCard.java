package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a
     * consequence, UI elements' variable names cannot be set to such keywords or an exception will be thrown
     * by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level
     *      4</a>
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
    private FlowPane tags;
    @FXML
    private Label dateOfLastVisit;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;

        setIdLabel(displayedIndex);
        setNameLabel(person);
        setPhoneLabel(person);
        setTagsFlowpane(person);
        setDateOfLastVisitLabel(person);
    }

    private void setIdLabel(int displayedIndex) {
        id.setText(displayedIndex + ". ");
    }

    private void setDateOfLastVisitLabel(Person personToView) {
        if (personToView.hasDateOfLastVisit()) {
            dateOfLastVisit.setText("Date last visited: " + personToView.getDateOfLastVisit().get().value);
            dateOfLastVisit.setManaged(true);
        } else {
            dateOfLastVisit.setText("");
            dateOfLastVisit.setManaged(false);
        }
    }

    private void setTagsFlowpane(Person personToView) {
        personToView.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void setPhoneLabel(Person personToView) {
        phone.setText("Phone number: " + personToView.getPhone().value);
    }

    private void setNameLabel(Person personToView) {
        name.setText(personToView.getName().fullName);
    }
}
