package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    @FXML
    private FlowPane preferredTimes;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        if (phone.getText().isEmpty()) {
            phone.setMaxHeight(0);
            phone.setMinHeight(0);
            phone.setPrefHeight(0);
        }
        address.setText(person.getAddress().value);
        if (address.getText().isEmpty()) {
            address.setMaxHeight(0);
            address.setMinHeight(0);
            address.setPrefHeight(0);
        }
        email.setText(person.getEmail().value);
        if (email.getText().isEmpty()) {
            email.setMaxHeight(0);
            email.setMinHeight(0);
            email.setPrefHeight(0);
        }
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getGames().values().stream()
                .sorted(Comparator.comparing(game -> game.gameName))
                .forEach(game -> games.getChildren().add(gameTextFlow(game)));
        person.getPreferredTimes().stream()
                .sorted(Comparator.comparing(preferredTime -> preferredTime.preferredTime))
                .forEach(preferredTime -> preferredTimes.getChildren().add(new Label("Preferred time: "
                        + preferredTime.preferredTime)));
    }

    private static Label gameTextFlow(Game game) {
        // Create the main label that will hold everything
        Label label = new Label();

        // Image for favorite status
        ImageView imageView = null;
        if (game.getFavouriteStatus()) {
            Image image = new Image(String.valueOf(PersonCard.class.getResource("/images/star.png")));
            imageView = new ImageView(image);
            imageView.setFitHeight(20); // Adjust size as needed
            imageView.setPreserveRatio(true);
        }

        // Create styled "Game: gamename" part with a larger font
        Text gameLabel = new Text("Game: " + game.getGameName() + "\n");
        gameLabel.setFont(Font.font("Comfortaa", FontWeight.BOLD, 20)); // Larger font for game name
        gameLabel.setFill(Color.WHITE);

        // Build details text
        StringBuilder sb = new StringBuilder();
        Username username = game.getUsername();
        SkillLevel skillLevel = game.getSkillLevel();
        Role role = game.getRole();
        if (!username.getUsername().toString().isEmpty()) {
            sb.append("Username: ").append(username);
        }
        if (!skillLevel.toString().isEmpty()) {
            sb.append("\n").append("Skill Lvl: ").append(skillLevel);
        }
        if (!role.toString().isEmpty()) {
            sb.append("\n").append("Role: ").append(role);
        }

        // Add the details text
        Text detailsText = new Text(sb.toString());
        detailsText.setFont(Font.font("System", FontWeight.NORMAL, 14)); // Regular font for other details
        detailsText.setFill(Color.WHITE);

        // Create a TextFlow and add both game name and details texts
        TextFlow textFlow = new TextFlow(gameLabel, detailsText);

        // Set up an HBox to hold the ImageView and TextFlow side-by-side
        HBox hbox = new HBox(5); // Add spacing between image and text
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(5));
        if (imageView != null) {
            hbox.getChildren().add(imageView); // Add star image on the left if favorite
        }

        hbox.getChildren().add(textFlow); // Add the text to the right of the image

        // Set HBox as the graphic of the label
        label.setGraphic(hbox);
        label.setPrefHeight(textFlow.getHeight());
        return label;
    }
}
