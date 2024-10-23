package seedu.address.ui;

import java.util.logging.Logger;

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
    private final Logger logger = LogsCenter.getLogger(OwnerListPanel.class);

    @FXML
    private ListView<Link> linkListView;

    /**
     * Creates an {@code OwnerListPanel} with the given {@code ObservableList}.
     */
    public LinkListPanel(ObservableList<Link> linkList) {
        super(FXML);
        linkListView.setItems(linkList);
        linkListView.setCellFactory(listView -> new LinkListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Owner} using an {@code OwnerCard}.
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
