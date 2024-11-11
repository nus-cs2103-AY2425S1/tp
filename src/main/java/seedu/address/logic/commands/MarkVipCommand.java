package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Marks a person as a VIP or revokes that title.
 */
public class MarkVipCommand extends Command {

    public static final String COMMAND_WORD = "vip";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks or unmarks the person identified by the index "
            + "number used in the displayed person list as a VIP.\n"
            + "Parameters: INDEX (must be a positive integer) IS_VIP (must be \"true\" or \"false\")\n"
            + "Example: " + COMMAND_WORD + " 1 true";

    public static final String MESSAGE_VIP_PERSON_SUCCESS = "Person marked as a VIP: %1$s";
    public static final String MESSAGE_UNVIP_PERSON_SUCCESS = "VIP status removed from person: %1$s";
    public static final String MESSAGE_VIP_PERSON_OBSOLETE = "Person already a VIP: %1$s";
    public static final String MESSAGE_UNVIP_PERSON_OBSOLETE = "Person not a VIP: %1$s";
    private final String messageSuccess;
    private final String messageObsolete;

    private final Index targetIndex;
    private final boolean newState;

    /**
     * Creates a MarkVipCommand to mark the specified {@code Person} as a VIP
     * @param targetIndex the index of the Person in the list
     * @param newState whether the Person should be labelled as a VIP when the operation is complete
     */
    public MarkVipCommand(Index targetIndex, boolean newState) {
        this.targetIndex = targetIndex;
        this.newState = newState;
        if (newState) {
            messageSuccess = MESSAGE_VIP_PERSON_SUCCESS;
            messageObsolete = MESSAGE_VIP_PERSON_OBSOLETE;
        } else {
            messageSuccess = MESSAGE_UNVIP_PERSON_SUCCESS;
            messageObsolete = MESSAGE_UNVIP_PERSON_OBSOLETE;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToMark = lastShownList.get(targetIndex.getZeroBased());
        if (personToMark.isVip() == newState) {
            return new CommandResult(String.format(messageObsolete, Messages.format(personToMark)));
        }
        Name name = personToMark.getName();
        Address address = personToMark.getAddress();
        Email email = personToMark.getEmail();
        Phone phone = personToMark.getPhone();
        Comment comment = personToMark.getComment();
        Set<Tag> tags = personToMark.getTags();
        Person updatedPerson = new Person(name, phone, email, address, comment, tags, newState);
        model.setPerson(personToMark, updatedPerson);
        return new CommandResult(String.format(messageSuccess, Messages.format(personToMark)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkVipCommand)) {
            return false;
        }

        MarkVipCommand otherMarkVipCommand = (MarkVipCommand) other;
        return targetIndex.equals(otherMarkVipCommand.targetIndex) && newState == otherMarkVipCommand.newState;
    }
}
