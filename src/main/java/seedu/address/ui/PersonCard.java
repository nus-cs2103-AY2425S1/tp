package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.game.Game;
import seedu.address.model.game.Role;
import seedu.address.model.game.SkillLevel;
import seedu.address.model.game.Username;
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
    private FlowPane tags;
    @FXML
    private FlowPane games;

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
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getGames().values().stream()
                .sorted(Comparator.comparing(game -> game.gameName))
                .forEach(game -> games.getChildren().add(gameLabel(game)));
    }

    private static Label gameLabel(Game game) {
        StringBuilder sb = new StringBuilder();
        Username username = game.getUsername();
        SkillLevel skillLevel = game.getSkillLevel();
        Role role = game.getRole();
        sb.append(game.getGameName()).append("\n");
        if (username.getUsername() != "") {
            sb.append("Username: ").append(game.getUsername()).append("\n");
        }
        if (skillLevel.getSkillLevel() != "") {
            sb.append("Skill Lvl: ").append(game.getSkillLevel()).append("\n");
        }
        if (role.getRole() != "") {
            sb.append("Role: ").append(game.getRole());
        }
        assert !sb.toString().equals("");
        return new Label(sb.toString());
    }

}
