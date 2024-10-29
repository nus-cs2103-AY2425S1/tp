package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.commons.util.ToStringBuilder;
import tuteez.logic.Messages;
import tuteez.model.Model;
import tuteez.model.person.Person;
import tuteez.model.person.predicates.AddressContainsKeywordsPredicate;
import tuteez.model.person.predicates.EmailContainsKeywordsPredicate;
import tuteez.model.person.predicates.LessonContainsKeywordsPredicate;
import tuteez.model.person.predicates.NameContainsKeywordsPredicate;
import tuteez.model.person.predicates.PhoneContainsKeywordsPredicate;
import tuteez.model.person.predicates.TagContainsKeywordsPredicate;
import tuteez.model.person.predicates.TelegramUsernameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private static final Logger logger = LogsCenter.getLogger(FindCommand.class);

    private final NameContainsKeywordsPredicate namePredicate;
    private final PhoneContainsKeywordsPredicate phonePredicate;
    private final EmailContainsKeywordsPredicate emailPredicate;
    private final AddressContainsKeywordsPredicate addressPredicate;
    private final TelegramUsernameContainsKeywordsPredicate telegramUsernamePredicate;
    private final TagContainsKeywordsPredicate tagPredicate;
    private final LessonContainsKeywordsPredicate lessonPredicate;

    /**
     * Creates a FindCommand to find persons with the specified {@code NameContainsKeywordsPredicate}.
     */
    public FindCommand(
            NameContainsKeywordsPredicate namePredicate,
            PhoneContainsKeywordsPredicate phonePredicate,
            EmailContainsKeywordsPredicate emailPredicate,
            AddressContainsKeywordsPredicate addressPredicate,
            TelegramUsernameContainsKeywordsPredicate telegramUsernamePredicate,
            TagContainsKeywordsPredicate tagPredicate,
            LessonContainsKeywordsPredicate lessonPredicate
    ) {
        this.namePredicate = namePredicate;
        this.phonePredicate = phonePredicate;
        this.emailPredicate = emailPredicate;
        this.addressPredicate = addressPredicate;
        this.telegramUsernamePredicate = telegramUsernamePredicate;
        this.tagPredicate = tagPredicate;
        this.lessonPredicate = lessonPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Person> combinedPredicate = namePredicate.or(phonePredicate).or(emailPredicate).or(addressPredicate)
                .or(telegramUsernamePredicate).or(tagPredicate).or(lessonPredicate);
        model.updateFilteredPersonList(combinedPredicate);
        int size = model.getFilteredPersonList().size();

        String logMessage = String.format("Find command execution completed. Found %d matching %s",
                size, size == 1 ? "result" : "results");
        logger.info(logMessage);
        if (size == 0) {
            logger.info("No matching results found.");
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, size)
        );
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
                && addressPredicate.equals(otherFindCommand.addressPredicate)
                && tagPredicate.equals(otherFindCommand.tagPredicate)
                && lessonPredicate.equals(otherFindCommand.lessonPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Names: ", namePredicate)
                .add("Phone numbers: ", phonePredicate)
                .add("Emails: ", emailPredicate)
                .add("Addresses: ", addressPredicate)
                .add("Telegram username: ", telegramUsernamePredicate)
                .add("Tags: ", tagPredicate)
                .add("Lessons: ", lessonPredicate)
                .toString();
    }
}
