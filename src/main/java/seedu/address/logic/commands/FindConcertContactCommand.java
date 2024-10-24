package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONCERT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONCERT_CONTACTS;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.person.Person;

/**
 * Finds and lists all concertContacts in address book
 * which is associated to the person keyword
 * and is associated to the concert keyword.
 * Keyword matching is case-insensitive.
 */
public class FindConcertContactCommand extends Command {

    public static final String COMMAND_WORD = "findcc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all concertContacts which are associated to "
            + "the person at the specified person index and the concert at the specified concert index "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_PERSON + "PERSON_INDEX] "
            + "[" + PREFIX_CONCERT + "CONCERT_INDEX]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON + "1 "
            + PREFIX_CONCERT + "1";

    private final Index indexP;
    private final Index indexC;

    /**
     * Creates an FindConcertContactCommand to find concertContacts that are associated to
     * the person at the specified {@code personIndex} and
     * the concert at the specified {@code concertIndex}.
     */
    public FindConcertContactCommand(Index personIndex, Index concertIndex) {
        assert personIndex != null || concertIndex != null
                : "Both person and concert index cannot be null at the same time";
        this.indexP = personIndex;
        this.indexC = concertIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person person = getPersonFromIndex(model);
        Concert concert = getConcertFromIndex(model);

        Predicate<ConcertContact> predicate = createPredicate(person, concert);
        model.updateFilteredConcertContactList(predicate);

        return new CommandResult(String.format(Messages.MESSAGE_CONCERT_CONTACTS_LISTED_OVERVIEW,
                model.getFilteredConcertContactList().size()), false, false, true);
    }

    private Person getPersonFromIndex(Model model) throws CommandException {
        assert model != null : "Model cannot be null";
        if (indexP == null) {
            return null;
        }

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        if (indexP.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownPersonList.get(indexP.getZeroBased());
    }

    private Concert getConcertFromIndex(Model model) throws CommandException {
        assert model != null : "Model cannot be null";
        if (indexC == null) {
            return null;
        }

        List<Concert> lastShownConcertList = model.getFilteredConcertList();
        if (indexC.getZeroBased() >= lastShownConcertList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
        }

        return lastShownConcertList.get(indexC.getZeroBased());
    }

    private Predicate<ConcertContact> createPredicate(Person person, Concert concert) {
        assert person != null || concert != null : "Both person and concert cannot be null at the same time";
        Predicate<ConcertContact> associatePersonPredicate = PREDICATE_SHOW_ALL_CONCERT_CONTACTS;
        Predicate<ConcertContact> associateConcertPredicate = PREDICATE_SHOW_ALL_CONCERT_CONTACTS;

        if (person != null) {
            associatePersonPredicate = cc -> cc.isAssociated(person);
        }
        if (concert != null) {
            associateConcertPredicate = cc -> cc.isAssociated(concert);
        }

        return associatePersonPredicate.and(associateConcertPredicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindConcertContactCommand)) {
            return false;
        }

        FindConcertContactCommand otherFindCommand = (FindConcertContactCommand) other;
        return indexP.equals(otherFindCommand.indexP)
                && indexC.equals(otherFindCommand.indexC);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("indexP", indexP)
                .add("indexC", indexC)
                .toString();
    }
}

