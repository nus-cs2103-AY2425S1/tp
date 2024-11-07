package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBERS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicateGroupException;
import seedu.address.model.person.exceptions.GroupNotFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Command to remove existing members from a group.
 */
public class RemoveFromGroupCommand extends Command {
    public static final String COMMAND_WORD = "removeFromGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes contacts from a existing group of specified name, the group must have "
            + "the specified persons (referenced by index of current list) inside.\n"
            + "Parameters: "
            + PREFIX_GROUP_NAME + "GROUP_NAME "
            + PREFIX_MEMBERS + " INDEX [MORE_INDICES...]\n"
            + "Example: " + COMMAND_WORD + " g/blood drive m/ 1 4 6";

    public static final String MESSAGE_REMOVE_FROM_GROUP_SUCCESS =
            "The following users were removed from the group %s:\n";
    public static final String MESSAGE_NO_PERSON_REMOVED = "No users were removed from the group %s.\n";
    public static final String MESSAGE_PERSON_NOT_IN_GROUP = "The following users were not in the group %s "
            + "and therefore could not be removed from the group, the rest of the users"
            + " have been removed accordingly: \n";
    public static final String MESSAGE_GROUP_NOT_EXISTS = "There is no group with name %s.";
    public static final String MESSAGE_PERSON_INDEX_NOT_EXISTS = "Index %s does not exist in the last shown list!";

    private final String groupName;

    private final List<Index> members;

    private List<Person> missingFromGroup = new ArrayList<>();
    private List<Person> removedMembers = new ArrayList<>();

    /**
     * Creates a new RemoveFromGroupCommand.
     * @param groupName The group name
     * @param members The persons to be removed (referenced by index)
     */
    public RemoveFromGroupCommand(String groupName, List<Index> members) {
        this.groupName = groupName;
        this.members = members;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getPersonList();

        Group existingGroup;
        Group newGroup;
        try {
            existingGroup = model.getGroup(groupName);
            newGroup = this.createGroupWithRemovedMembers(existingGroup, members, lastShownList);
            model.setGroup(existingGroup, newGroup);
        } catch (GroupNotFoundException gnfe) {
            throw new CommandException(String.format(MESSAGE_GROUP_NOT_EXISTS, groupName));
        } catch (IndexOutOfBoundsException | DuplicateGroupException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(formatSuccessCommandResultMessage(), true);
    }

    /**
     * Formats the message to output to the user after the execution of the command.
     * This is non-trivial as new entries and people not in the group are taken into account to
     * output the correct string format.
     *
     * @return a string which should go into the CommandResult.
     */
    private String formatSuccessCommandResultMessage() {
        final StringBuilder builder = new StringBuilder();
        if (this.removedMembers.isEmpty()) {
            builder.append(String.format(MESSAGE_NO_PERSON_REMOVED, groupName));
        } else {
            builder.append(String.format(MESSAGE_REMOVE_FROM_GROUP_SUCCESS, groupName));
        }
        this.removedMembers.forEach(person -> builder.append(person.getName()).append('\n'));
        if (!this.missingFromGroup.isEmpty()) {
            builder.append(String.format(MESSAGE_PERSON_NOT_IN_GROUP, groupName));
            this.missingFromGroup.forEach(person -> builder.append(person.getName()).append('\n'));
        }
        return builder.toString();
    }

    /**
     * To enforce immutability, this function creates a new group with the removed members.
     *
     * @param existingGroup The group object of the group before removal of members.
     * @param members The indices of the members to be removed, based on the lastShownList.
     * @param lastShownList The list of Person's objects last displayed to the user.
     * @return a new Group object with the added person objects.
     */
    private Group createGroupWithRemovedMembers(Group existingGroup,
                                            List<Index> members, List<Person> lastShownList) {
        Group newGroup = new Group(existingGroup);
        members.stream()
                .map(member -> {
                    int indexAsInt = member.getZeroBased();
                    assert indexAsInt >= 0; // Should be checked at parser level.
                    if (indexAsInt >= lastShownList.size()) {
                        throw new IndexOutOfBoundsException(String.format(
                                MESSAGE_PERSON_INDEX_NOT_EXISTS, indexAsInt + 1
                        ));
                    }
                    return lastShownList.get(indexAsInt);
                })
                .forEach(person -> {
                    try {
                        newGroup.remove(person);
                        removedMembers.add(person);
                    } catch (PersonNotFoundException pnfe) {
                        this.missingFromGroup.add(person);
                    }
                });
        return newGroup;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RemoveFromGroupCommand e)) {
            return false;
        }
        return groupName.equals(e.groupName) && members.equals(e.members);
    }
}
