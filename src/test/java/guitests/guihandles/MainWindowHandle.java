package guitests.guihandles;

import javafx.stage.Stage;

/**
 * A handle for the main application window, providing access to its various components such as the person list panel,
 * result display, command box, status bar, and menu bar.
 */
public class MainWindowHandle extends StageHandle {

    private final PersonListPanelHandle personListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarHandle statusBar;
    private final MenuBarHandle mainMenu;

    /**
     * Constructs a {@code MainWindowHandle} for the specified stage.
     *
     * @param stage The stage representing the main window of the application.
     */
    public MainWindowHandle(Stage stage) {
        super(stage);

        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBar = new StatusBarHandle(getChildNode(StatusBarHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MenuBarHandle(getChildNode(MenuBarHandle.MENU_BAR_ID));
    }

    /**
     * Returns the {@code PersonListPanelHandle} to interact with the person list panel.
     *
     * @return The handle for the person list panel.
     */
    public PersonListPanelHandle getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Returns the {@code ResultDisplayHandle} to interact with the result display.
     *
     * @return The handle for the result display.
     */
    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    /**
     * Returns the {@code CommandBoxHandle} to interact with the command box.
     *
     * @return The handle for the command box.
     */
    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    /**
     * Returns the {@code StatusBarHandle} to interact with the status bar.
     *
     * @return The handle for the status bar.
     */
    public StatusBarHandle getStatusBar() {
        return statusBar;
    }

    /**
     * Returns the {@code MenuBarHandle} to interact with the main menu.
     *
     * @return The handle for the main menu.
     */
    public MenuBarHandle getMainMenu() {
        return mainMenu;
    }
}
