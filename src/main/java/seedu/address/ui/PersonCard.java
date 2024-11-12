package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleName;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.TelegramHandle;

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
    private Label contactType;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label telegramHandle;
    @FXML
    private Label email;
    @FXML
    private Label moduleName;
    @FXML
    private Label remark;
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
        phone.setText(person.getPhone().map(Phone::toString).orElse(" "));
        email.setText(person.getEmail().map(Email::toString).orElse(" "));
        telegramHandle.setText(person.getTelegramHandle().map(TelegramHandle::toString).orElse(" "));
        moduleName.setText(person.getModuleName().map(ModuleName::toString).orElse(" "));
        String contactTypeStr = person.getContactType().value.toString().toLowerCase();
        remark.setText(person.getRemark().map(Remark::toString).orElse(" "));
        contactType.setText(contactTypeStr);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
