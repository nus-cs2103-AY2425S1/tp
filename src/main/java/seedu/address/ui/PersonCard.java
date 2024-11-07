package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.parser.ParserUtil;
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
    private FlowPane tags;
    @FXML
    private Label telegram;
    @FXML
    private Label github;
    @FXML
    private FlowPane weekLabel;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        telegram.setText(person.getTelegram().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        if (person.getGithub() != null) {
            github.setText(person.getGithub().toString());
        } else {
            github.setText("GitHub username unspecified");
        }

        Label labelOfWeek = new Label("Weeks attended: ");
        if (!person.getWeeksPresent().isEmpty()) {
            labelOfWeek.getStyleClass().add("information-label");
            weekLabel.getChildren().add(labelOfWeek);

            for (int week = 0; week <= ParserUtil.MAX_WEEK; week++) {
                Label weekLabelNode = new Label(String.valueOf(week));

                if (person.getWeeksPresent().contains(week)) {
                    weekLabelNode.getStyleClass().add("week-number-marked");
                } else {
                    weekLabelNode.getStyleClass().add("week-number-unmarked");
                }
                weekLabel.getChildren().add(weekLabelNode);
            }
        } else {
            labelOfWeek = new Label("No weeks attended");
            labelOfWeek.getStyleClass().add("information-label");
            weekLabel.getChildren().add(labelOfWeek);
        }
    }

}
