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
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.person.Person;

/**
 * Links an existing person in the address book to an existing Concert
 */
public class AddConcertContactCommand extends Command {

    public static final String COMMAND_WORD = "addcc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": links the person identified "
            + "by the index number used in the displayed person list to the concert identified by the"
            + "index number used in the displayed concert list.\n"
            + "Parameters: " + PREFIX_PERSON + "PERSON_INDEX (must be a positive integer) "
             + PREFIX_CONCERT + "CONCERT_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PERSON + "1 " + PREFIX_CONCERT + "1";

    public static final String MESSAGE_LINK_PERSON_SUCCESS = "Linked Person: %1$s to Concert: %2$s";
    public static final String MESSAGE_DUPLICATE_CONCERTCONTACT =
            "This ConcertContact already exists in the address book";

    private final Index indexP;
    private final Index indexC;

    /**
     * @param index1 of the person in the person list to link
     * @param index2 of the concert in the concert list
     */
    public AddConcertContactCommand(Index index1, Index index2) {
        requireNonNull(index1);
        requireNonNull(index2);

        this.indexP = index1;
        this.indexC = index2;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Concert> lastShownConcertList = model.getFilteredConcertList();

        if (indexP.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (indexC.getZeroBased() >= lastShownConcertList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
        }

        Person personToLink = lastShownList.get(indexP.getZeroBased());
        Concert concert = lastShownConcertList.get(indexC.getZeroBased());

        ConcertContact linkedPerson = new ConcertContact(personToLink, concert);

        if (model.hasConcertContact(linkedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONCERTCONTACT);
        }

        model.addConcertContact(linkedPerson);
        return new CommandResult(String.format(MESSAGE_LINK_PERSON_SUCCESS, Messages.format(personToLink),
                Messages.format(concert)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddConcertContactCommand)) {
            return false;
        }

        AddConcertContactCommand otherAddConcertContactCommand = (AddConcertContactCommand) other;
        return indexP.equals(otherAddConcertContactCommand.indexP)
                && indexC.equals(otherAddConcertContactCommand.indexC);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("indexP", indexP)
                .add("indexC", indexC)
                .toString();
    }
}
