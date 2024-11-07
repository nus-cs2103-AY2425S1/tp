package seedu.address.ui;

import java.util.Comparator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final double EMERGENCY_CONTACT_LIST_DEFAULT_CARD_HEIGHT = 90;
    private static final double EMERGENCY_CONTACT_LIST_DEFAULT_BORDER_SIZE = 1.5;
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
    private EmergencyContactListPanel emergencyContactListPanel;
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
    @FXML
    private VBox emergencyContactsBox;
    @FXML
    private VBox doctorBox;

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
        doctorName.setText(person.getDoctor().getName().getDoctorName());
        doctorPhone.setText(person.getDoctor().getPhone().value);
        doctorEmail.setText(person.getDoctor().getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Follows syntax of personCard, odd here refers to a zero-indexed list
        if (displayedIndex % 2 == 0) {
            doctorBox.getStyleClass().add("emergencyContactListView-odd");
            emergencyContactsBox.getStyleClass().add("emergencyContactListView-odd");
        } else {
            doctorBox.getStyleClass().add("emergencyContactListView-even");
            emergencyContactsBox.getStyleClass().add("emergencyContactListView-even");
        }

        int numEmergencyContacts = person.getEmergencyContacts().size();
        emergencyContactListPanel = new EmergencyContactListPanel(
                FXCollections.observableArrayList(person.getEmergencyContacts()));
        emergencyContactListPanel.getEmergencyContactListView().getSelectionModel()
                .selectedItemProperty().addListener(emergencyContactSelectionListener());
        emergencyContactListPanelPlaceholder.getChildren().add(emergencyContactListPanel.getRoot());
        emergencyContactListPanelPlaceholder.setPrefHeight(EMERGENCY_CONTACT_LIST_DEFAULT_CARD_HEIGHT
                * numEmergencyContacts);
        emergencyContactListPanelPlaceholder.setMaxHeight(EMERGENCY_CONTACT_LIST_DEFAULT_CARD_HEIGHT * 2);
    }

    private ChangeListener<EmergencyContact> emergencyContactSelectionListener() {
        return (ObservableValue<? extends EmergencyContact> observableValue,
                EmergencyContact previousSelection,
                EmergencyContact currentSelection) -> {
            int updatedNumEmergencyContacts = person.getEmergencyContacts().size();
            if (currentSelection != null) {
                emergencyContactListPanelPlaceholder.setPrefHeight(EMERGENCY_CONTACT_LIST_DEFAULT_CARD_HEIGHT
                        * updatedNumEmergencyContacts + EMERGENCY_CONTACT_LIST_DEFAULT_BORDER_SIZE * 2);
                emergencyContactListPanelPlaceholder.setMaxHeight(EMERGENCY_CONTACT_LIST_DEFAULT_CARD_HEIGHT
                        * 2 + EMERGENCY_CONTACT_LIST_DEFAULT_BORDER_SIZE * 2);
            } else {
                emergencyContactListPanelPlaceholder.setPrefHeight(EMERGENCY_CONTACT_LIST_DEFAULT_CARD_HEIGHT
                        * updatedNumEmergencyContacts);
                emergencyContactListPanelPlaceholder.setMaxHeight(EMERGENCY_CONTACT_LIST_DEFAULT_CARD_HEIGHT * 2);
            }
        };
    }

    public ListView<EmergencyContact> getEmergencyContactListView() {
        return emergencyContactListPanel.getEmergencyContactListView();
    }
}
