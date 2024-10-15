package careconnect.ui;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * A fallback ui for the person detail section that is displayed as the right pane of the window.
 */
public class PersonDetailFallback extends UiPart<Region> {
    private static final String IMAGE_SEARCH = "/images/search.png";
    private static final String FXML = "PersonDetailFallback.fxml";

    @FXML
    private Rectangle imagePlaceholder;

    /**
     * Creates a fallback ui prompting user to select a person to view.
     */
    public PersonDetailFallback() {
        super(FXML);
        Image searchImage = new Image(
                Objects.requireNonNull(this.getClass().getResourceAsStream(IMAGE_SEARCH))
        );
        imagePlaceholder.setFill(new ImagePattern(searchImage));
    }
}
