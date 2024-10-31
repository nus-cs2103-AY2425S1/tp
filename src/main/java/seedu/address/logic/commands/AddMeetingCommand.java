package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.Phone;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Type;

/**
 * Adds a {@code Meeting} to the meeting book.
 */
public class AddMeetingCommand extends Command {

    public static final String COMMAND_WORD = "addmeeting";

    public static final String MESSAGE_USAGE = String
            .format("%s: Adds a meeting to the meeting book.\n"
                            + "Parameters: %sMEETING_TITLE %sMEETING_DATE %sBUYER_PHONE %sSELLER_PHONE %sTYPE "
                            + "%sPOSTALCODE\n"
                            + "Restrictions:\n"
                            + "\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s",
                    COMMAND_WORD, PREFIX_MEETING_TITLE, PREFIX_MEETING_DATE, PREFIX_BUYER_PHONE, PREFIX_SELLER_PHONE,
                    PREFIX_TYPE, PREFIX_POSTALCODE, MeetingTitle.MESSAGE_CONSTRAINTS, MeetingDate.MESSAGE_CONSTRAINTS,
                    Phone.MESSAGE_CONSTRAINTS, Type.MESSAGE_CONSTRAINTS, PostalCode.MESSAGE_CONSTRAINTS,
                    "There must be an existing buyer in the client book with a phone number equal to BUYER_PHONE. "
                            + "Likewise for SELLER_PHONE.", "Postal Code must belong to some existing property "
                            + "in the property book of the specified Type.");

    public static final String MESSAGE_SUCCESS = "New meeting added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEETING = "There is already a meeting of the same title "
            + "scheduled on this date. Please change either the meeting title or date.";

    private final Meeting toAdd;

    /**
     * Creates an AddMeetingCommand to add the specified {@code Meeting}
     */
    public AddMeetingCommand(Meeting meeting) {
        requireNonNull(meeting);
        toAdd = meeting;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_CLIENTS);

        if (model.hasMeeting(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        }

        model.getFilteredClientList().stream()
                .filter(Client::isBuyer)
                .filter(client -> client.getPhone().equals(toAdd.getBuyerPhone()))
                .findFirst().orElseThrow(() ->
                        new CommandException(
                                String.format("Buyer with phone number: "
                                        + toAdd.getBuyerPhone().toString() + " not found.")));
        model.getFilteredClientList().stream()
                .filter(Client::isSeller)
                .filter(client -> client.getPhone().equals(toAdd.getSellerPhone()))
                .findFirst().orElseThrow(() ->
                        new CommandException(
                                String.format("Seller with phone number: "
                                        + toAdd.getSellerPhone().toString() + " not found.")));
        model.getFilteredPropertyList().stream().filter(p ->
                p.getType().equals(toAdd.getType()) && p.getPostalCode().equals(toAdd.getPostalCode()))
                .findFirst().orElseThrow(() ->
                        new CommandException(String.format("Property not found. ", toAdd.getPostalCode())));

        model.addMeeting(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddMeetingCommand)) {
            return false;
        }

        AddMeetingCommand otherAddMeetingCommand = (AddMeetingCommand) other;
        return toAdd.equals(otherAddMeetingCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
