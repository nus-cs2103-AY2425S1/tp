package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of tags.
 */
public class TagListPanel extends UiPart<Region> {

    private static final String FXML = "TagListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TagListPanel.class);

    @FXML
    private ListView<Tag> tagListView;

    private MainWindow mainWindow;

    /**
     * Creates a {@code TagListPanel} with the given {@code ObservableList}.
     */
    public TagListPanel(ObservableList<Tag> tagList, MainWindow mainWindow) {
        super(FXML);
        tagListView.setItems(FXCollections.observableArrayList(tagList));
        tagListView.setCellFactory(listView -> new TagListViewCell());
        this.mainWindow = mainWindow;
    }

    /**
     * Updates the tag list with new data.
     */
    public void updateTagList(ObservableList<Tag> tagList) {
        ObservableList<Tag> sortedTags = FXCollections
                .observableArrayList(tagList);
        tagListView.setItems(sortedTags);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Tag} using a {@code TagCard}.
     */
    class TagListViewCell extends ListCell<Tag> {
        @Override
        protected void updateItem(Tag tag, boolean empty) {
            super.updateItem(tag, empty);

            if (empty || tag == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TagCard(tag, getIndex(), mainWindow).getRoot());
            }
        }
    }
}
