package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.scheme.Scheme;
import seedu.address.model.scheme.SchemeRetrieval;

/**
 * Views the schemes of a person is subscribed to in the address book.
 */
public class ViewPersonSchemeCommand extends Command {
    public static final String COMMAND_WORD = "viewscheme";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the schemes the person is subscribed to identified by the "
            + "index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public ViewPersonSchemeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person targetPerson = lastShownList.get(targetIndex.getZeroBased());
        SchemeRetrieval schemeRetrieval = new SchemeRetrieval(targetPerson);
        ArrayList<Scheme> schemes = schemeRetrieval.getSubscribedSchemes();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < schemes.size(); i++) {
            result.append((i + 1 + ". ")).append(schemes.get(i).getSchemeName()).append("\n");
        }
        if (result.toString().equals("")) {
            result = new StringBuilder("This person is not subscribed to any schemes.");
        }
        result.insert(0, "Schemes " + targetPerson.getName() + " is subscribed to:\n");
        return new CommandResult(result.toString());
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewPersonSchemeCommand)) {
            return false;
        }

        ViewPersonSchemeCommand otherSchemeCommand = (ViewPersonSchemeCommand) other;
        return targetIndex.equals(otherSchemeCommand.targetIndex);
    }

    @Override
    public String undo(Model model, CommandHistory pastCommands) {
        return null;
    }
}
