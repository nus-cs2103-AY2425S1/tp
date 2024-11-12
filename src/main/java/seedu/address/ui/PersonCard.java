package seedu.address.ui;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
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
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label priority;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label dateOfBirth;
    @FXML
    private Label householdIncome;
    @FXML
    private Label familySize;
    @FXML
    private Label updatedAt;
    @FXML
    private FlowPane tags;
    @FXML
    private Label isArchived;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);

        priority.getStyleClass().add(switch (person.getPriority()) {
        case HIGH -> "priority-high";
        case MEDIUM -> "priority-medium";
        case LOW -> "priority-low";
        });
        priority.setText(person.getPriority().name());

        dateOfBirth.setText(String.format("%s (Age: %d)", person.getDateOfBirth(), getPersonAge(person)));
        householdIncome.setText(String.format("[Household Income] %s", person.getIncome()));
        familySize.setText(String.format("Family size: %s", person.getFamilySize()));
        updatedAt.setText(String.format("Last updated: %s", person.getUpdatedAt()));

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        isArchived.setVisible(person.isArchived());
        isArchived.setManaged(person.isArchived());
    }

    private static int getPersonAge(Person person) {
        LocalDate date = person.getDateOfBirth().getValue();
        LocalDate now = LocalDate.now();
        return Period.between(date, now).getYears();
    }
}
