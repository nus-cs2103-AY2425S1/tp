package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all contacts whose entries contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME KEYWORD(s) "
            + PREFIX_PHONE + "PHONE KEYWORD(s) "
            + PREFIX_ADDRESS + "ADDRESS KEYWORD(s) "
            + PREFIX_TAG + "TAG KEYWORD(s)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob charlie\n"
            + COMMAND_WORD + " " + PREFIX_ADDRESS + "blk 40_blk 50_blk 60\n"
            + COMMAND_WORD + " " + PREFIX_PHONE + "9243 9312";

    private final Predicate<Person> combinedPredicate;
    private final NameContainsKeywordsPredicate namePredicate;
    private final PhoneContainsKeywordsPredicate phonePredicate;
    private final AddressContainsKeywordsPredicate addressPredicate;
    private final TagContainsKeywordsPredicate tagPredicate;

    /**
     * Constructs a FindCommand object with optional predicates for filtering by name, phone, and address.
     * The command updates the filtered person list based on which predicates are present.
     * @param namePredicate  The predicate used to filter persons by their name,
     *                       or null if no name filtering is required.
     * @param phonePredicate  The predicate used to filter persons by their phone,
     *                        or null if no phone filtering is required.
     * @param addressPredicate  The predicate used to filter persons by their address,
     *                          or null if no address filtering is required.
     * @param tagPredicate  The predicate used to filter persons by their tag,
     *                          or null if no tag filtering is required.
     */
    public FindCommand(NameContainsKeywordsPredicate namePredicate,
                       PhoneContainsKeywordsPredicate phonePredicate,
                       AddressContainsKeywordsPredicate addressPredicate,
                       TagContainsKeywordsPredicate tagPredicate) {
        this.namePredicate = namePredicate;
        this.phonePredicate = phonePredicate;
        this.addressPredicate = addressPredicate;
        this.tagPredicate = tagPredicate;

        Predicate<Person> basePredicate = ((namePredicate == null)
                && (phonePredicate == null)
                && (addressPredicate == null)
                && (tagPredicate == null))
                ? person -> false : person -> true;

        if (namePredicate != null) {
            basePredicate = basePredicate.and(namePredicate);
        }
        if (phonePredicate != null) {
            basePredicate = basePredicate.and(phonePredicate);
        }
        if (addressPredicate != null) {
            basePredicate = basePredicate.and(addressPredicate);
        }
        if (tagPredicate != null) {
            basePredicate = basePredicate.and(tagPredicate);
        }

        this.combinedPredicate = basePredicate;
    }

    public FindCommand(NameContainsKeywordsPredicate namePredicate,
                       PhoneContainsKeywordsPredicate phonePredicate,
                       AddressContainsKeywordsPredicate addressPredicate) {
        this(namePredicate, phonePredicate, addressPredicate, null);
    }

    public FindCommand(NameContainsKeywordsPredicate namePredicate,
                       PhoneContainsKeywordsPredicate phonePredicate,
                       TagContainsKeywordsPredicate tagPredicate) {
        this(namePredicate, phonePredicate, null, tagPredicate);
    }

    public FindCommand(NameContainsKeywordsPredicate namePredicate,
                       AddressContainsKeywordsPredicate addressPredicate,
                       TagContainsKeywordsPredicate tagPredicate) {
        this(namePredicate, null, addressPredicate, tagPredicate);
    }

    public FindCommand(PhoneContainsKeywordsPredicate phonePredicate,
                       AddressContainsKeywordsPredicate addressPredicate,
                       TagContainsKeywordsPredicate tagPredicate) {
        this(null, phonePredicate, addressPredicate, tagPredicate);
    }

    public FindCommand(NameContainsKeywordsPredicate namePredicate,
                       PhoneContainsKeywordsPredicate phonePredicate) {
        this(namePredicate, phonePredicate, null, null);
    }

    public FindCommand(PhoneContainsKeywordsPredicate phonePredicate,
                       AddressContainsKeywordsPredicate addressPredicate) {
        this(null, phonePredicate, addressPredicate, null);
    }

    public FindCommand(NameContainsKeywordsPredicate namePredicate,
                       TagContainsKeywordsPredicate tagPredicate) {
        this(namePredicate, null, null, tagPredicate);
    }

    public FindCommand(NameContainsKeywordsPredicate namePredicate,
                       AddressContainsKeywordsPredicate addressPredicate) {
        this(namePredicate, null, addressPredicate, null);
    }

    public FindCommand(PhoneContainsKeywordsPredicate phonePredicate,
                       TagContainsKeywordsPredicate tagPredicate) {
        this(null, phonePredicate, null, tagPredicate);
    }

    public FindCommand(AddressContainsKeywordsPredicate addressPredicate,
                       TagContainsKeywordsPredicate tagPredicate) {
        this(null, null, addressPredicate, tagPredicate);
    }

    public FindCommand(NameContainsKeywordsPredicate namePredicate) {
        this(namePredicate, null, null, null);
    }

    public FindCommand(PhoneContainsKeywordsPredicate phonePredicate) {
        this(null, phonePredicate, null, null);
    }

    public FindCommand(AddressContainsKeywordsPredicate addressPredicate) {
        this(null, null, addressPredicate, null);
    }

    public FindCommand(TagContainsKeywordsPredicate tagPredicate) {
        this(null, null, null, tagPredicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(combinedPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;

        return (Objects.equals(this.namePredicate, otherFindCommand.namePredicate))
                && (Objects.equals(this.phonePredicate, otherFindCommand.phonePredicate))
                && (Objects.equals(this.addressPredicate, otherFindCommand.addressPredicate))
                && (Objects.equals(this.tagPredicate, otherFindCommand.tagPredicate));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate == null ? "null" : namePredicate.toString())
                .add("phonePredicate", phonePredicate == null ? "null" : phonePredicate.toString())
                .add("addressPredicate", addressPredicate == null ? "null" : addressPredicate.toString())
                .add("tagPredicate", tagPredicate == null ? "null" : tagPredicate.toString())
                .toString();
    }
}
