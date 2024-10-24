package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Counts the number of persons in the address book, with optional filters.
 * Supports filtering by name prefix or by tags. If no filter is provided,
 * it counts all persons in the list.
 */
public class CountCommand extends Command {

    public static final String COMMAND_WORD = "count";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Counts the number of persons in the list.\n"
            + "Optional filters:\n"
            + "  - name/<prefix>: Count persons with names starting with the given prefix.\n"
            + "  - tag/<tag>: Count persons associated with the specified tag.\n"
            + "Examples:\n"
            + "  count\n"
            + "  count name/Al\n"
            + "  count tag/friend";

    public static final String MESSAGE_COUNT_PERSONS = "There are %d person(s) in your list.";
    public static final String MESSAGE_NO_MATCHES = "No persons match the given criteria.";
    public static final String ERROR_INVALID_FILTER = "Invalid filter format. Use 'name/<prefix>' or 'tag/<tag>'.";

    private final Optional<String> namePrefix;
    private final Optional<String> tag;

    /**
     * Creates a CountCommand with optional filters for name prefix and tag.
     *
     * @param namePrefix Optional prefix to filter persons by name.
     * @param tag Optional tag to filter persons.
     */
    public CountCommand(Optional<String> namePrefix, Optional<String> tag) {
        this.namePrefix = namePrefix;
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null : "Model must  not be null";
        Predicate<Person> predicate = getFilterPredicate();

        List<Person> filteredList = model.getFilteredPersonList().stream()
                .filter(predicate)
                .toList();

        if (filteredList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_MATCHES);
        }

        int personCount = filteredList.size();
        return new CommandResult(String.format(MESSAGE_COUNT_PERSONS, personCount));
    }

    /**
     * Generates a predicate based on the provided filters.
     * If no filter is specified, it returns a predicate that matches all persons.
     *
     * @return Predicate to filter the persons.
     * @throws CommandException If both filters are provided or invalid filter syntax is detected.
     */
    private Predicate<Person> getFilterPredicate() throws CommandException {
        if (namePrefix.isPresent() && tag.isPresent()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        if (namePrefix.isPresent()) {
            String prefix = namePrefix.get().toLowerCase();
            return person -> person.getName().fullName.toLowerCase().startsWith(prefix);
        }

        if (tag.isPresent()) {
            String tagName = tag.get().toLowerCase();
            return person -> person.getTags().stream()
                    .anyMatch(tag -> tag.tagName.toLowerCase().equals(tagName));
        }

        // If no filters provided, match all persons.
        return person -> true;
    }

    @Override
    public boolean equals(Object other) {
        assert other != null : "Object must not be null";
        if (other == this) {
            return true;
        }
        if (!(other instanceof CountCommand)) {
            return false;
        }
        CountCommand otherCommand = (CountCommand) other;
        return namePrefix.equals(otherCommand.namePrefix) && tag.equals(otherCommand.tag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tag", tag)
                .toString();
    }
}


