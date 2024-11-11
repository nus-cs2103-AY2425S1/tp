package seedu.ddd.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.ddd.logic.parser.CliFlags.FLAG_EVENT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_CLIENTS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_VENDORS;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_CLIENT_ID;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_EVENT_DATE;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_EVENT_DESCRIPTION;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_EVENT_NAME;
import static seedu.ddd.model.util.SampleDataUtil.SAMPLE_VENDOR_ID;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.CommandResult;
import seedu.ddd.logic.commands.exceptions.CommandException;
import seedu.ddd.model.AddressBook;
import seedu.ddd.model.Model;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Date;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;

/**
 * Adds an event to DDD.
 */
public class AddEventCommand extends AddCommand {
    public static final String COMMAND_DESCRIPTION = COMMAND_WORD + " " + FLAG_EVENT + " : adds an event";
    public static final String COMMAND_USAGE = "usage: " + COMMAND_WORD + " " + FLAG_EVENT + " "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESC + "DESCRIPTION "
            + PREFIX_DATE + "DATE "
            + PREFIX_CLIENTS + "CLIENT_ID "
            + PREFIX_VENDORS + "VENDOR_ID "
            + "[" + PREFIX_CLIENTS + "CLIENT_ID ...] "
            + "[" + PREFIX_VENDORS + "VENDOR_ID ...]";
    public static final String USAGE_EXAMPLE = "example: " + COMMAND_WORD + " " + FLAG_EVENT + " "
            + PREFIX_NAME + SAMPLE_EVENT_NAME + " "
            + PREFIX_DESC + SAMPLE_EVENT_DESCRIPTION + " "
            + PREFIX_DATE + SAMPLE_EVENT_DATE + " "
            + PREFIX_CLIENTS + SAMPLE_CLIENT_ID + " "
            + PREFIX_VENDORS + SAMPLE_VENDOR_ID;

    public static final String MESSAGE_USAGE = COMMAND_DESCRIPTION + "\n"
            + COMMAND_USAGE + "\n"
            + USAGE_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "New event created: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "An event of this name exists in DDD already. "
                                                         + "Choose another name!";
    public static final String MESSAGE_INVALID_CONTACT_IDS = "Client ID(s) %s and vendor ID(s) %s not found in DDD";
    public static final String MESSAGE_INVALID_CLIENT_IDS = "Client ID(s) %s not found in DDD";
    public static final String MESSAGE_INVALID_VENDOR_IDS = "Vendor ID(s) %s not found in DDD";

    private final Name name;
    private final Description description;
    private final Date date;
    private final Id eventId;
    private final Set<Id> clientContactIds;
    private final Set<Id> vendorContactIds;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}
     */
    public AddEventCommand(
        Name name,
        Description description,
        Date date,
        Set<Id> clientContactIds,
        Set<Id> vendorContactIds,
        Id eventId
    ) {
        requireAllNonNull(name, description, date, clientContactIds, vendorContactIds, eventId);

        this.name = name;
        this.description = description;
        this.date = date;
        this.eventId = eventId;
        this.clientContactIds = clientContactIds;
        this.vendorContactIds = vendorContactIds;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Set<Id> invalidClientContactIds = clientIdsNotInModel(model, clientContactIds);
        Set<Id> invalidVendorContactIds = vendorIdsNotInModel(model, vendorContactIds);

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
        assert !clientsToAdd.isEmpty();

        Event eventToAdd = new Event(name, description, date, clientsToAdd, vendorsToAdd, eventId);
        if (model.hasEvent(eventToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
        model.addEvent(eventToAdd);
        AddressBook.incrementNextEventId();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(eventToAdd)));
    }

    private Set<Id> clientIdsNotInModel(Model model, Set<Id> contactIds) {
        return contactIds.stream().filter(id -> !model.hasClientId(id)).collect(Collectors.toSet());
    }

    private Set<Id> vendorIdsNotInModel(Model model, Set<Id> contactIds) {
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
                .add("name", name)
                .add("description", description)
                .add("date", date)
                .add("clientIds", clientContactIds)
                .add("vendorIds", vendorContactIds)
                .add("id", eventId)
                .toString();
    }
}

