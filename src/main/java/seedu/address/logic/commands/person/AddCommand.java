package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. \n"
            + "Parameters (optional parameters in square brackets): "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_WEDDING + "WEDDING]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney "
            + PREFIX_WEDDING + "Wedding March 20th 2027 "
            + PREFIX_WEDDING + "Amy's Wedding";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_PERSON);
        }

        // Creates weddings for all new ones and assigns person to all wedding guest lists
        for (Wedding wedding : toAdd.getWeddings()) {
            if (!model.hasWedding(wedding)) {
                model.addWedding(wedding);
            }
            wedding.addToGuestList(toAdd);
        }

        // Gets model's version of all weddings that need to be added
        Set<Wedding> modelWeddings = toAdd.getWeddings().stream().map(model::getWedding)
                .collect(Collectors.toCollection(HashSet::new));
        // Replaces all versions of weddings in person with model version
        toAdd.setWeddings(modelWeddings);

        // Creates tags for all new ones and increases tag count for all
        for (Tag tag : toAdd.getTags()) {
            if (!model.hasTag(tag)) {
                model.addTag(tag);
            }
            tag.increaseTaggedCount();
        }

        // Gets model's version of all tags that need to be added
        Set<Tag> modelTags = toAdd.getTags().stream().map(model::getTag).collect(Collectors.toCollection(HashSet::new));
        // Replaces all versions of tags in person with model version
        toAdd.setTags(modelTags);

        model.addPerson(toAdd);
        return new CommandResult(String.format(Messages.MESSAGE_ADD_PERSON_SUCCESS, Messages.format(toAdd)));
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
