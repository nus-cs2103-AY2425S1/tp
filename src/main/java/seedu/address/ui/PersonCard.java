package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private Label nameLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label id;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        nameLabel.setText(person.getName().fullName);
        categoryLabel.setText(person.getCategoryDisplayName());
        addCategoryColor(person.getCategoryDisplayName());
        phoneLabel.setText("Phone: " + person.getPhone().value);
    }

    /**
     * Adds background color for category.
     * @param value The value of the category.
     */
    public void addCategoryColor(String value) {
        if ("Student".equalsIgnoreCase(value.trim())) {
            categoryLabel.getStyleClass().add("student-background");
        } else if ("Company".equalsIgnoreCase(value.trim())) {
            categoryLabel.getStyleClass().add("company-background");
        }
    }
}
