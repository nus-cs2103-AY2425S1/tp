package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays brief information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    public final Person person;
    private final boolean isVisualsEnabled;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */


    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    private MainWindow mainWindow;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex, MainWindow mainWindow, boolean isVisualsEnabled) {
        super(FXML);
        this.person = person;
        this.mainWindow = mainWindow;
        this.isVisualsEnabled = isVisualsEnabled;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);

        // check for recent birthday
        if (isVisualsEnabled && person.getBirthday().hasBirthdayWithin7Days()) {
            name.setStyle("-fx-text-fill: #ffa500");
            Tooltip birthdayTooltip = new Tooltip("Birthday soon!");
            birthdayTooltip.setShowDelay(javafx.util.Duration.millis(10));
            Tooltip.install(name, birthdayTooltip);
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);

                    if (isVisualsEnabled) {
                        if ("highnetworth".equalsIgnoreCase(tag.tagName)) {
                            tagLabel.getStyleClass().add("tag-high");
                        } else if ("midnetworth".equalsIgnoreCase(tag.tagName)) {
                            tagLabel.getStyleClass().add("tag-mid");
                        } else if ("lownetworth".equalsIgnoreCase(tag.tagName)) {
                            tagLabel.getStyleClass().add("tag-low");
                        }
                    }
                    tags.getChildren().add(tagLabel);
                });
    }

    /**
     * Creates a detailed view of the contact
     */
    @FXML
    private void handleOnClick() {
        PersonDetailedView personDetailedView = new PersonDetailedView(person, isVisualsEnabled);
        mainWindow.updatePersonDetailedView(personDetailedView);
    }
}
