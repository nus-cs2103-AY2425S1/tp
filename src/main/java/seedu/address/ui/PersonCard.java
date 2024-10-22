package seedu.address.ui;

import java.util.Comparator;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {
    private static final String FXML = "PersonListCard.fxml";
    private static final String NO_APPOINTMENT = "Date: , From: , To: ";
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

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     *
     * <p>This constructor initializes the UI components with the details of the specified {@code Person},
     * including the person's name, phone number, email, appointment, property, and tags.</p>
     *
     * @param person        The {@code Person} whose details are to be displayed in the card.
     * @param displayedIndex The index of the person in the list, used for display purposes.
     *                      This will be prefixed to the person's name in the UI.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        if (Objects.equals(person.getAppointment().toString(), NO_APPOINTMENT)) {
            appointment.setText("");
        } else {
            appointment.setText(person.getAppointment().toString());
        }
        property.setText(person.getProperty().toString());

        // Clear existing tags if necessary (in case of reuse)
        tags.getChildren().clear();


        if (person.getTags().isEmpty()) {
            tags.setVisible(false); // Hide tags section if no tags
        } else {
            tags.setVisible(true); // Show the tag section if there are tags

            // Set FlowPane properties
            tags.setHgap(5);
            tags.setVgap(5);
            tags.setAlignment(Pos.CENTER_RIGHT); // Align tags to the right

            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        // Create the tag label
                        Label tagLabel = new Label(tag.tagName);
                        tagLabel.getStyleClass().add("bookmark");

                        // Measure the width of the text using a Text node
                        Text tempText = new Text(tag.tagName);
                        tempText.setFont(tagLabel.getFont());
                        double textWidth = tempText.getLayoutBounds().getWidth();

                        // Calculate padding based on text length
                        double minPadding = 5; // Constant padding for top, right, and bottom
                        // Adjust the left padding based on text length
                        double dynamicLeftPadding = Math.max(minPadding, textWidth / 2);

                        // Apply padding
                        tagLabel.setStyle(String.format("-fx-padding: %.0fpx %.0fpx %.0fpx %.0fpx;",
                                minPadding, // top padding
                                minPadding, // right padding
                                minPadding, // bottom padding
                                dynamicLeftPadding)); // left padding

                        tags.getChildren().add(tagLabel);
                    });
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
