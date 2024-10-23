package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
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

    /**
     * Creates an {@code OwnerListPanel} with the given {@code ObservableList}.
     */
    public LinkListPanel(ObservableList<Link> linkList) {
        super(FXML);
        System.out.println(linkList);
        ObservableList<Link> reversedList = FXCollections.observableArrayList(linkList);
        FXCollections.reverse(reversedList); // display the most recently created link first
        linkListView.setItems(reversedList);
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
