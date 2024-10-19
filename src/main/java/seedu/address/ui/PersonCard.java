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
    private Label nric;
    @FXML
    private Label birthDate;
    @FXML
    private Label sex;
    @FXML
    private FlowPane healthServices;
    @FXML
    private Label appointmentDateTime;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        nric.setText(person.getNric().value);
        sex.setText(person.getSex().value);
        birthDate.setText(person.getBirthdate().value);
        person.getHealthServices().stream()
                .sorted(Comparator.comparing(healthservice -> healthservice.healthServiceName))
                .forEach(healthservice -> healthServices.getChildren().add(new Label(healthservice.healthServiceName)));
        if (person.getAppts().size() == 0) {
            appointmentDateTime.setText("No appointments currently");
        } else {
            appointmentDateTime.setText(person.getApptsString());
        }
    }
}
