package seedu.ddd.ui;

import javafx.scene.layout.Region;

/**
 * Represents cards for {@code Displayable} items.
 */
public abstract class DisplayedCard extends UiPart<Region> {
    public DisplayedCard(String fxml) {
        super(fxml);
    }
}
