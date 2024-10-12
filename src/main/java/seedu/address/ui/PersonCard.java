package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
    private Label assignment;
    @FXML
    private FlowPane tags;
    @FXML
    private Hyperlink telegram;
    @FXML
    private Label github;

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
        if (person.getAssignment() != null) {
            assignment.setText(person.getAssignment().toString());
        } else {
            assignment.setText("No assignment available"); // Optional: for better user feedback
        }
      
        /*
        Adopted from ChatGPT.
         */
        // Set the telegram hyperlink
        if (person.getTelegram() != null) {
            telegram.setText(person.getTelegram().value);
            telegram.setOnAction(event -> openTelegramLink(person.getTelegram().value));
        } else {
            telegram.setDisable(true); // Disable hyperlink if no Telegram ID
        }
      
        if (person.getGithub() != null) {
            github.setText(person.getGithub().toString());
        } else {
            github.setText("GitHub username unspecified");
        }

    }

    // Adopted from GPT with the java.awt.Desktop.getDesktop().browse(...)
    /**
     * Opens the Telegram link in the default web browser.
     */
    private void openTelegramLink(String telegramId) {
        // This is to remove the '@' when keying into the telegram link
        char[] charArray = telegramId.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < charArray.length; i++) {
            sb.append(charArray[i]);
        }

        try {
            String url = "https://t.me/" + sb.toString();
            java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
