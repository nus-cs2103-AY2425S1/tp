package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    private static final boolean SUPPORTS_EMOJIS = isEmojiSupported();

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
    private Label postalCode;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText((SUPPORTS_EMOJIS ? "👤 " : "☺ ") + person.getName().fullName);
        phone.setText((SUPPORTS_EMOJIS ? "📞 " : "☎ ") + person.getPhone().value);
        address.setText((SUPPORTS_EMOJIS ? "🏠 " : "⌂ ") + person.getAddress().value);
        email.setText("✉️ " + person.getEmail().value);
        postalCode.setText((SUPPORTS_EMOJIS ? "📍 " : "➤ ") + person.getPostalCode().value);

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label((SUPPORTS_EMOJIS ? "🏷️ " : "⚑ ") + tag.tagName)));
    }

    /**
     * Detects if the current OS supports emojis (approximate).
     * @return true if emojis are likely supported, false otherwise.
     */
    private static boolean isEmojiSupported() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win") || os.contains("mac");
    }
}
