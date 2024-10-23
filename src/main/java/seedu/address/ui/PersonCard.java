package seedu.address.ui;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
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
    private VBox publicAddress;
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
        addPublicAddressUI(person);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Adds the user interface elements for the public addresses of the given person.
     *
     * This method iterates through the public address entries of the person. For each non-empty entry,
     * it creates a label displaying the network name followed by each public address indented by two spaces.
     * The labels are then added to the `publicAddress` Pane.
     *
     * @param person The person whose public addresses to display.
     */
    public void addPublicAddressUI(Person person) {
        person.getPublicAddresses().entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .flatMap(entry -> {
                    Network network = entry.getKey();
                    Set<PublicAddress> addresses = entry.getValue();

                    return Stream.concat(Stream.of(network.toString()),
                            addresses.stream().map(address -> "  " + address.toString()).sorted()
                    );
                })
                .forEach(output -> {
                    Label label = new Label(output);
                    label.getStyleClass().add("cell_small_label");
                    publicAddress.getChildren().add(label);
                });
    }
}

