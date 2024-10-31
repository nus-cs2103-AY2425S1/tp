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

    /**
     * Executes the command to add a meeting.
     * Firstly Checks for duplicate meetings, verifies the existence of the buyer, seller, and property,
     * then adds the meeting to the model.
     *
     * @param model the model which contains the data and operations for the application
     * @return CommandResult indicating the success of adding the meeting
     * @throws CommandException if there are any validation issues, such as duplicate meetings,
     *                          or missing buyer, seller, or property
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_CLIENTS);

        checkForDuplicateMeeting(model);
        checkBuyerExists(model);
        checkSellerExists(model);
        checkPropertyExists(model);

        model.addMeeting(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    /**
     * Checks if a meeting with the same details already exists in the model.
     *
     * @param model the model to check for existing meetings
     * @throws CommandException if a meeting with the same details already exists
     */
    private void checkForDuplicateMeeting(Model model) throws CommandException {
        if (model.hasMeeting(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        }
    }

    /**
     * Verifies the existence of a buyer in the client list by checking the buyer's phone number.
     *
     * @param model the model containing the client list
     * @throws CommandException if the buyer with the specified phone number is not found
     */
    private void checkBuyerExists(Model model) throws CommandException {
        model.getFilteredClientList().stream()
                .filter(Client::isBuyer)
                .filter(client -> client.getPhone().equals(toAdd.getBuyerPhone()))
                .findFirst().orElseThrow(() ->
                        new CommandException(String.format("Buyer with phone number: %s not found.",
                                toAdd.getBuyerPhone().toString())
                        ));
    }

    /**
     * Verifies the existence of a seller in the client list by checking the seller's phone number.
     *
     * @param model the model containing the client list
     * @throws CommandException if the seller with the specified phone number is not found
     */
    private void checkSellerExists(Model model) throws CommandException {
        model.getFilteredClientList().stream()
                .filter(Client::isSeller)
                .filter(client -> client.getPhone().equals(toAdd.getSellerPhone()))
                .findFirst().orElseThrow(() ->
                        new CommandException(
                                String.format("Seller with phone number: %s not found.",
                                        toAdd.getSellerPhone().toString())
                        ));
    }

    /**
     * Verifies the existence of a property with the specified type and postal code in the property list.
     *
     * @param model the model containing the property list
     * @throws CommandException if the property with the specified type and postal code is not found
     */
    private void checkPropertyExists(Model model) throws CommandException {
        model.getFilteredPropertyList().stream()
                .filter(property ->
                        property.getType().equals(toAdd.getType())
                                && property.getPostalCode().equals(toAdd.getPostalCode())
                )
                .findFirst().orElseThrow(() ->
                        new CommandException(String.format("Property not found. Postal code: %s",
                                toAdd.getPostalCode())));
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
