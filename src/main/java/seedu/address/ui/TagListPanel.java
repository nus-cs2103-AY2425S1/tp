package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of tags.
 */
public class TagListPanel extends UiPart<Region> {
    private static final String FXML = "TagListPanel.fxml";
    @FXML
    private ListView<Tag> tagListView;

    /**
     * Creates a {@code TagListPanel} with the given {@code ObservableList}.
     */
    public TagListPanel(ObservableList<Tag> tags) {
        super(FXML);
        tagListView.setItems(tags);
        System.out.println(tagListView.getItems());
        tagListView.setCellFactory(listView -> new TagListViewCell());

        tags.addListener((ListChangeListener<Tag>) c -> {
            System.out.println("TagListPanel: Detected change in tag list.");
            while (c.next()) {
                if (c.wasAdded() || c.wasRemoved()) {
                    System.out.println("Tags added/removed.");
                    tagListView.refresh();
                }
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Tag} using a {@code Label}.
     */
    class TagListViewCell extends ListCell<Tag> {
        @Override
        protected void updateItem(Tag tag, boolean empty) {
            super.updateItem(tag, empty);

            if (empty || tag == null) {
                setGraphic(null);
                setText(null);
            } else {
                Label tagLabel = new Label(tag.getTagName());
                tagLabel.getStyleClass().add("tag-label");
                setGraphic(tagLabel);
            }
        }
    }

}
