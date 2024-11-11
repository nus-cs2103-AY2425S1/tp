package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Tag}.
 */
public class TagCard extends UiPart<Region> {

    private static final String FXML = "TagCard.fxml";

    public final Tag tag;

    @FXML
    private HBox cardPane;
    @FXML
    private Label tagName;

    private MainWindow mainWindow;

    /**
     * Creates a {@code TagCard} with the given {@code Tag} and index to display.
     */
    public TagCard(Tag tag, int displayedIndex, MainWindow mainWindow) {
        super(FXML);
        this.mainWindow = mainWindow;
        this.tag = tag;
        tagName.setText((displayedIndex + 1) + ". " + tag.tagName);

        String color = tag.getTagColour();
        tagName.setStyle("-fx-text-fill: " + color + ";");

        tagName.setOnMouseClicked(event -> showTagDetails());
    }

    /**
     * Displays tag details in the ResultDisplay component of MainWindow.
     */
    private void showTagDetails() {
        String details = "Tag Name: " + tag.tagName + "\nCategory: " + tag.getTagCategory().toString();
        mainWindow.updateResultDisplay(details);
    }
}
