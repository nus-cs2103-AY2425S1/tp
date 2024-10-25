package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
/**
 * Filters and lists all persons in address book whose fields (name, role, email, phone, address)
 * match any of the specified keywords (case-insensitive) and displays them as a list with index numbers.
 * Parameters: n/NAME [r/ROLE] [e/EMAIL] [p/PHONE] [a/ADDRESS]...
 * Example: filter n/John r/vendor e/john@example.com
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_NO_CRITERIA = "At least one filter criteria must be provided";

    private final RoleContainsKeywordsPredicate predicate;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters persons by multiple criteria. "
            + "At least one field must be specified.\n"
            + "Parameters: [n/NAME] [r/ROLE] [e/EMAIL] [p/PHONE] [a/ADDRESS]...\n"
            + "Example: " + COMMAND_WORD + " n/John r/vendor";

    private final String name;
    private final String role;
    private final String email;
    private final String phone;
    private final String address;

    /**
     * Creates a FilterCommand to filter the list of persons.
     */
    public FilterCommand(String name, String role, String email, String phone, String address) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    /**
     * Executes the FilterCommand to filter the list of persons.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Predicate<Person>> predicates = new ArrayList<>();

        if (!name.isEmpty()) {
            predicates.add(new NameContainsKeywordsPredicate(List.of(name.split("\\s+"))));
        }
        if (!role.isEmpty()) {
            predicates.add(new RoleContainsKeywordsPredicate(List.of(role.split("\\s+"))));
        }
        if (!email.isEmpty()) {
            predicates.add(new EmailContainsKeywordsPredicate(List.of(email.split("\\s+"))));
        }
        if (!phone.isEmpty()) {
            predicates.add(new PhoneContainsKeywordsPredicate(List.of(phone.split("\\s+"))));
        }
        if (!address.isEmpty()) {
            predicates.add(new AddressContainsKeywordsPredicate(List.of(address)));
        }

        if (predicates.isEmpty()) {
            throw new CommandException(MESSAGE_NO_CRITERIA);
        }

        Predicate<Person> combinedPredicate = predicates.stream().reduce(x -> true, Predicate::and);
        model.updateFilteredPersonList(combinedPredicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return name.equals(otherFilterCommand.name)
                && role.equals(otherFilterCommand.role)
                && email.equals(otherFilterCommand.email)
                && phone.equals(otherFilterCommand.phone)
                && address.equals(otherFilterCommand.address);
    }

    @Override
    public String toString() {
        return "FilterCommand{"
                + "name='" + name + '\''
                + ", role='" + role + '\''
                + ", email='" + email + '\''
                + ", phone='" + phone + '\''
                + ", address='" + address + '\''
                + '}';
    }
}
