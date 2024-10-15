package seedu.address.ui;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;

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
    private Label studentId;
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
    private FlowPane tutorials;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        studentId.setText(person.getStudentId().value);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        Set<Tutorial> tutorialSet = person.getTutorials();
        IntStream.rangeClosed(1, 10)
                .mapToObj(Integer::toString)
                .forEach(index -> {
                    Label tutorialLabel = new Label(index);
                    tutorialLabel.setStyle("-fx-background-color: #8c2029; -fx-pref-width: 50px; "
                            + "-fx-alignment: center; -fx-padding: 5px; -fx-border-color: white; "
                            + "-fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;");

                    if (tutorialSet.contains(new Tutorial(index))) {
                        tutorialLabel.setStyle("-fx-background-color: #206e35; -fx-pref-width: 50px; "
                                + "-fx-alignment: center; -fx-padding: 5px; -fx-border-color: white; "
                                + "-fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
                    }
                    tutorials.getChildren().add(tutorialLabel);
                });
    }
}
