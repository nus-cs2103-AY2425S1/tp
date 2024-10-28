package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.StaticContext;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;

/**
 * Deletes a wedding identified by its name from the wedding book.
 */
public class DeleteWeddingCommand extends Command {
    public static final String COMMAND_WORD = "delete-wedding";
    public static final String COMMAND_FUNCTION = COMMAND_WORD
            + ": Deletes the wedding identified by the wedding name used in the address book.";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "Parameters: w/NAME & NAME\n"
            + "Example: " + COMMAND_WORD + " w/Jonus Ho & Izzat Syazani";

    public static final String MESSAGE_NO_MATCH_FOUND = "No wedding with the name '%1$s' found.";
    public static final String MESSAGE_MISSING_NAME = "Wedding name is required.";
    public static final String MESSAGE_CONFIRMATION_PROMPT = """
            Are you sure you want to delete the following wedding?
            Enter 'delete-y' to confirm, or 'delete-n' to cancel.
            Name: %1$s
            Venue: %2$s
            Date: %3$s
            """;

    private final String weddingName;

    /**
     * Creates a DeleteWeddingCommand to delete the Wedding with the specified {@code String}
     * @param weddingName
     */
    public DeleteWeddingCommand(String weddingName) {
        this.weddingName = weddingName.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (weddingName.isEmpty()) {
            throw new CommandException(MESSAGE_MISSING_NAME);
        }

        List<Wedding> matchingWeddings = model.getFilteredWeddingList().stream()
                .filter(wedding -> wedding.getWeddingName().toString().equalsIgnoreCase(weddingName))
                .toList();

        if (matchingWeddings.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_MATCH_FOUND, weddingName));
        }

        Wedding weddingToDelete = matchingWeddings.get(0);

        String confirmationMessage = String.format(MESSAGE_CONFIRMATION_PROMPT,
                weddingToDelete.getWeddingName(),
                weddingToDelete.getVenue(),
                weddingToDelete.getDate());

        // Store the weddingToDelete in a static context
        StaticContext.setWeddingToDelete(weddingToDelete);

        return new CommandResult(confirmationMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteWeddingCommand)) {
            return false;
        }

        DeleteWeddingCommand otherDeleteWeddingCommand = (DeleteWeddingCommand) other;
        return weddingName.equals(otherDeleteWeddingCommand.weddingName);
    }
}
