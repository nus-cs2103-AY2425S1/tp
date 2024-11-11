package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_DELETE_CONTACT_SUCCESS;
import static seedu.ddd.logic.Messages.MESSAGE_DELETE_EVENT_SUCCESS;
import static seedu.ddd.logic.Messages.MESSAGE_DEPENDENT_EVENT;
import static seedu.ddd.logic.Messages.MESSAGE_UNKNOWN_ITEM;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.ddd.commons.core.index.Index;
import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.exceptions.CommandException;
import seedu.ddd.model.Displayable;
import seedu.ddd.model.Model;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Event;

/**
 * Deletes a contact or event identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_DESCRIPTION = COMMAND_WORD + ": deletes a contact";
    public static final String COMMAND_USAGE = "usage: " + COMMAND_WORD + " INDEX";
    public static final String EXAMPLE_USAGE = "example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_USAGE = COMMAND_DESCRIPTION + "\n"
            + COMMAND_USAGE + "\n"
            + EXAMPLE_USAGE;

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Displayable> lastShownList = model.getDisplayedList();

        if (targetIndex.getOneBased() > lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_DISPLAYED_INDEX_TOO_LARGE);
        }

        Displayable itemToDelete = lastShownList.get(targetIndex.getZeroBased());

        if (itemToDelete instanceof Contact contactToDelete) {
            if (contactToDelete instanceof Client) {
                List<Id> dependentEventIds = contactToDelete.getEvents().stream()
                        .filter(event -> event.getClients().size() == 1)
                        .map(Event::getEventId)
                        .toList();
                if (!dependentEventIds.isEmpty()) {
                    String idString = dependentEventIds.stream()
                            .map(id -> id.id)
                            .map(String::valueOf)
                            .sorted()
                            .collect(Collectors.joining(", "));
                    throw new CommandException(String.format(MESSAGE_DEPENDENT_EVENT, idString));
                }
            }

            if (contactToDelete instanceof Vendor) {
                List<Id> dependentEventIds = contactToDelete.getEvents().stream()
                        .filter(event -> event.getVendors().size() == 1)
                        .map(Event::getEventId)
                        .toList();
                if (!dependentEventIds.isEmpty()) {
                    String idString = dependentEventIds.stream()
                            .map(id -> id.id)
                            .map(String::valueOf)
                            .sorted()
                            .collect(Collectors.joining(", "));
                    throw new CommandException(String.format(MESSAGE_DEPENDENT_EVENT, idString));
                }
            }

            model.deleteContact(contactToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_CONTACT_SUCCESS, Messages.format(contactToDelete)));
        } else if (itemToDelete instanceof Event eventToDelete) {
            model.deleteEvent(eventToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, Messages.format(eventToDelete)));
        } else {
            throw new CommandException(MESSAGE_UNKNOWN_ITEM);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
