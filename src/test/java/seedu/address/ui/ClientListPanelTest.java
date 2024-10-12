package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.client.Buyer;
import seedu.address.model.client.Client;
import seedu.address.testutil.ClientBuilder;

public class ClientListPanelTest extends ApplicationTest {

    private ClientListPanel clientListPanel;
    private ObservableList<Client> clientObservableList;

    @BeforeEach
    public void setUp() {
        clientObservableList = FXCollections.observableArrayList();
        clientObservableList.add(new ClientBuilder().withName("Alice").buildBuyer());
        clientObservableList.add(new ClientBuilder().withName("Bob").buildBuyer());

        // Initialize ClientListPanel with some clients
        clientListPanel = new ClientListPanel(clientObservableList);
    }

    @Override
    public void start(Stage stage) {
        // Required setup for JavaFX application tests
    }

    @Test
    @Tag("gui")
    public void clientListView_initialized_correctly() {
        // Get access to the ListView directly and check its contents
        ListView<Client> clientListView = clientListPanel.getClientListView();
        assertEquals(clientObservableList, clientListView.getItems());
        assertEquals(2, clientListView.getItems().size());
        assertEquals("Alice", clientListView.getItems().get(0).getName().fullName);
        assertEquals("Bob", clientListView.getItems().get(1).getName().fullName);
    }

    @Test
    @Tag("gui")
    public void clientListViewCell_updateItem_setsCorrectGraphics() throws Exception {
        ClientListPanel.ClientListViewCell cell = clientListPanel.new ClientListViewCell();
        Client alice = new ClientBuilder().withName("Alice").buildBuyer();

        // Simulate updating the cell with a valid client
        Platform.runLater(() -> cell.updateItem(alice, false));
        WaitForAsyncUtils.waitForFxEvents(); // Ensure the UI update completes

        // Assert that the graphic (ClientCard) is set in the cell
        assertTrue(cell.getGraphic() instanceof Region);

        // Now we need to check the content of the graphic (ClientCard)
        Region graphic = (Region) cell.getGraphic();
        Label nameLabel = (Label) graphic.lookup("#name"); // the Label in ClientCard has fx:id="name"
        assertEquals("Alice", nameLabel.getText());

        // Simulate updating the cell with null (empty state)
        Platform.runLater(() -> cell.updateItem(null, true));
        WaitForAsyncUtils.waitForFxEvents();

        // Assert that the graphic and text are cleared when the cell is empty
        assertNull(cell.getGraphic());
        assertNull(cell.getText());
    }

    @Test
    @Tag("gui")
    public void clientListViewCell_updateItem_whenEmpty() {
        ClientListPanel.ClientListViewCell cell = clientListPanel.new ClientListViewCell();

        // Simulate updating the cell with empty and null client
        cell.updateItem(null, true);
        assertNull(cell.getGraphic());
        assertNull(cell.getText());
    }

    @Test
    @Tag("gui")
    public void clientListViewCell_updateItem_whenClientNotEmpty() {
        ClientListPanel.ClientListViewCell cell = clientListPanel.new ClientListViewCell();
        Buyer bob = new ClientBuilder().withName("Bob").buildBuyer();

        // Simulate updating the cell with a client
        cell.updateItem(bob, false);
        assertTrue(cell.getGraphic() instanceof Region);
        assertEquals("Bob", bob.getName().fullName);
    }
}
