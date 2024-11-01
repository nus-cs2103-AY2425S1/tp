package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
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
        initialiseLabel();
        updateTagsDisplay(tags);
        setUpListener(tags);
    }

    private void initialiseLabel() {
        Label tagsTitleLabel = new Label("Tags: ");
        tagsTitleLabel.getStyleClass().add("tags-title-label");
        tagListFlowPane.getChildren().add(tagsTitleLabel);
    }

    private void updateTagsDisplay(ObservableList<Tag> tags) {
        tagListFlowPane.getChildren().clear();
        initialiseLabel();

        tags.stream()
                .sorted(Comparator.comparing(tag -> tag.getTagName().toLowerCase()))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.getTagName());
                    tagLabel.getStyleClass().add("tag-label");

                    Color colour = TagColourManager.getColourForTag(tag.getTagName());
                    tagLabel.setStyle(String.format("-fx-background-color: #%02x%02x%02x;",
                            (int) (colour.getRed() * 255),
                            (int) (colour.getGreen() * 255),
                            (int) (colour.getBlue() * 255)));

                    tagListFlowPane.getChildren().add(tagLabel);
                });
        tagListFlowPane.requestLayout();
    }

    private void setUpListener(ObservableList<Tag> tags) {
        tags.addListener((ListChangeListener<Tag>) c -> {
            updateTagsDisplay(tags);
        });
    }
}
