package seedu.address.ui;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;

/**
 *  The client window provides detailed information about the clients, such as name, phone, email, address, conditions
 *  and schedule
 */
public class ClientWindow extends UiPart<Stage> implements Initializable {

    private static final Logger logger = LogsCenter.getLogger(ClientWindow.class);
    private static final String FXML = "ClientWindow.fxml";

    @FXML
    private Label clientName;

    @FXML
    private Label clientPhone;

    @FXML
    private Label clientEmail;

    @FXML
    private Label clientAddress;

    @FXML
    private Label clientCondition;

    @FXML
    private Label clientReminder;

    @FXML
    private ImageView clientImage;

    @FXML
    private TableView<Schedule> clientScheduleTable;

    @FXML
    private TableColumn<Schedule, String> clientScheduleDate;

    @FXML
    private TableColumn<Schedule, String> clientScheduleDetails;

    @FXML
    private TableColumn<Schedule, String> clientSchedulePaymentStatus;

    private ObservableList<Schedule> scheduleList;

    /**
     * Creates a new ClientWindow.
     *
     * @param root Stage to use as the root of the ClientWindow.
     */
    public ClientWindow(Stage root, Person client) {
        super(FXML, root);
        // Load the image using the class loader
        Image newImage = new Image(getClass().getResourceAsStream("/images/cryingCat.png"));
        this.clientImage.setImage(newImage);
        setClient(client);

        // create key handler
        root.setOnShown(event -> {
            // track key press (keyEvent) when window is focused
            root.getScene().setOnKeyPressed(keyEvent -> {
                // check the code of the key pressed
                switch (keyEvent.getCode()) {
                // if key pressed is escape, hide the window
                case ESCAPE -> hide();
                // else do nothing by default
                default -> { }
                }
            });
        });
    }


    /**
     * Creates a new ClientWindow.
     */
    public ClientWindow(Person client) {
        this(new Stage(), client);
    }

    /**
     * Fills in client info into the local variables
     */
    private void setClient(Person client) {
        clientName.setText(client.getName().fullName);
        clientPhone.setText(client.getPhone().value);
        clientEmail.setText(client.getEmail().value);
        clientAddress.setText(client.getAddress().value);
        // might have to change this later on after condition is compulsory
        if (client.getTags().isEmpty()) {
            clientCondition.setText("-");
        } else {
            clientCondition.setText(client.getTags().toString());
        }
        if (client.getReminder().toString().isEmpty()) {
            clientReminder.setText("No Reminder Set");
        } else {
            clientReminder.setText(client.getReminder().toString());
        }
        // convert use Set<Schedule> into a List<Schedule>
        ArrayList<Schedule> scheduleArrayList = new ArrayList<>(client.getSchedules());
        // set scheduleList accordingly
        scheduleList = FXCollections.observableArrayList(scheduleArrayList);
        clientScheduleTable.setItems(scheduleList);
    }

    /**
     * Shows the client window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing information page about the client.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the client window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the client window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the client window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        clientScheduleDate.setCellValueFactory(new PropertyValueFactory<Schedule, String>("formattedDateTime"));
        clientScheduleDetails.setCellValueFactory(new PropertyValueFactory<Schedule, String>("notes"));
        clientSchedulePaymentStatus.setCellValueFactory(new PropertyValueFactory<Schedule, String>("isPaid"));
        scheduleList = FXCollections.observableArrayList();
        clientScheduleTable.setItems(scheduleList);
        clientScheduleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
