package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of tags.
 */
public class TagListPanel extends UiPart<Region> {
    private static final String FXML = "TagListPanel.fxml";
    @FXML
    private FlowPane tagListFlowPane;

    /**
     * Creates a {@code TagListPanel} with the given {@code ObservableList}.
     */
    public TagListPanel(ObservableList<Tag> tags) {
        super(FXML);
        updateTagsDisplay(tags);
        setUpListener(tags);
    }

    private void setUpListener(ObservableList<Tag> tags) {
        tags.addListener((ListChangeListener<Tag>) c -> {
            updateTagsDisplay(tags);
        });
    }

    private void updateTagsDisplay(ObservableList<Tag> tags) {
        tagListFlowPane.getChildren().clear();
        for (Tag tag : tags) {
            Label tagLabel = new Label(tag.getTagName());
            tagLabel.getStyleClass().add("tag-label");

            Color colour = TagColourManager.getColourForTag(tag.getTagName());
            tagLabel.setStyle(String.format("-fx-background-color: #%02x%02x%02x;",
                    (int) (colour.getRed() * 255),
                    (int) (colour.getGreen() * 255),
                    (int) (colour.getBlue() * 255)));

            tagListFlowPane.getChildren().add(tagLabel);
        }
    }
}
