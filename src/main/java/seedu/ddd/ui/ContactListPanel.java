package seedu.ddd.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.vendor.Vendor;

/**
 * Panel containing the list of contacts.
 */
public class ContactListPanel extends UiPart<Region> {

    private static final String FXML = "ContactListPanel.fxml";

    @FXML
    private ListView<Contact> contactListView;

    /**
     * Creates a {@code ContactListPanel} with the given {@code ObservableList}.
     */
    public ContactListPanel(ObservableList<Contact> contactList) {
        super(FXML);
        contactListView.setItems(contactList);
        contactListView.setCellFactory(listView -> new ContactListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Contact}
     * using a {@code ClientCard} or a {@code VendorCard}.
     */
    class ContactListViewCell extends ListCell<Contact> {
        @Override
        protected void updateItem(Contact contact, boolean empty) {
            super.updateItem(contact, empty);

            if (empty || contact == null) {
                setGraphic(null);
                setText(null);
            } else if (contact instanceof Client client) {
                setGraphic(new ClientCard(client, getIndex() + 1).getRoot());
            } else if (contact instanceof Vendor vendor) {
                setGraphic(new VendorCard(vendor, getIndex() + 1).getRoot());
            }
        }
    }
}
