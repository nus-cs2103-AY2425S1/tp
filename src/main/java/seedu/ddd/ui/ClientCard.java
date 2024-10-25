package seedu.ddd.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.ddd.model.contact.client.Client;

/**
 * An UI component that displays information of a {@code Client}.
 */
public class ClientCard extends DisplayedCard {

    private static final String FXML = "ClientCard.fxml";

    public final Client client;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label email;

    @FXML
    private Label address;

    @FXML
    private FlowPane tags;

    @FXML
    private Label id;

    /**
     * Creates a {@code Client} with the given {@code client} and index to display.
     */
    public ClientCard(Client client, int displayedIndex) {
        super(FXML);
        this.client = client;

        name.setText(String.format("%s. %s", displayedIndex, client.getName().fullName));
        id.setText(String.format("Client: #%d", client.getId().contactId));

        phone.setText(String.format("📞  Phone Number: %s", client.getPhone()));
        email.setText(String.format("📫  Email: %s", client.getEmail()));
        address.setText(String.format("🏠  Address: %s", client.getAddress()));
        client.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
