package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.scheme.Scheme;
import seedu.address.model.scheme.SchemeRetrieval;

/**
 * Displays the scheme available to family identified using the displayed index from the address book.
 */
public class SchemeCommand extends Command {

    public static final String COMMAND_WORD = "scheme";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the scheme available to the family identified by the "
            + "index number used in the displayed family list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    /**
     * Takes in the index to be used for displaying the scheme.
     *
     * @param targetIndex Index of the family.
     */
    public SchemeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the Scheme command where at a valid index.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Message to user that query was successful.
     * @throws CommandException If any of the index given falls out of range.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person targetFamily = lastShownList.get(targetIndex.getZeroBased());
        SchemeRetrieval schemeRetrieval = new SchemeRetrieval(targetFamily);
        ArrayList<Scheme> schemes = schemeRetrieval.getSchemes();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < schemes.size(); i++) {
            result.append((i + 1 + ". ")).append(schemes.get(i).getSchemeName()).append("\n");
        }
        if (result.toString().equals("")) {
            result = new StringBuilder("No schemes available for this family.");
        }
        return new CommandResult(result.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SchemeCommand)) {
            return false;
        }

        SchemeCommand otherSchemeCommand = (SchemeCommand) other;
        return targetIndex.equals(otherSchemeCommand.targetIndex);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
