package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import seedu.address.model.client.Client;
import seedu.address.testutil.ClientBuilder;

public class ClientCardTest extends ApplicationTest {

    private ClientCard clientCard;
    private Client client;
    private int displayedIndex = 1;

    @BeforeEach
    public void setUp() {
        client = new ClientBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@example.com").buildBuyer();

        clientCard = new ClientCard(client, displayedIndex);
    }

    @Override
    public void start(Stage stage) {
        // Required setup for JavaFX application tests
    }

    @Test
    @Tag("gui")
    public void display_correctlyInitializesUi() {
        // Verify that the client card's UI components are displaying the correct information
        Label idLabel = (Label) clientCard.getRoot().lookup("#id");
        Label nameLabel = (Label) clientCard.getRoot().lookup("#name");
        Label phoneLabel = (Label) clientCard.getRoot().lookup("#phone");
        Label emailLabel = (Label) clientCard.getRoot().lookup("#email");

        assertEquals(displayedIndex + ". ", idLabel.getText());
        assertEquals("Alice", nameLabel.getText());
        assertEquals("12345678", phoneLabel.getText());
        assertEquals("alice@example.com", emailLabel.getText());
    }

    @Test
    @Tag("gui")
    public void testCardPaneInitialized() {
        // Verify that the HBox cardPane is not null and is part of the UI
        HBox cardPane = (HBox) clientCard.getRoot().lookup("#cardPane");
        assertEquals(clientCard.getCardPane(), cardPane);
    }

    @Test
    @Tag("gui")
    public void testClientCardWithDifferentClient() {
        Client anotherClient = new ClientBuilder().withName("Bob").withPhone("98765432")
                .withEmail("bob@example.com").buildBuyer();
        ClientCard anotherClientCard = new ClientCard(anotherClient, 2);

        Label idLabel = (Label) anotherClientCard.getRoot().lookup("#id");
        Label nameLabel = (Label) anotherClientCard.getRoot().lookup("#name");
        Label phoneLabel = (Label) anotherClientCard.getRoot().lookup("#phone");
        Label emailLabel = (Label) anotherClientCard.getRoot().lookup("#email");

        assertEquals("2. ", idLabel.getText());
        assertEquals("Bob", nameLabel.getText());
        assertEquals("98765432", phoneLabel.getText());
        assertEquals("bob@example.com", emailLabel.getText());
    }
}
