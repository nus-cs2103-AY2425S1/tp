package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.owner.Owner;
import seedu.address.model.pet.Pet;

/**
 * Panel containing two lists: one for owners and one for pets.
 */
public class CombinedListPanel extends UiPart<Region> {
    private static final String FXML = "CombinedListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CombinedListPanel.class);

    @FXML
    private ListView<Owner> ownerListView;

    @FXML
    private ListView<Pet> petListView;

    /**
     * Creates a {@code CombinedListPanel} with the given {@code ObservableList}.
     */
    public CombinedListPanel(ObservableList<Owner> ownerList, ObservableList<Pet> petList) {
        super(FXML);
        ownerListView.setItems(ownerList);
        ownerListView.setCellFactory(listView -> new OwnerListViewCell());

        petListView.setItems(petList);
        petListView.setCellFactory(listView -> new PetListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Owner} using a {@code OwnerCard}.
     */
    class OwnerListViewCell extends ListCell<Owner> {
        @Override
        protected void updateItem(Owner owner, boolean empty) {
            super.updateItem(owner, empty);

            if (empty || owner == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OwnerCard(owner, getIndex() + 1).getRoot());
            }
        }
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
