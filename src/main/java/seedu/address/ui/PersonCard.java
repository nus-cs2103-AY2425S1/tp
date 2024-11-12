package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Donor;
import seedu.address.model.person.Partner;
import seedu.address.model.person.Person;
import seedu.address.model.person.Volunteer;

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
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label hours;
    @FXML
    private Label donatedAmount;
    @FXML
    private Label partnershipEndDate;
    @FXML
    private Label role;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        role.setText(person.getRole().toString());

        // Add appropriate style class based on the role
        if (person instanceof Volunteer) {
            role.getStyleClass().add("role-volunteer");
            Volunteer volunteer = (Volunteer) person;
            hours.setText("Hours: " + volunteer.getHours().toString());
            hours.setVisible(true);
            hours.setManaged(true);
        } else if (person instanceof Donor) {
            role.getStyleClass().add("role-donor");
            Donor donor = (Donor) person;
            donatedAmount.setText("Donated: $" + donor.getDonatedAmount().toString());
            donatedAmount.setVisible(true);
            donatedAmount.setManaged(true);
        } else if (person instanceof Partner) {
            role.getStyleClass().add("role-partner");
            Partner partner = (Partner) person;
            partnershipEndDate.setText("End Date: " + partner.getEndDate().toString());
            partnershipEndDate.setVisible(true);
            partnershipEndDate.setManaged(true);
        } else {
            role.getStyleClass().add("role-person");
        }

        // Add tags
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
