package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.ui.ThemeController;

/**
 * Switches the GUI theme
 */
public class SwitchThemeCommand extends Command {
    public static final String COMMAND_WORD = "switch";
    public static final String MESSAGE_SUCCESS = "Changed Theme!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the theme of this interface.\n"
            + "Parameters: light or dark\n"
            + "Example: " + COMMAND_WORD + " dark";
    private final String theme;

    public SwitchThemeCommand(String theme) {
        this.theme = theme;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ThemeController.switchTheme(theme);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
