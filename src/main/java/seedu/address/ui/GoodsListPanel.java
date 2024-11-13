package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.goodsreceipt.GoodsReceipt;

/**
 * Panel containing the list of goods.
 */
public class GoodsListPanel extends UiPart<Region> {
    private static final String FXML = "GoodsListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GoodsListPanel.class);

    @javafx.fxml.FXML
    private ListView<GoodsReceipt> goodsListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public GoodsListPanel(ObservableList<GoodsReceipt> goodsList) {
        super(FXML);
        goodsListView.setItems(goodsList);
        goodsListView.setCellFactory(listView -> new GoodsListPanel.GoodsListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class GoodsListViewCell extends ListCell<GoodsReceipt> {
        @Override
        protected void updateItem(GoodsReceipt goodsReceipt, boolean empty) {
            super.updateItem(goodsReceipt, empty);

            if (empty || goodsReceipt == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GoodsCard(goodsReceipt, getIndex() + 1).getRoot());
            }
        }
    }

}
