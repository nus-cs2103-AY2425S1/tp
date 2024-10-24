package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seedu.address.model.wedding.Datetime;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

public class WeddingListPanelTest {

    private static final ObservableList<Wedding> SAMPLE_WEDDINGS = FXCollections.observableArrayList(
            new Wedding(new WeddingName("Alice & Bob"), new Venue("Marina Bay Sands"), new Datetime("22/12/2025")),
            new Wedding(new WeddingName("Charlie & Diana"), new Venue("Sentosa Beach"), new Datetime("15/06/2026"))
    );

    @BeforeAll
    public static void setUpClass() {
        if (!javafx.application.Platform.isFxApplicationThread()) {
            javafx.application.Platform.startup(() -> {});
        }
    }

    @Test
    public void constructor_validWeddingList_initializesSuccessfully() {
        WeddingListPanel weddingListPanel = new WeddingListPanel(SAMPLE_WEDDINGS);

        assertNotNull(weddingListPanel);

        Parent root = weddingListPanel.getRoot();
        assertNotNull(root);
        assertTrue(root instanceof Parent);
    }
}
