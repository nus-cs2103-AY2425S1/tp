package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.model.listing.Listing;
import seedu.address.testutil.TypicalListings;

public class ListingListPanelUiTest extends ApplicationTest {
    private ListingListPanel listingListPanel;
    private ListView<Listing> listingListView;

    @Override
    public void start(Stage stage) {
        listingListPanel = new ListingListPanel(TypicalListings.getTypicalListings().getListingList());

        Scene scene = new Scene(listingListPanel.getRoot(), 400, 600);
        stage.setScene(scene);
        stage.show();

        listingListView = lookup("#listingListView").query();
    }

    @Test
    public void listingListView_isRendered() {
        assertNotNull(listingListPanel);
        assertEquals(TypicalListings.getTypicalListings().getListingList().size(),
                listingListView.getItems().size());
    }

    @Test
    public void listingListView_displaysListingsCorrectly() {
        for (int i = 0; i < TypicalListings.getTypicalListings().getListingList().size(); i++) {
            Listing expectedListing = TypicalListings.getTypicalListings().getListingList().get(i);
            Listing displayedListing = listingListView.getItems().get(i);
            assertEquals(expectedListing, displayedListing);
        }
    }
}
