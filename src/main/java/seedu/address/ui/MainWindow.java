package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Label personLabel = new Label("Persons");
    private Label appointmentLabel = new Label("Appointments");

    private final Stage primaryStage;
    private final Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private AppointmentListPanel appointmentListPanel;
    private ResultDisplay resultDisplay;
    private final HelpWindow helpWindow;
    private HBox hbox = new HBox();
    private VBox personVBox = new VBox();
    private VBox appointmentVBox = new VBox();

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        appointmentListPanel = new AppointmentListPanel(logic.getFilteredAppointmentList());
        listPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        personVBox.getChildren().addAll(personLabel, personListPanel.getRoot());
        appointmentVBox.getChildren().addAll(appointmentLabel, appointmentListPanel.getRoot());


        hbox.setSpacing(16);
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setAlignment(Pos.CENTER);
        HBox.setHgrow(personVBox, Priority.ALWAYS);
        HBox.setHgrow(appointmentVBox, Priority.ALWAYS);

        listPanelPlaceholder.getChildren().add(hbox);
        hbox.setMinWidth(listPanelPlaceholder.getWidth());


        personLabel.setStyle("-fx-font-weight: bold;");
        appointmentLabel.setStyle("-fx-font-weight: bold;");
        personLabel.setStyle("-fx-text-fill: #3c3c3c; -fx-font-weight: bold;");
        appointmentLabel.setStyle("-fx-text-fill: #3c3c3c; -fx-font-weight: bold;");
        hbox.setStyle("-fx-background-color: #f2e1b3;");
        commandBoxPlaceholder.setStyle("-fx-min-height: 65px");

        personVBox.setSpacing(5);
        appointmentVBox.setSpacing(5);

        VBox.setVgrow(personListPanel.getRoot(), Priority.ALWAYS);
        VBox.setVgrow(appointmentListPanel.getRoot(), Priority.ALWAYS);

        personVBox.setMaxWidth(Double.MAX_VALUE);
        personVBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        personVBox.setMaxHeight(Double.MAX_VALUE);
        appointmentVBox.setMaxWidth(Double.MAX_VALUE);
        appointmentVBox.setPrefWidth(Region.USE_COMPUTED_SIZE);


        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        resultDisplay.getRoot().setStyle("-fx-background-color: #f2e1b3;");

        hbox.getChildren().add(personVBox);
        hbox.getChildren().add(appointmentVBox);
        hbox.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(hbox, Priority.ALWAYS);

        StatusBarFooter addressBookFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(addressBookFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    private void showPersonListPanel() {
        hbox.getChildren().add(personVBox);
        statusbarPlaceholder.getChildren().clear();
        StatusBarFooter addressBookFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(addressBookFooter.getRoot());
    }

    private void showAppointmentListPanel() {
        hbox.getChildren().add(appointmentVBox);
        statusbarPlaceholder.getChildren().clear();
        StatusBarFooter appointmentBookFooter = new StatusBarFooter(logic.getAppointmentBookFilePath());
        statusbarPlaceholder.getChildren().add(appointmentBookFooter.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
            (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandText.trim().contains(" person")) {
                showPersonListPanel();
            } else if (commandText.trim().contains(" appt")) {
                showAppointmentListPanel();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
