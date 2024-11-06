package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteProfileCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAttendanceCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.UnmarkAttendanceCommand;
import seedu.address.logic.commands.ViewCommand;

/**
 * Controller for a help page.
 */
public class HelpWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    private static final String[] commandMessageUsages = {
        AddCommand.MESSAGE_USAGE,
        ClearCommand.MESSAGE_USAGE,
        DeleteCommand.MESSAGE_USAGE,
        DeleteProfileCommand.MESSAGE_USAGE,
        EditCommand.MESSAGE_USAGE,
        ExitCommand.MESSAGE_USAGE,
        FindCommand.MESSAGE_USAGE,
        HelpCommand.MESSAGE_USAGE,
        ListAttendanceCommand.MESSAGE_USAGE,
        ListCommand.MESSAGE_USAGE,
        MarkAttendanceCommand.MESSAGE_USAGE,
        SortCommand.MESSAGE_USAGE,
        SwitchCommand.MESSAGE_USAGE,
        UnmarkAttendanceCommand.MESSAGE_USAGE,
        ViewCommand.MESSAGE_USAGE
    };


    @FXML
    private ListView<String> helpListView;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        initializeHelpList();
        root.setMinWidth(800);
        root.setMinHeight(400);
        root.setMinWidth(800);
        root.setHeight(600);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException if this method is called improperly on the JavaFX Application Thread.
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Initializes the ListView with command usage messages.
     */
    private void initializeHelpList() {
        ObservableList<String> helpMessages = FXCollections.observableArrayList(commandMessageUsages);
        helpListView.setItems(helpMessages);
    }
}

