package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {
    private static final String FXML = "PersonListCard.fxml";
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
    private Label remark;
    @FXML
    private Label appointment;
    @FXML
    private Line underline;
    @FXML
    private Label role;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     *
     * @param person        The {@code Person} whose details are to be displayed in the card.
     * @param displayedIndex The index of the person in the list, used for display purposes.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;

        initializeId(displayedIndex);
        initializeName();
        initializeUnderline();
        initializePhone();
        initializeEmail();
        initializeAppointment();
        initializeTags();
        initializeRole();
    }

    private void initializeId(int displayedIndex) {
        id.setText(displayedIndex + ". ");
    }

    private void initializeName() {
        String actualName = person.getName().fullName;

        // Check if the name length is greater than 55
        if (actualName.length() > 55) {
            // Truncate the name to 55 characters and add "..."
            name.setText(actualName.substring(0, 55) + "...");
        } else {
            name.setText(actualName);
        }
    }

    private void initializeUnderline() {
        // Bind underline width to name label with adjustment
        underline.endXProperty().bind(name.widthProperty().add(45));
    }

    private void initializePhone() {
        String actualPhone = person.getPhone().value;

        // Check if the name length is greater than 25
        if (actualPhone.length() > 25) {
            // Truncate the name to 25 characters and add "..."
            phone.setText(actualPhone.substring(0, 25) + "...");
        } else {
            phone.setText(actualPhone);
        }
    }

    private void initializeEmail() {
        String actualEmail = person.getEmail().value;

        // Check if the email length is greater than 55
        if (actualEmail.length() > 55) {
            // Truncate the email to 55 characters and add "..."
            email.setText(actualEmail.substring(0, 55) + "...");
        } else {
            email.setText(actualEmail);
        }
    }

    private void initializeAppointment() {
        appointment.setText(person.getAppointment().toString());
    }

    private void initializeTags() {
        tags.getChildren().clear(); // Clear existing tags

        if (person.getTags().isEmpty()) {
            tags.setVisible(false); // Hide tags if no tags exist
        } else {
            tags.setVisible(true); // Show tags section if present

            // Configure tag alignment and spacing
            tags.setHgap(5);
            tags.setVgap(5);
            tags.setAlignment(Pos.CENTER_RIGHT);

            final double tagWidth = 185;
            final int charLimit = 15;

            // Sort and add tags to the UI
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        String name1 = tag.tagName;
                        // Check if the tag name length is greater than 25
                        if (name1.length() > charLimit) {
                            // Truncate the tag name to 25 characters and add "..."
                            name1 = name1.substring(0, charLimit) + "...";
                        }
                        Label tagLabel = createTagLabel(name1, tagWidth);
                        tags.getChildren().add(tagLabel);
                    });
        }
    }

    private Label createTagLabel(String tagName, double tagWidth) {
        Label tagLabel = new Label(tagName);
        tagLabel.getStyleClass().add("bookmark");

        // Set fixed width for tag label
        tagLabel.setMinWidth(tagWidth);
        tagLabel.setMaxWidth(tagWidth);
        tagLabel.setAlignment(Pos.CENTER); // Center-align text within label

        // Apply padding for visual consistency
        tagLabel.setStyle("-fx-padding: 5px 5px 5px 40px; -fx-alignment: center;");

        return tagLabel;
    }

    private void initializeRole() {
        if (person instanceof Buyer) {
            role.setText("Buyer");
            role.getStyleClass().add("buyer_label");
        } else {
            role.setText("Seller");
            role.getStyleClass().add("seller_label");
        }
    }

    // Getter methods for private fields
    public Label getId() {
        return id;
    }

    public Label getName() {
        return name;
    }

    public Label getPhone() {
        return phone;
    }

    public Label getEmail() {
        return email;
    }

    public FlowPane getTags() {
        return tags;
    }

    public Label getAppointment() {
        return appointment;
    }
}

