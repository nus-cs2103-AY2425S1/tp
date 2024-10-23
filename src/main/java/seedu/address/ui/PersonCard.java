package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.person.Person;
import seedu.address.model.person.RsvpStatus;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String PENDING_STYLE = "-fx-background-color: #eba250; -fx-background-radius: 2";
    private static final String COMING_STYLE = "-fx-background-color: #85bd80; -fx-background-radius: 2";
    private static final String NOT_COMING_STYLE = "-fx-background-color: #DD0000; -fx-background-radius: 2; "
           + "-fx-text-fill: white";

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
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label isRsvp;



    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.getStyleClass().add("tag-label");

                    Color color = TagColourManager.getColourForTag(tag.tagName);
                    tagLabel.setStyle(String.format("-fx-background-color: #%02x%02x%02x;",
                            (int) (color.getRed() * 255),
                            (int) (color.getGreen() * 255),
                            (int) (color.getBlue() * 255)));

                    tags.getChildren().add(tagLabel);
                });
        isRsvp.setText(person.getRsvpStatusCard());

        // set colour of rsvp status
        RsvpStatus rsvpStatus = person.getRsvpStatus();
        switch (rsvpStatus) {

        case COMING:
            isRsvp.setStyle(COMING_STYLE);
            break;

        case NOT_COMING:
            isRsvp.setStyle(NOT_COMING_STYLE);
            break;

        default:
            isRsvp.setStyle(PENDING_STYLE);
            break;

        }

    }
}
