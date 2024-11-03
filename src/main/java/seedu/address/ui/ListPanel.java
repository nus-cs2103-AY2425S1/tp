package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * Panel containing a list of objects of type T.
 * @param <T> the type of the objects in the list.
 */
public abstract class ListPanel<T> extends UiPart<Region> {
    public ListPanel(String fxml) {
        super(fxml);
    }
}
