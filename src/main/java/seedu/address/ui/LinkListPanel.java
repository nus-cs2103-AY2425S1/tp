package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.link.Link;

/**
 * Panel containing the list of links.
 */
public class LinkListPanel extends UiPart<Region> {
    private static final String FXML = "LinkListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LinkListPanel.class);

    @FXML
    private ListView<Link> linkListView;

    private ObservableList<Link> originalLinkList;

    /**
     * Creates an {@code LinkListPanel} with the given {@code ObservableList}.
     */
    public LinkListPanel(ObservableList<Link> linkList) {
        /*
        super(FXML);
        // Create a reversed copy of the original list to display the most recent links first
        ObservableList<Link> reversedList = FXCollections.observableArrayList(linkList);
        FXCollections.reverse(reversedList); // Reverse the copy

        linkListView.setItems(reversedList); // Set the reversed list into the ListView
        linkListView.setCellFactory(listView -> new LinkListViewCell());

         */

        super(FXML);
        this.originalLinkList = linkList;

        // Add listener to update when list changes
        linkList.addListener((ListChangeListener<Link>) change -> updateListView());

        // Initialize the list view with reversed items
        updateListView();
    }

    /**
     * Reverses the list and updates the ListView with the latest items.
     */
    private void updateListView() {
        ObservableList<Link> reversedList = FXCollections.observableArrayList(originalLinkList);
        FXCollections.reverse(reversedList);  // Reverse the list for display
        linkListView.setItems(reversedList);  // Set the reversed list to the view
        linkListView.setCellFactory(listView -> new LinkListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Link} using a {@code LinkCard}.
     */
    class LinkListViewCell extends ListCell<Link> {
        @Override
        protected void updateItem(Link link, boolean empty) {
            super.updateItem(link, empty);

            if (empty || link == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LinkCard(link, getIndex() + 1).getRoot());
            }
        }
    }
}
