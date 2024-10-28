package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Payment;

/**
 * An UI component that displays information of a {@code Attendance}.
 */
public class PaymentCard extends UiPart<Region> {

    private static final String FXML = "PaymentCard.fxml";

    public final int payment;

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
     * Creates a {@code AttendanceCard} of the given tutorial
     */
    public PaymentCard(Payment payment) {
        super(FXML);
        this.payment = Integer.parseInt(payment.overdueAmount);
        setDisplayMonth();
        setPaymentDetails();
    }

    private void setDisplayMonth() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM ''yy");

        String displayString = "up till " + currentDate.format(formatter);
        month.setText(displayString);
    }

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

    private void setOverduePaymentStyle() {
        paymentStatus.setText("OVERDUE");
        paymentStatus.setStyle("-fx-text-fill: #D16767");

        Image icon = new Image("/images/priority_icon.png");
        paymentStatusIcon.setImage(icon);

        paymentStatusContainer.setStyle("-fx-background-color: #FFF1F1");
    }

    private void setNoOverduePaymentStyle() {
        paymentStatus.setText("PAID");
        paymentStatus.setStyle("-fx-text-fill: #339A35");

        Image icon = new Image("/images/paid_icon.png");
        paymentStatusIcon.setImage(icon);

        paymentStatusContainer.setStyle("-fx-background-color: #DDF4DD");
    }
}
