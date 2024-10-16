package seedu.address.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * A UI component that displays detailed information of a selected contact.
 */
public class ContactDisplay extends VBox {
    public static final String CONDENSED_HELP_MESSAGE = "Adding a person: add\n"
            + "Adds a person to the address book.\n"
            + "\n" + "Format: add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…\u200B \n---\n"
            + "Listing all persons : list\n"
            + "Shows a list of all persons in the address book.\n"
            + "\n" + "Format: list\n---\n"
            + "Editing a person : edit\n"
            + "Edits an existing person in the address book.\n" + "\n"
            + "Format: edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…\u200B \n---\n"
            + "Locating persons by name: find\n"
            + "Finds persons whose names contain any of the given keywords.\n"
            + "\n" + "Format: find KEYWORD [MORE_KEYWORDS]\n---\n"
            + "Deleting a person : delete\n"
            + "Deletes the specified person from the address book.\n"
            + "\n" + "Format: delete INDEX\n---\n"
            + "Clearing all entries : clear\n"
            + "Clears all entries from the address book.\n"
            + "\n" + "Format: clear\n---\n"
            + "Exiting the program : exit\n"
            + "Exits the program.\n" + "\n" + "Format: exit\n";
    private Label nameLabel;
    private Label phoneLabel;
    private Label emailLabel;

    /**
     * Constructs a ContactDisplay with default placeholder labels.
     */

    public ContactDisplay() {
        nameLabel = new Label("Name:");
        phoneLabel = new Label("Phone:");
        emailLabel = new Label("Email:");

        getChildren().addAll(nameLabel, phoneLabel, emailLabel);
        setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");
    }

    /**
     * Updates the contact display with the details of the specified person.
     *
     * @param person The person whose details are to be displayed.
     */
    public void updateContactDetails(Person person) {
        nameLabel.setText("Name: " + person.getName());
        phoneLabel.setText("Phone: " + person.getPhone());
        emailLabel.setText("Email: " + person.getEmail());
    }

    /**
     * Clears the contact details, resetting the labels to their default placeholders.
     */
    public void clear() {
        nameLabel.setText("Name:");
        phoneLabel.setText("Phone:");
        emailLabel.setText("Email:");
    }

    /**
     * Shows the condensed help message over the contact display.
     */
    public void showHelpDisplay() {
        nameLabel.setText(CONDENSED_HELP_MESSAGE);
        phoneLabel.setText(null);
        emailLabel.setText(null);
    }
}

