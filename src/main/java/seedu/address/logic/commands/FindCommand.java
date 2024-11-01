package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.EmailContainsKeywordsPredicate;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.model.client.PhoneContainsKeywordsPredicate;
import seedu.address.model.client.RentalInformationContainsKeywordsPredicate;
import seedu.address.model.tag.TagsContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all clients whose details contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: (at least one must be provided) "
            + "[" + PREFIX_KEYWORD + "KEYWORD]... "
            + "[" + PREFIX_NAME + "NAME]... "
            + "[" + PREFIX_PHONE + "PHONE]... "
            + "[" + PREFIX_EMAIL + "EMAIL]... "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "john "
            + PREFIX_EMAIL + "@example "
            + PREFIX_EMAIL + "@nus "
            + PREFIX_TAG + "friends";

    private final NameContainsKeywordsPredicate namePredicate;
    private final PhoneContainsKeywordsPredicate phonePredicate;
    private final EmailContainsKeywordsPredicate emailPredicate;
    private final TagsContainsKeywordsPredicate tagsPredicate;

    private final RentalInformationContainsKeywordsPredicate rentalInfoPredicate;

    /**
     * Constructs a {@code FindCommand} with the given name, phone, email, tags and rental information predicates.
     * The command will find all clients whose name, phone, email, tags and rental information matches the predicates.
     */
    public FindCommand(NameContainsKeywordsPredicate namePredicate,
                       PhoneContainsKeywordsPredicate phonePredicate,
                       EmailContainsKeywordsPredicate emailPredicate,
                       TagsContainsKeywordsPredicate tagsPredicate,
                       RentalInformationContainsKeywordsPredicate rentalInfoPredicate) {
        this.namePredicate = namePredicate;
        this.phonePredicate = phonePredicate;
        this.emailPredicate = emailPredicate;
        this.tagsPredicate = tagsPredicate;
        this.rentalInfoPredicate = rentalInfoPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Client> combinedPredicate = namePredicate.or(phonePredicate).or(emailPredicate).or(tagsPredicate)
                .or(rentalInfoPredicate);
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
        return namePredicate.equals(otherFindCommand.namePredicate)
                && phonePredicate.equals(otherFindCommand.phonePredicate)
                && emailPredicate.equals(otherFindCommand.emailPredicate)
                && rentalInfoPredicate.equals(otherFindCommand.rentalInfoPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("phonePredicate", phonePredicate)
                .add("emailPredicate", emailPredicate)
                .add("tagsPredicate", tagsPredicate)
                .add("rentalInfoPredicate", rentalInfoPredicate)
                .toString();
    }
}
