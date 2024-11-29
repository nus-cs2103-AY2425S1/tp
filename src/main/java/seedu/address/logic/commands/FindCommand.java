package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.COMMAND_FORMAT_PREAMBLE;
import static seedu.address.logic.Messages.LINE_BREAK;
import static seedu.address.logic.Messages.MESSAGE_HELP_PROMPT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_DESCRIPTION_INPUT;
import static seedu.address.logic.Messages.MESSAGE_NOTHING_AFTER_COMMAND_AND_BEFORE_PREFIX_GENERAL;
import static seedu.address.logic.Messages.WHITESPACE;
import static seedu.address.logic.Messages.styleCommand;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Finds and lists all contacts in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String MESSAGE_NO_CONTACTS_FOUND = "No contacts with the specified field found.\n"
            + "If you are finding by name and can't find what you are looking for, perhaps you can find by "
            + "nickname.";
    public static final String MESSAGE_FUNCTION = COMMAND_WORD + ": Finds all contacts whose details contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.";
    public static final String MESSAGE_COMMAND_FORMAT =
            styleCommand(COMMAND_WORD + " [PREFIX] [new description]");
    public static final String MESSAGE_COMMAND_EXAMPLE = "Example 1: " + COMMAND_WORD + " "
            + PREFIX_NAME + " John Doe" + "\n"
            + "Example 2: " + COMMAND_WORD + " "
            + PREFIX_NAME + "alice bob charlie "
            + PREFIX_ROLE + "President " + PREFIX_ROLE + "Admin";

    public static final String MESSAGE_COMMAND_FORMAT_AND_HELP_PROMPT =
            COMMAND_FORMAT_PREAMBLE + WHITESPACE + FindCommand.MESSAGE_COMMAND_FORMAT + LINE_BREAK
                    + String.format(MESSAGE_HELP_PROMPT, HelpCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD);

    public static final String MESSAGE_MISSING_DESCRIPTION =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, String.format(MESSAGE_MISSING_DESCRIPTION_INPUT, "find")
                    + LINE_BREAK + MESSAGE_COMMAND_FORMAT_AND_HELP_PROMPT);

    public static final String MESSAGE_MISSING_PREFIX =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(Messages.MESSAGE_MISSING_PREFIX, "finding")
                            + WHITESPACE + MESSAGE_COMMAND_FORMAT_AND_HELP_PROMPT);

    public static final String MESSAGE_NOTHING_AFTER_COMMAND_AND_BEFORE_PREFIX =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(MESSAGE_NOTHING_AFTER_COMMAND_AND_BEFORE_PREFIX_GENERAL,
                            styleCommand(COMMAND_WORD))
                            + LINE_BREAK + MESSAGE_COMMAND_FORMAT_AND_HELP_PROMPT);

    private final Predicate<Contact> predicate;

    public FindCommand(Predicate<Contact> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(predicate);
        if (model.getFilteredContactList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_CONTACTS_FOUND);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW, model.getFilteredContactList().size()));
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
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
