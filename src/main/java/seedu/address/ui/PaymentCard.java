package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Payment;

/**
 * An UI component that displays information of a {@code Payment}.
 */
public class PaymentCard extends UiPart<Region> {

    private static final String FXML = "PaymentCard.fxml";

    private final Logger logger = LogsCenter.getLogger(PaymentCard.class);

    private final int payment;

    @FXML
    private Label month;
    @FXML
    private Label fees;
    @FXML
    private Label advance;
    @FXML
    private HBox paymentStatusContainer;
    @FXML
    private Label paymentStatus;
    @FXML
    private ImageView paymentStatusIcon;

    /**
     * Creates a {@code PaymentCard} with the given {@code Payment} to display.
     */
    public PaymentCard(Payment payment) {
        super(FXML);
        this.payment = Integer.parseInt(payment.overdueAmount);
        setDisplayMonth();
        setPaymentDetails();

        logger.info("Successfully created payment card for payment: " + this.payment);
    }

    /**
     * Sets the label to display the current month.
     */
    private void setDisplayMonth() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM ''yy");

        String displayString = "payment up till " + currentDate.format(formatter);
        month.setText(displayString);
    }

    /**
     * Sets the display according to the amount.
     */
    private void setPaymentDetails() {
        if (payment == 0) {
            fees.setText("$0");
            advance.setText("- ");
            setNoOverduePaymentStyle();
        } else if (payment < 0) {
            fees.setText("- ");
            advance.setText(Math.abs(payment) + "");
            setNoOverduePaymentStyle();
        } else {
            fees.setText("$" + payment);
            advance.setText("- ");
            setOverduePaymentStyle();
        }
    }

    /**
     * Sets the style to indicate overdue payment.
     */
    private void setOverduePaymentStyle() {
        paymentStatus.setText("OVERDUE");
        paymentStatus.setStyle("-fx-text-fill: #D16767");

        Image icon = new Image("/images/priority_icon.png");
        paymentStatusIcon.setImage(icon);

        paymentStatusContainer.setStyle("-fx-background-color: #FFF1F1");
    }

    /**
     * Sets the style to indicate no overdue payment.
     */
    private void setNoOverduePaymentStyle() {
        paymentStatus.setText("PAID");
        paymentStatus.setStyle("-fx-text-fill: #339A35");

        Image icon = new Image("/images/paid_icon.png");
        paymentStatusIcon.setImage(icon);

        paymentStatusContainer.setStyle("-fx-background-color: #DDF4DD");
    }
}
