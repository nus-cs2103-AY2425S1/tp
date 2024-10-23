package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONCERT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.concert.Concert;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using its displayed index from the concert identified using its displayed index.
 */
public class DeleteConcertContactCommand extends Command {

    public static final String COMMAND_WORD = "deletecc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: " + PREFIX_PERSON + " PERSON_INDEX (must be a positive integer) "
            + PREFIX_CONCERT + "CONCERT_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PERSON + "1 " + PREFIX_CONCERT + "2";

    public static final String MESSAGE_DELETE_CONCERT_CONTACT_SUCCESS = "Deleted Person: %1$s from Concert: %2$s";

    private final Index indexP;
    private final Index indexC;

    /**
     * Creates a DeleteConcertContactCommand to delete the specified {@code Person} from the specifed {@code Concert}.
     */
    public DeleteConcertContactCommand(Index index1, Index index2) {
        this.indexP = index1;
        this.indexC = index2;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Concert> lastShownConcertList = model.getFilteredConcertList();

        if (indexP.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (indexC.getZeroBased() >= lastShownConcertList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
        }

        Concert concertToEdit = lastShownConcertList.get(indexC.getZeroBased());
        Person personToDelete = lastShownPersonList.get(indexP.getZeroBased());

        if (!model.hasConcertContact(personToDelete, concertToEdit)) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONCERT_CONTACT);
        }

        model.deleteConcertContact(personToDelete, concertToEdit);
        return new CommandResult(String.format(MESSAGE_DELETE_CONCERT_CONTACT_SUCCESS,
                Messages.format(personToDelete), Messages.format(concertToEdit)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteConcertContactCommand)) {
            return false;
        }

        DeleteConcertContactCommand otherDeleteConcertContactCommand = (DeleteConcertContactCommand) other;
        boolean sameConcertIndex = indexC.equals(otherDeleteConcertContactCommand.indexC);
        boolean samePersonIndex = indexP.equals(otherDeleteConcertContactCommand.indexP);
        return samePersonIndex && sameConcertIndex;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("indexP", indexP)
                .add("indexC", indexC)
                .toString();
    }
}
