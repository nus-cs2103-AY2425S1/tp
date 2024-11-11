package seedu.address.ui;

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

    private static final String FXMLEmployee = "PersonListCardEmployee.fxml";
    private static final String FXMLPotential = "PersonListCardPotential.fxml";

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
    private Label department;
    @FXML
    private Label role;
    @FXML
    private Label contractEndDate;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(person.isEmployee() ? FXMLEmployee : FXMLPotential);
        this.person = person;
        id.setText(displayedIndex + ". ");
        if (person.isEmployee()) {
            name.setText("(Employee) " + person.getName().fullName);
        } else {
            name.setText("(Potential Hire) " + person.getName().fullName);
        }
        phone.setText("Phone: " + person.getPhone().value);
        address.setText("Address: " + person.getAddress().value);
        email.setText("Email: " + person.getEmail().value);
        department.setText("Department: " + person.getDepartment().value);
        role.setText("Role: " + person.getRole().value);
        contractEndDate.setText("Contract End Date: " + person.getContractEndDate().getValue());
        if (person.isPotentialHire()) {
            contractEndDate.managedProperty().set(false);
            contractEndDate.visibleProperty().set(false);
        }
    }
}
