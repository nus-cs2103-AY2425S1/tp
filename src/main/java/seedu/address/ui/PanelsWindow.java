package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * A panel containing a list of persons and a list of either weddings or tasks, side by side.
 */
public class PanelsWindow extends UiPart<Region> {
    private static final String FXML = "PanelsWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(PanelsWindow.class);

    @FXML
    private HBox panelsContainer;

    @FXML
    private VBox personList;

    @FXML
    private VBox entityList;

    /**
     * Constructs a panel window with a list of persons and a list of weddings side by side.
     * @param persons A {@code ListPanel} of {@code Person} objects
     * @param weddings A {@code ListPanel} of {@code Wedding} objects
     */
    public PanelsWindow(ListPanel<Person> persons, ListPanel<Wedding> weddings) {
        super(FXML);
        personList.getChildren().add(persons.getRoot());
        // default view for the right panel is weddings
        entityList.getChildren().add(weddings.getRoot());
    }

    /**
     * Binds the widths of the panels to be half the weight of their container.
     */
    public void setWidths() {
        personList.prefHeightProperty()
                .bind(panelsContainer.widthProperty()
                        .subtract(panelsContainer.getSpacing() / (double) 2));
        entityList.prefHeightProperty()
                .bind(panelsContainer.widthProperty()
                        .subtract(panelsContainer.getSpacing() / (double) 2));
    }

    /**
     * Sets the panel to show the given panel.
     * @param panel The panel containing objects of type S.
     * @param <S> The type of object that is in the list.
     */
    public <S> void switchView(ListPanel<S> panel) {
        entityList.getChildren().clear();
        entityList.getChildren().add(panel.getRoot());
    }
}
