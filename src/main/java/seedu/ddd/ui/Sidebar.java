package seedu.ddd.ui;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class Sidebar extends UiPart<HBox> {

    private static final String FXML = "Sidebar.fxml";

    @FXML
    private StackPane appTitleContainer;

    @FXML
    private ImageView appTitle;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public Sidebar() {
        super(FXML);

        appTitleContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            appTitle.setFitHeight(appTitleContainer.getHeight());
            appTitle.setFitWidth(appTitleContainer.getWidth());
        });
    }
}
