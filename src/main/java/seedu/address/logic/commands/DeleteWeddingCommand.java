package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

import java.util.Set;

import static java.util.Objects.requireNonNull;

public class DeleteWeddingCommand extends Command {
    public static final String COMMAND_WORD = "delete-wedding";
    public static final String COMMAND_FUNCTION = COMMAND_WORD
            + ": Deletes the wedding identified by the wedding name used in the address book.";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "Parameters: n/NAME & NAME\n"
            + "Example: " + COMMAND_WORD + " n/Jonus Ho & Izzat Syazani";

    public static final String MESSAGE_NO_MATCH_FOUND = "No wedding with the name '%1$s' found.";
    public static final String MESSAGE_MISSING_NAME = "Wedding name is required.";
    public static final String MESSAGE_CONFIRMATION_PROMPT = """
            Are you sure you want to delete the following wedding?
            Enter 'delete-y' to confirm, or 'delete-n' to cancel.
            Name: %1$s
            Venue: %2$s
            Date: %3$s
            """;

    private final Wedding toDelete;

    /**
     * Creates an AddWeddingCommand to add the specified {@code Wedding}
     * @param wedding
     */
    public DeleteWeddingCommand(Wedding wedding) {
        requireNonNull(wedding);
        this.toDelete = wedding;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasWedding(toDelete)) {
            throw new CommandException(MESSAGE_NO_MATCH_FOUND);
        }

        deleteTagsWithWedding();
        model.deleteWedding(toDelete);

        return new CommandResult(String.format(MESSAGE_CONFIRMATION_PROMPT, toDelete.getWeddingName(),
                toDelete.getVenue(), toDelete.getDate()));
    }

    private void deleteTagsWithWedding() throws CommandException {
        Set<Person> weddingParticipants = toDelete.getParticipants();
        for (Person participant : weddingParticipants) {
            participant.getTags().removeIf(tag -> tag.getTagName().equals(toDelete.getWeddingName().toString()));
        }
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
        return toDelete.equals(otherDeleteWeddingCommand.toDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toDelete", toDelete)
                .toString();
    }
}
