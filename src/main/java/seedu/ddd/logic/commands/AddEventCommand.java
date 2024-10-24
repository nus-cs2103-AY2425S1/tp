package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_CLIENTS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_VENDORS;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.exceptions.CommandException;
import seedu.ddd.model.AddressBook;
import seedu.ddd.model.Model;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.EventId;

/**
 * Adds an event to DDD.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "event";

    public static final String COMMAND_DESCRIPTION = COMMAND_WORD + ": adds an event";
    public static final String COMMAND_USAGE = "usage: " + COMMAND_WORD + " "
            + PREFIX_DESC + "DESCRIPTION "
            + PREFIX_CLIENTS + "CLIENT ... "
            + PREFIX_VENDORS + "VENDOR ...";
    public static final String USAGE_EXAMPLE = "example: " + COMMAND_WORD + " "
            + PREFIX_DESC + "Wedding "
            + PREFIX_CLIENTS + "0 "
            + PREFIX_VENDORS + "1 "
            + PREFIX_VENDORS + "2 ";
    public static final String MESSAGE_USAGE = COMMAND_DESCRIPTION + "\n"
            + COMMAND_USAGE + "\n"
            + USAGE_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "New event created: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in DDD";
    public static final String MESSAGE_INVALID_CONTACT_IDS = "Client ID(s) %s and vendor ID(s) %s not found in DDD";
    public static final String MESSAGE_INVALID_CLIENT_IDS = "Client ID(s) %s not found in DDD";
    public static final String MESSAGE_INVALID_VENDOR_IDS = "Vendor ID(s) %s not found in DDD";

    private final Set<ContactId> clientContactIds;
    private final Set<ContactId> vendorContactIds;
    private final Description description;
    private final EventId eventId;


    /**
     * Creates an AddEventCommand to add the specified {@code Event}
     */
    public AddEventCommand(Set<ContactId> clientContactIds, Set<ContactId> vendorContactIds, Description description,
            EventId eventId) {
        requireNonNull(clientContactIds);
        requireNonNull(vendorContactIds);
        requireNonNull(description);
        requireNonNull(eventId);

        this.clientContactIds = clientContactIds;
        this.vendorContactIds = vendorContactIds;
        this.description = description;
        this.eventId = eventId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Set<ContactId> invalidClientContactIds = clientIdsNotInModel(model, this.clientContactIds);
        Set<ContactId> invalidVendorContactIds = vendorIdsNotInModel(model, this.vendorContactIds);

        if (!invalidClientContactIds.isEmpty() && !invalidVendorContactIds.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_INVALID_CONTACT_IDS,
                    invalidClientContactIds, invalidVendorContactIds));
        } else if (!invalidClientContactIds.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_INVALID_CLIENT_IDS, invalidClientContactIds));
        } else if (!invalidVendorContactIds.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_INVALID_VENDOR_IDS, invalidVendorContactIds));
        }

        List<Client> clientsToAdd = clientContactIds.stream().map(id -> (Client) model.getContact(id)).toList();
        List<Vendor> vendorsToAdd = vendorContactIds.stream().map(id -> (Vendor) model.getContact(id)).toList();

        Event eventToAdd = new Event(clientsToAdd, vendorsToAdd,
                this.description, this.eventId);

        if (model.hasEvent(eventToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(eventToAdd);

        AddressBook.incrementNextEventId();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(eventToAdd)));
    }

    private Set<ContactId> clientIdsNotInModel(Model model, Set<ContactId> contactIds) {
        return contactIds.stream().filter(id -> !model.hasClientId(id)).collect(Collectors.toSet());
    }

    private Set<ContactId> vendorIdsNotInModel(Model model, Set<ContactId> contactIds) {
        return contactIds.stream().filter(id -> !model.hasVendorId(id)).collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddEventCommand)) {
            return false;
        }

        AddEventCommand otherAddEventCommand = (AddEventCommand) other;
        return Objects.equals(clientContactIds, otherAddEventCommand.clientContactIds)
                && Objects.equals(vendorContactIds, otherAddEventCommand.vendorContactIds)
                && Objects.equals(description, otherAddEventCommand.description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientIds", clientContactIds)
                .add("vendorIds", vendorContactIds)
                .add("description", description)
                .toString();
    }
}

