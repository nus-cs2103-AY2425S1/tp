package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String COMMAND_FUNCTION = COMMAND_WORD + ": Adds a person to the address book. ";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "\nParameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_JOB + "JOB "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_JOB + "Caterer "
            + PREFIX_TAG + "Jonus Ho & Izzat Syazani "
            + PREFIX_TAG + "Jackson & Jennie";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_EMPTY = "";

    public static final String MESSAGE_NEAR_DUPLICATE_PERSON = "A person with a similar name already exists: %1$s"
            + "\nIf you want to add this person, consider changing the input name";
    public static final String MESSAGE_WEDDING_DOESNT_EXIST = "Tag(s): '%1$s' does not exist as a Wedding yet."
            + "\n"
            + "Wedding needs to be created with Tag(s): '%2$s' using command '"
            + AddWeddingCommand.COMMAND_WORD
            + "' first.";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}.
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasExactPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        // Normalize the input name
        String toAddName = toAdd.getName().fullName;

        // Check for near duplication
        for (Person person : model.getAddressBook().getPersonList()) {
            String existingName = person.getName().fullName;
            if (toAddName.equals(existingName)) {
                throw new CommandException(String.format(MESSAGE_NEAR_DUPLICATE_PERSON, Messages.format(person)));
            }
        }

        String tagMsg = handleWeddingDoesNotExist(model, toAdd.getTags());

        model.addPerson(toAdd);
        model.setPersonInWedding(toAdd, null);

        String successMessage = !tagMsg.isEmpty() ? tagMsg + "\n"
                + String.format(MESSAGE_SUCCESS, Messages.format(toAdd))
                : String.format(MESSAGE_SUCCESS, Messages.format(toAdd));
        return new CommandResult(successMessage);
    }

    /**
     * Generates message based on whether tag can be added, which depends on whether wedding exists or not.
     * @param model current Model containing necessary wedding address book.
     * @param editedTags Set of tags that exist as a wedding as well.
     * @return String message stating whether tag exists as a wedding or not.
     */
    private String handleWeddingDoesNotExist(Model model, Set<Tag> editedTags) {
        List<Wedding> weddingList = model.getWeddingFromTags(editedTags);

        if (weddingList.isEmpty() && !editedTags.isEmpty()) {
            Set<Tag> tagsDontExist = new HashSet<>(editedTags);

            editedTags.removeAll(editedTags);

            return String.format(MESSAGE_WEDDING_DOESNT_EXIST,
                    Messages.tagSetToString(tagsDontExist), Messages.tagSetToString(tagsDontExist));
        } else if (weddingList.size() < editedTags.size()) {
            Set<Tag> weddingSet = weddingList.stream().map(Wedding::getWeddingName)
                    .map(WeddingName::toString).map(Tag::new).collect(Collectors.toSet());
            Set<Tag> tagsDontExist = new HashSet<>(editedTags);

            editedTags.retainAll(weddingSet);
            tagsDontExist.removeAll(weddingSet);

            return String.format(MESSAGE_WEDDING_DOESNT_EXIST, Messages.tagSetToString(tagsDontExist),
                    Messages.tagSetToString(tagsDontExist));
        }
        return MESSAGE_EMPTY;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
