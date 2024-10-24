package seedu.address.logic.commands.consultation;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.DEFAULT_DELIMITER;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Consultation;

/**
 * Deletes a consultation by its index in TAHub.
 */
public class DeleteConsultCommand extends Command {

    public static final String COMMAND_WORD = "deleteconsult";
    public static final CommandType COMMAND_TYPE = CommandType.DELETECONSULT;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the consultation identified by the index number used in the displayed consultation list.\n"
            + "Parameters: INDEX (must be a positive integer) [;INDEX...]\n"
            + "Example: " + COMMAND_WORD + " 1" + DEFAULT_DELIMITER + "2";

    public static final String MESSAGE_DELETE_CONSULT_SUCCESS = "Deleted Consult(s):\n%1$s";

    private final Set<Index> targetIndices;

    public DeleteConsultCommand(Set<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    /**
     * Returns Command Type DELETECONSULT
     *
     * @return Command Type DELETECONSULT
     */
    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Consultation> consults = model.getFilteredConsultationList();

        boolean throwException = false;
        ArrayList<Index> outOfBounds = new ArrayList<>();

        for (Index item: targetIndices) {
            if (item.getZeroBased() >= consults.size()) {
                throwException = true;
                outOfBounds.add(item);
            }
        }

        if (throwException) {
            String formattedOutOfBoundIndices = outOfBounds.stream()
                    .map(index -> String.valueOf(index.getOneBased()))
                    .collect(Collectors.joining(", "));
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN, formattedOutOfBoundIndices));
        }

        List<Consultation> deletedPeople = targetIndices.stream()
                .map(targetIndex -> consults.get(targetIndex.getZeroBased()))
                .toList();


        deletedPeople.forEach(model::deleteConsult);

        String formattedDeletedPeople = deletedPeople.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));


        return new CommandResult(
                String.format(MESSAGE_DELETE_CONSULT_SUCCESS, formattedDeletedPeople),
                COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteConsultCommand)) {
            return false;
        }

        DeleteConsultCommand otherDeleteCommand = (DeleteConsultCommand) other;
        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }
}
