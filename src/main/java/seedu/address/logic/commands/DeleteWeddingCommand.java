package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;

import java.util.List;

import seedu.address.logic.StaticContext;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;

/**
 * Deletes a wedding identified by its name from the wedding book.
 * The wedding must be input in the exact way it was added to the wedding book.
 */
public class DeleteWeddingCommand extends Command {
    public static final String COMMAND_WORD = "del-wed";
    public static final String COMMAND_WORD_SHORT = "dw";
    public static final String COMMAND_FUNCTION = COMMAND_WORD + " OR " + COMMAND_WORD_SHORT
            + ": Deletes the wedding identified by the wedding name used in the address book.";
    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "\nParameters: w/NAME & NAME\n"
            + "Example: " + COMMAND_WORD_SHORT + " w/Jonus Ho & Izzat Syazani";
    public static final String MESSAGE_NO_MATCH_FOUND = """
            No wedding with the name '%1$s' found.
            Please check the wedding name and try again.
            The wedding must be input in the exact way it was added to the wedding book.
            """;
    public static final String MESSAGE_MISSING_NAME = "Wedding name is required.";
    public static final String MESSAGE_CONFIRMATION_PROMPT = """
            Are you sure you want to delete the following wedding?
            Enter 'y' to confirm, or 'n' to cancel.
            Name: %1$s
            Venue: %2$s
            Date: %3$s
            """;

    private final String weddingName;

    /**
     * Creates a DeleteWeddingCommand to delete the Wedding with the specified {@code String}.
     */
    public DeleteWeddingCommand(String weddingName) {
        this.weddingName = weddingName.trim();
    }

    /**
     * Executes the DeleteWeddingCommand.
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that describes the result of executing the command.
     * @throws CommandException if the wedding name is empty or if the wedding name does not match any wedding.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);


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
