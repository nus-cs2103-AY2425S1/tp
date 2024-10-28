package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Panel containing the list of weddings.
 */
public class WeddingListPanel extends UiPart<Region> {
    private static final String FXML = "WeddingListPanel.fxml";

    @FXML
    private VBox currentWeddingNameCardContainer;

    @FXML
    private ListView<Wedding> weddingListView;

    /**
     * Creates a {@code WeddingListPanel} with the given {@code ObservableList}.
     */
    public WeddingListPanel(ObservableList<Wedding> weddingList, ObjectProperty<WeddingName> currentWeddingName) {
        super(FXML);

        StringBinding weddingNameBinding = Bindings.createStringBinding(
                () -> currentWeddingName.get() == null
                        ? "Not viewing any wedding"
                        : "Viewing: " + currentWeddingName.getValue().toString(), currentWeddingName
        );

        // Set up then bind the CurrentWeddingNameCard
        CurrentWeddingNameCard currentWeddingNameCard = new CurrentWeddingNameCard();
        currentWeddingNameCard.bindCurrentWeddingName(weddingNameBinding);
        currentWeddingNameCardContainer.getChildren().add(currentWeddingNameCard.getRoot());

        // Set up the ListView with weddings
        weddingListView.setItems(weddingList);
        weddingListView.setCellFactory(listView -> new WeddingListViewCell());
    }

    class WeddingListViewCell extends ListCell<Wedding> {
        @Override
        protected void updateItem(Wedding wedding, boolean empty) {
            super.updateItem(wedding, empty);

            if (empty || wedding == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WeddingCard(wedding, getIndex() + 1).getRoot());
            }
        }
    }
}