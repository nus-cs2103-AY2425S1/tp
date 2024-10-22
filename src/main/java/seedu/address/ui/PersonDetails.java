package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import seedu.address.model.person.Person;

/**
 * Controller class for displaying a person's details in the UI. Encapsulates
 * all features for the person object, and displays the details line by line.
 */
public class PersonDetails {
    @FXML
    public Label birthdayLabel;
    @FXML
    public Label historyLabel;
    @FXML
    public Label remarkLabel;
    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;




    /**
     * Sets the details of the specified {@code Person} in the respective UI labels.
     *
     * @param person The person whose details are to be displayed.
     *               This includes the person's name, phone, email, and address.
     */
    public void setPersonDetails(Person person) {
        nameLabel.setText(person.getName().fullName);
        phoneLabel.setText(person.getPhone().value);
        emailLabel.setText(person.getEmail().value);
        addressLabel.setText(person.getAddress().value);
        birthdayLabel.setText(person.getBirthday().value.toString());
        historyLabel.setText(person.getDateOfCreation().toString());

    }
}
