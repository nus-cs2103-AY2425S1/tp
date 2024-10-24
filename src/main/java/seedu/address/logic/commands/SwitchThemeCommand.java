package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SwitchThemeCommand)) {
            return false;
        }

        SwitchThemeCommand otherSwitchThemeCommand = (SwitchThemeCommand) other;
        return theme.equals(otherSwitchThemeCommand.theme);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("theme", theme)
            .toString();
    }
}
