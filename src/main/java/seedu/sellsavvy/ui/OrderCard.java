package seedu.sellsavvy.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.Status;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class OrderCard extends UiPart<Region> {

    public static final String ORDER_HEADING_FORMAT = "%1$s. %2$s (x%3$s)";
    public static final String DELIVER_BY_FORMAT = "Delivery by: %1$s";
    private static final String FXML = "OrderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final Order order;

    @FXML
    private HBox cardPane;
    @FXML
    private Label heading;
    @FXML
    private Label status;
    @FXML
    private Label date;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;
        heading.setText(String.format(ORDER_HEADING_FORMAT, displayedIndex,
                order.getItem().fullDescription, order.getQuantity().value));
        date.setText(String.format(DELIVER_BY_FORMAT, order.getDate().value));
        setStatus(order.getStatus());
    }

    /**
     * Sets the status displayed with the given {@code Status}.
     */
    private void setStatus(Status orderStatus) {
        status.setText(orderStatus.toString());
        status.getStyleClass().add(orderStatus.toString().toLowerCase());
    }
}

