package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.pet.Pet;

/**
 * Panel containing the list of pets.
 */
public class PetListPanel extends UiPart<Region> {
    private static final String FXML = "PetListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PetListPanel.class);

    @FXML
    private ListView<Pet> petListView;

    /**
     * Creates a {@code PetListPanel} with the given {@code ObservableList}.
     */
    public PetListPanel(ObservableList<Pet> petList) {
        super(FXML);

        petListView.setPlaceholder(new Label("No Pets"));
        petListView.setItems(petList);
        petListView.setCellFactory(listView -> new PetListViewCell());

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Pet} using a {@code PetCard}.
     */
    class PetListViewCell extends ListCell<Pet> {
        @Override
        protected void updateItem(Pet pet, boolean empty) {
            super.updateItem(pet, empty);

            if (empty || pet == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PetCard(pet, getIndex() + 1).getRoot());
            }
        }
    }
}
