package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
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
 * Whitelists and existing person.
 */
public class WhitelistCommand extends Command {

    public static final String COMMAND_WORD = "whitelist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " index cs/CLIENT_STATUS"
            + ": removes person from the blacklist";

    public static final String MESSAGE_WHITELIST_PERSON_SUCCESS = "Whitelisted Person: %1$s";

    private final Index index;
    private final ClientStatus clientStatus;

    /**
     * @param index of the person in the filtered person list to whitelist
     */
    public WhitelistCommand(Index index, ClientStatus clientStatus) {
        requireAllNonNull(index, clientStatus);
        this.index = index;
        this.clientStatus = clientStatus;
    }

    /**
     * Creates a whitelisted copy of the original person.
     *
     * @param personToWhitelist the original person to be blacklisted
     * @return a blacklisted copy of the person
     */
    public Person createWhitelistedPerson(Person personToWhitelist) {
        return new Person(personToWhitelist.getName(),
                personToWhitelist.getPhone(),
                personToWhitelist.getEmail(),
                personToWhitelist.getAddress(),
                personToWhitelist.getTags(),
                personToWhitelist.getProjectStatus(),
                personToWhitelist.getPaymentStatus(),
                clientStatus,
                personToWhitelist.getDeadline());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToWhitelist = lastShownList.get(index.getZeroBased());
        Person whitelistedPerson = createWhitelistedPerson(personToWhitelist);

        model.setPerson(personToWhitelist, whitelistedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_WHITELIST_PERSON_SUCCESS, Messages.format(personToWhitelist)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WhitelistCommand)) {
            return false;
        }

        WhitelistCommand otherWhitelistCommand = (WhitelistCommand) other;
        return index.equals(otherWhitelistCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("change status", "whitelist")
                .toString();
    }
}

