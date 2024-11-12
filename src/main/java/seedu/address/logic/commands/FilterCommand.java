package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.role.Role;

/**
 * Filters and lists all persons in address book whose fields (name, role, email, phone, address)
 * match any of the specified keywords (case-insensitive) and displays them as a list with index numbers.
 * Keyword matching is case-insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_NO_CRITERIA = "At least one filter criteria must be provided";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters persons by multiple criteria. "
            + "At least one field must be specified.\n"
            + "Parameters: [n/NAME] [r/ROLE] [e/EMAIL] [p/PHONE] [a/ADDRESS]...\n"
            + "Example: " + COMMAND_WORD + " n/John r/vendor";

    private final Name name;
    private final Optional<Role> role;
    private final Email email;
    private final Phone phone;
    private final Address address;

    /**
     * Creates a {@code FilterCommand} object to filter persons by the specified {@code name},
     * {@code role}, {@code email}, {@code phone} and {@code address}.
     *
     * @param name the {@code Name} field to check against.
     * @param role the {@code Role} field to check against.
     * @param email the {@code Email} field to check against.
     * @param phone the {@code Phone} field to check against.
     * @param address the {@code Address} field to check against.
     */
    public FilterCommand(Name name, Optional<Role> role, Email email, Phone phone, Address address) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Predicate<Person>> predicates = new ArrayList<>();

        if (name != null) {
            String[] wordNames = name.toString().toLowerCase().split("\\s+");
            predicates.add(new NameMatchesKeywordPredicate(Arrays.asList(wordNames)));
        }

        if (role != null) {
            predicates.add(new RoleContainsKeywordsPredicate(Arrays.asList(role.get().toString().toLowerCase())));
        }

        if (email != null) {
            predicates.add(new EmailContainsKeywordsPredicate(Arrays.asList(email.toString().toLowerCase())));
        }

        if (phone != null) {
            predicates.add(new PhoneContainsKeywordsPredicate(Arrays.asList(phone.toString())));
        }

        if (address != null) {
            predicates.add(new AddressContainsKeywordsPredicate(Arrays.asList(address.toString().toLowerCase())));
        }

        if (predicates.isEmpty()) {
            throw new CommandException(MESSAGE_NO_CRITERIA);
        }

        Predicate<Person> combinedPredicate = predicates.stream()
                .reduce(person -> false, Predicate::or);

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

        boolean equalName = (name == null && otherFilterCommand.name == null)
                || name != null && name.equals(otherFilterCommand.name);

        boolean equalRole = (role == null && otherFilterCommand.role == null)
                || role != null && role.equals(otherFilterCommand.role);

        boolean equalEmail = (email == null && otherFilterCommand.email == null)
                || email != null && email.equals(otherFilterCommand.email);

        boolean equalPhone = (phone == null && otherFilterCommand.phone == null)
                || phone != null && phone.equals(otherFilterCommand.phone);

        boolean equalAddress = (address == null && otherFilterCommand.address == null)
                || address != null && address.equals(otherFilterCommand.address);

        return equalName && equalRole && equalEmail && equalPhone && equalAddress;
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
