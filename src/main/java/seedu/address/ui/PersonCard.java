package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;

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
    private Label jobCode;
    @FXML
    private Label email;
    @FXML
    //private Label tag;
    private FlowPane tagPane;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        jobCode.setText(person.getJobCode().value);
        email.setText(person.getEmail().value);
        //tag.setText(person.getTag().tagName);
        tagPane.getChildren().add(createTagLabel(person.getTag()));
    }

    private Label createTagLabel(Tag tag) {
        Label label = new Label(tag.toString());
        if (tag.tagCode.equalsIgnoreCase("N")) {
            label.setStyle("-fx-background-color: #bababa;"); // Red
        } else if (tag.tagCode.equalsIgnoreCase("TP")) {
            label.setStyle("-fx-background-color: #ffd295;"); // Blue
        } else if (tag.tagCode.equalsIgnoreCase("TC")) {
            label.setStyle("-fx-background-color: #85e0ff;"); // Orange
        } else if (tag.tagCode.equalsIgnoreCase("BP")) {
            label.setStyle("-fx-background-color: #f6b1ff;"); // Orange
        } else if (tag.tagCode.equalsIgnoreCase("BC")) {
            label.setStyle("-fx-background-color: #bd9cff;"); // Orange
        } else if (tag.tagCode.equalsIgnoreCase("R")) {
            label.setStyle("-fx-background-color: #ff5858;"); // Orange
        } else {
            label.setStyle("-fx-background-color: #6fffb1;"); // Default gray
        }
        return label;
    }

}
