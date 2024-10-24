package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ClientStatus;
import seedu.address.model.person.Person;

/**
 * Blacklists an existing person.
 */
public class BlacklistCommand extends Command {

    public static final String COMMAND_WORD = "blacklist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " index : adds person to the blacklist";

    public static final String MESSAGE_BLACKLIST_PERSON_SUCCESS = "Blacklisted Person: %1$s";

    private final Index index;

    /**
     * @param index of the person in the filtered person list to blacklist
     */
    public BlacklistCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    /**
     * Creates a blacklisted copy of the original person.
     *
     * @param personToBlacklist the original person to be blacklisted
     * @return a blacklisted copy of the person
     */
    public Person createBlacklistedPerson(Person personToBlacklist) {
        return new Person(personToBlacklist.getName(),
                personToBlacklist.getPhone(),
                personToBlacklist.getEmail(),
                personToBlacklist.getAddress(),
                personToBlacklist.getTags(),
                personToBlacklist.getProjectStatus(),
                personToBlacklist.getPaymentStatus(),
                new ClientStatus("blacklisted"),
                personToBlacklist.getDeadline());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToBlacklisted = lastShownList.get(index.getZeroBased());
        Person blacklistedPerson = createBlacklistedPerson(personToBlacklisted);

        model.setPerson(personToBlacklist, blacklistedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_BLACKLIST_PERSON_SUCCESS, Messages.format(personToBlacklist)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BlacklistCommand)) {
            return false;
        }

        BlacklistCommand otherBlacklistCommand = (BlacklistCommand) other;
        return index.equals(otherBlacklistCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("change status", "blacklist")
                .toString();
    }
}

