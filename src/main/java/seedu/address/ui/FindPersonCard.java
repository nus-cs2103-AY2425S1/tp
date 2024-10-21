package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

import java.util.Comparator;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class FindPersonCard extends UiPart<Region> {

    private static final String FXML = "FindPersonCard.fxml";

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
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label nric;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane caregivers;
    @FXML
    private FlowPane patients;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public FindPersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + "");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        nric.setText(person.getNric().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getCaregivers().stream()
                .sorted(Comparator.comparing(caregiver -> caregiver.toString()))
                .forEach(caregiver -> caregivers.getChildren().add(new Label(caregiver.toString())));
        person.getPatients().stream()
                .sorted(Comparator.comparing(patient -> patient.toString()))
                .forEach(patient -> patients.getChildren().add(new Label(patient.toString())));
    }
}
