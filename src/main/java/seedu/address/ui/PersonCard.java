package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
    private EmergencyContactListPanel emergencyContactListPanel;

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
    private StackPane emergencyContactListPanelPlaceholder;
    @FXML
    private Label doctorName;
    @FXML
    private Label doctorPhone;
    @FXML
    private Label doctorEmail;
    @FXML
    private FlowPane tags;

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

        emergencyContactListPanel = new EmergencyContactListPanel(
                FXCollections.observableArrayList(person.getEmergencyContacts()));
        emergencyContactListPanelPlaceholder.getChildren().add(emergencyContactListPanel.getRoot());
        // 90 is the height of 1 EmergencyContactHeight. Will update in future to make this dynamic
        // instead of hard-coded.
        emergencyContactListPanelPlaceholder.setPrefHeight(90 * person.getEmergencyContacts().size());

        doctorName.setText(person.getDoctor().getName().getDoctorName());
        doctorPhone.setText(person.getDoctor().getPhone().value);
        doctorEmail.setText(person.getDoctor().getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
