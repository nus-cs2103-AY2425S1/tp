package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
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
    private Label property;
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
        initializeProperty();
        initializeTags();
        initializeRole();
    }

    private void initializeId(int displayedIndex) {
        id.setText(displayedIndex + ". ");
    }

    private void initializeName() {
        name.setText(person.getName().fullName);
    }

    private void initializeUnderline() {
        // Bind underline width to name label with adjustment
        underline.endXProperty().bind(name.widthProperty().add(45));
    }

    private void initializePhone() {
        phone.setText(person.getPhone().value);
    }

    private void initializeEmail() {
        email.setText(person.getEmail().value);
    }

    private void initializeAppointment() {
        if (person.getAppointment().isEmpty()) {
            appointment.setText("");
        } else {
            appointment.setText(person.getAppointment().toString());
        }
    }

    private void initializeProperty() {
        property.setText(person.getProperty().toString());
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

            // Sort and add tags to the UI
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        Label tagLabel = createTagLabel(tag.tagName);
                        tags.getChildren().add(tagLabel);
                    });
        }
    }

    private Label createTagLabel(String tagName) {
        Label tagLabel = new Label(tagName);
        tagLabel.getStyleClass().add("bookmark");

        // Measure the width of the text using a Text node
        Text tempText = new Text(tagName);
        tempText.setFont(tagLabel.getFont());
        double textWidth = tempText.getLayoutBounds().getWidth();

        // Calculate dynamic left padding based on text length
        double minPadding = 5;
        double dynamicLeftPadding = Math.max(minPadding, textWidth / 2);

        // Apply padding to the tag label
        tagLabel.setStyle(String.format("-fx-padding: %.0fpx %.0fpx %.0fpx %.0fpx;",
                minPadding, minPadding, minPadding, dynamicLeftPadding));

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

    public Label getProperty() {
        return property;
    }
}

