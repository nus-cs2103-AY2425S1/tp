package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.address.model.car.Car;
import seedu.address.model.person.Person;

/**
 * Controller for view client page
 */
public class ViewClientWindow extends UiPart<Stage> {

    private static final String FXML = "ViewClientWindow.fxml";

    @FXML
    private Label clientNameLabel;

    @FXML
    private Label clientIdLabel;

    @FXML
    private Label clientPhoneLabel;

    @FXML
    private Label clientEmailLabel;

    @FXML
    private Label clientAddressLabel;

    @FXML
    private Label clientCarDetailsLabel;

    @FXML
    private FlowPane tags;

    /**
     * Creates a new ViewClientWindow.
     *
     * @param root Stage to use as the root of the ViewClientWindow.
     */
    public ViewClientWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new ViewClientWindow.
     */
    public ViewClientWindow() {
        this(new Stage());
    }

    /**
     * Shows the client details window.
     * @throws IllegalStateException if this method is called on a thread other than the JavaFX Application Thread.
     */
    public void show(Person client) {
        fillClientDetails(client);
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the client details window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the client details window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the client details window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Fills the client details labels with the given client's details.
     */
    private void fillClientDetails(Person client) {
        tags.getChildren().clear(); // tags is reused.

        // Client Details.
        clientNameLabel.setText("        Name: " + client.getName());
        clientPhoneLabel.setText("        Phone: " + client.getPhone());
        clientEmailLabel.setText("        Email: " + client.getEmail());
        clientAddressLabel.setText("        Address: " + client.getAddress());

        // Car Details.
        Car car = client.getCar();
        if (car == null) {
            clientCarDetailsLabel.setText("        No Car associated to Client");
            tags.getChildren().add(new Label("Not Applicable"));
            return;
        }
        String carDetails = String.format("        VRN: %s\n"
                + "        VIN: %s\n"
                + "        Make: %s\n"
                + "        Model: %s",
                car.getVrn(), car.getVin(), car.getCarMake(), car.getCarModel());
        clientCarDetailsLabel.setText(carDetails);

        // Car Issues
        if (client.getTags().isEmpty()) {
            tags.getChildren().add(new Label("No Issues"));
            return;
        }
        client.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Releases resources allocated to the client details window.
     */
    public void handleClose() {
        getRoot().close();
    }
}

