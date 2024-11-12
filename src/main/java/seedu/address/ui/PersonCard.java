package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    private final Logger logger = LogsCenter.getLogger(PersonCard.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final Person person;

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
    private Label payment;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox paymentCardPlaceholder;
    @FXML
    private VBox attendanceContainerPlaceholder;

    /**
     * Creates a {@code PersonCard} with the given {@code Person}, list of {@code Participation}
     * and index to display.
     */
    public PersonCard(Person person, ObservableList<Participation> participationList, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + "");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label label = new Label(tag.tagName);
                    label.setWrapText(true);
                    label.setMaxWidth(300);
                    tags.getChildren().add(label);
                });

        if (tags.getChildren().isEmpty()) {
            tags.setManaged(false);
        }

        paymentCardPlaceholder.getChildren().add(new PaymentCard(person.getPayment()).getRoot());
        attendanceContainerPlaceholder.getChildren().add(new AttendanceContainer(participationList).getRoot());

        logger.info("Successfully created PersonCard for " + person + "\n - Participation: " + participationList);
    }
}
