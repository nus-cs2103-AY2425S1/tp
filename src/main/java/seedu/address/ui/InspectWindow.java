package seedu.address.ui;

import java.util.Set;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The Inspect Window. Provides information about the contact being inspected
 * and takes commands to manage deliveries under this contact.
 */
public class InspectWindow extends UiPart<Stage> {

    private static final String FXML = "InspectWindow.fxml";

    private static Person person;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private CommandBox commandBox;
    private double windowWidth;
    private double windowHeight;
    private double windowX;
    private double windowY;
    private boolean isMaximized;
    private boolean isFullScreen;

    @javafx.fxml.FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personInfoPlaceholder;

    @FXML
    private StackPane deliveryListPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private SplitPane mainSplitPane;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public InspectWindow(Stage primaryStage, Logic logic, Person person) {
        super(FXML, primaryStage);

        InspectWindow.person = person;

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
        VBox personInfoBox = new VBox();
        personInfoBox.setSpacing(20);

        HBox nameField = createNormalField("Name", InspectWindow.person.getName().toString());
        HBox phoneField = createNormalField("Phone", InspectWindow.person.getPhone().toString());
        HBox emailField = createNormalField("Email", InspectWindow.person.getEmail().toString());
        HBox addressField = createNormalField("Address", InspectWindow.person.getAddress().toString());
        HBox tagsField = createTagField("Tags", InspectWindow.person.getTags());
        HBox roleField = createNormalField("Role", InspectWindow.person.getRole().toString());

        personInfoBox.getChildren().addAll(nameField, phoneField, emailField, addressField, tagsField, roleField);

        DeliveryListPanel deliveryListPanel = new DeliveryListPanel(InspectWindow.person.getUnmodifiableDeliveryList());
        VBox deliveryListBox = new VBox(deliveryListPanel.getRoot());

        personInfoPlaceholder.getChildren().add(personInfoBox);
        deliveryListPlaceholder.getChildren().add(deliveryListBox);

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        this.commandBox = commandBox;
    }

    /**
     * Returns an HBox than has the format "fieldName: value" to be displayed on the UI
     * @param fieldName name of the field to be displayed
     * @param value value to be displayed
     * @return HBox to be displayed
     */
    private HBox createNormalField(String fieldName, String value) {
        HBox hBox = new HBox();
        Label fieldNameLabel = new Label(fieldName + ": ");
        Label valueLabel = new Label(value);

        fieldNameLabel.getStyleClass().add("personInfoTitle");
        fieldNameLabel.setMinWidth(80);
        valueLabel.getStyleClass().add("personInfo");

        hBox.getChildren().addAll(fieldNameLabel, valueLabel);
        return hBox;
    }

    /**
     * Returns an HBox than has the format "fieldName: tags" to be displayed on the UI
     * @param fieldName name of the field to be displayed
     * @param tags tags to be displayed
     * @return HBox to be displayed
     */
    private HBox createTagField(String fieldName, Set<Tag> tags) {
        HBox hBox = new HBox();
        Label fieldNameLabel = new Label(fieldName + ": ");
        FlowPane flowPane = new FlowPane();

        fieldNameLabel.getStyleClass().add("personInfoTitle");
        fieldNameLabel.setMinWidth(80);
        flowPane.setId("tags");
        flowPane.setStyle("-fx-padding: 10 0 10 0");

        for (Tag tag : tags) {
            Label tagLabel = new Label(tag.tagName);
            flowPane.getChildren().add(tagLabel);
        }

        hBox.getChildren().addAll(fieldNameLabel, flowPane);
        return hBox;
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
            helpWindow.show(true, commandBox);
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

    private void handleList(CommandResult commandResult) {
        logger.info("Changing UI back to main window...");

        storeWindowSize();
        MainWindow mainWindow = new MainWindow(primaryStage, logic);
        keepWindowSize(mainWindow.getPrimaryStage());
        mainWindow.show();
        mainWindow.fillInnerParts();
        mainWindow.getResultDisplay().setFeedbackToUser(commandResult.getFeedbackToUser());
        AddressBookParser.setInspect(false);

        helpWindow.close();
    }

    /**
     * Stores the current window size before changing windows.
     */
    private void storeWindowSize() {
        windowWidth = primaryStage.getWidth();
        windowHeight = primaryStage.getHeight();
        windowX = primaryStage.getX();
        windowY = primaryStage.getY();
        isMaximized = primaryStage.isMaximized();
        isFullScreen = primaryStage.isFullScreen();
        System.out.println(windowWidth);
        System.out.println(windowHeight);
        System.out.println(windowX);
        System.out.println(windowY);
        System.out.println(isMaximized);
        System.out.println(isFullScreen);
    }

    /**
     * Sets the window size of the next window to match the size of the current window
     * @param stage (window to be switched to)
     */
    private void keepWindowSize(Stage stage) {
        stage.setFullScreen(isFullScreen);
        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight);
        stage.setX(windowX);
        stage.setY(windowY);
        stage.setMaximized(isMaximized);
    }

    public ResultDisplay getResultDisplay() {
        return resultDisplay;
    }

    public CommandBox getCommandBox() {
        return commandBox;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            /* commandResult stores 'isInspect' boolean for whether we are in inspect mode.
            *  However, to get this commandResult in the first place, we need to parse the correct add command
            *  (i.e. window vs inspect).
            *  Thus, another boolean for 'isInspect' is tracked in AddressBookParser. These 2 booleans
            *  will always have the same value. */

            // This commandResult should store the result after executing add.
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            } else if (commandResult.isExit()) {
                handleExit();
            } else if (commandResult.isList()) {
                handleList(commandResult);
            } else {
                return commandResult;
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    public static Person getInspectedPerson() {
        return InspectWindow.person;
    }
}
