package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

public class ContactDetails extends UiPart<Region> {

    private static final String FXML = "ContactDetails.fxml";
    private Person focusedPerson;

    @FXML
    private HBox contactDetailPlane;

    @FXML
    private Label name;

    /**
     * Creates a {@code ContactDetailPannel} with the given {@code Person}.
     */
    public ContactDetails(Person person) {
        super(FXML);
        if (person != null) {
            this.focusedPerson = person;
            name.setText(focusedPerson.getName().fullName);
        }
    }

}
