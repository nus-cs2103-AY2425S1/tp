package seedu.ddd.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.ddd.model.Displayable;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Event;

/**
 * Panel containing the list of displayed contacts/events.
 */
public class DisplayedListPanel extends UiPart<Region> {

    private static final String FXML = "DisplayedListPanel.fxml";

    @FXML
    private ListView<Displayable> displayedListView;

    /**
     * Creates a {@code DisplayedListPanel} with the given {@code ObservableList}.
     */
    public DisplayedListPanel(ObservableList<Displayable> displayedList) {
        super(FXML);
        displayedListView.setItems(displayedList);
        displayedListView.setCellFactory(listView -> new ContactListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Displayable}
     * using a {@code ClientCard}, {@code VendorCard} or a {@Code EventCard}.
     */
    class ContactListViewCell extends ListCell<Displayable> {
        @Override
        protected void updateItem(Displayable displayableItem, boolean empty) {
            super.updateItem(displayableItem, empty);

            if (empty || displayableItem == null) {
                setGraphic(null);
                setText(null);
            } else if (displayableItem instanceof Client client) {
                setGraphic(new ClientCard(client, getIndex() + 1).getRoot());
            } else if (displayableItem instanceof Vendor vendor) {
                setGraphic(new VendorCard(vendor, getIndex() + 1).getRoot());
            } else if (displayableItem instanceof Event event) {
                setGraphic(new EventCard(event, getIndex() + 1).getRoot());
            }
        }
    }
}
