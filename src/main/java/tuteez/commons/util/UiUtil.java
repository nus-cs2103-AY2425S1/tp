package tuteez.commons.util;

import java.util.Comparator;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import tuteez.model.person.Person;
import tuteez.model.person.TelegramUsername;

/**
 * A container for Ui specific utility functions
 */
public class UiUtil {

    /**
     * Sets the name label text for the {@code Person}.
     *
     * @param nameLabel The label to update with the person's name.
     * @param person    The {@code Person} whose name is to be displayed.
     */
    public static void setNameText(Label nameLabel, Person person) {
        assert(person != null && nameLabel != null);
        nameLabel.setText(person.getName().fullName);
    }

    /**
     * Sets the phone label text for the {@code Person}.
     *
     * @param phoneLabel The label to update with the person's phone number.
     * @param person     The {@code Person} whose phone number is to be displayed.
     */
    public static void setPhoneText(Label phoneLabel, Person person) {
        assert(person != null && phoneLabel != null);
        phoneLabel.setText(person.getPhone().value);
    }

    /**
     * Sets the address label text if an address exists for the {@code Person}.
     * Hides the address label if no address is present.
     *
     * @param addressLabel The label to update with the address.
     * @param person      The person whose address to display on the label.
     */
    public static void setAddressText(Label addressLabel, Person person) {
        assert(person != null && addressLabel != null);
        if (person.getAddress().value != null) {
            addressLabel.setText(person.getAddress().value);
            addressLabel.setVisible(true);
        } else {
            addressLabel.setVisible(false);
        }
    }

    /**
     * Sets the Telegram username label text if a Telegram username exists for the {@code Person}.
     * Hides the Telegram label if no username is present.
     *
     * @param telegramLabel The label to update with the Telegram username.
     * @param person        The person whose Telegram username to display on the label.
     */
    public static void setTelegramUsernameText(Label telegramLabel, Person person) {
        assert(person != null && telegramLabel != null);
        TelegramUsername username = person.getTelegramUsername();
        if (username != null && username.telegramUsername != null && !username.telegramUsername.isEmpty()) {
            telegramLabel.setText("@" + username.telegramUsername);
            telegramLabel.setVisible(true);
        } else {
            telegramLabel.setVisible(false);
        }
    }

    /**
     * Sets the email label text if an email exists for the {@code Person}.
     * Hides the email label if no email is present.
     *
     * @param emailLabel The label to update with the email.
     * @param person The {@code Person} whose email is to be displayed.
     */
    public static void setEmailText(Label emailLabel, Person person) {
        assert(person != null && emailLabel != null);
        if (person.getEmail().value != null) {
            emailLabel.setText(person.getEmail().value);
            emailLabel.setVisible(true);
        } else {
            emailLabel.setVisible(false);
        }
    }

    /**
     * Sets the tags associated with the {@code Person} in the tags flow pane.
     * Sorts the tags alphabetically for consistent ordering.
     *
     * @param tagsPane The flow pane to update with the tags.
     * @param person   The {@code Person} whose tags are to be displayed.
     */
    public static void setTags(FlowPane tagsPane, Person person) {
        assert(person != null && tagsPane != null);
        tagsPane.getChildren().clear(); // Clear previous tags
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tagsPane.getChildren().add(new Label(tag.tagName)));
    }
}
