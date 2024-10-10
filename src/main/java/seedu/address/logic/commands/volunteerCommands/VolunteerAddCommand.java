package seedu.address.logic.commands.volunteerCommands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.volunteer.Volunteer;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_AVAILABLE_DATE;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_START_TIME;


public class VolunteerAddCommand extends Command {
    public static final String COMMAND_WORD = "addv";
    private static final String MESSAGE_DUPLICATE_VOLUNTEER = "This volunteer already exists!";
    private static final String MESSAGE_SUCCESS = "Volunteer added successfully!";

    private final Volunteer toAdd;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a volunteer to the system. "
            + "Parameters: "
            + "type/volunteer "
            + VOLUNTEER_PREFIX_NAME + "NAME "
            + VOLUNTEER_PREFIX_PHONE + "PHONE "
            + VOLUNTEER_PREFIX_EMAIL + "EMAIL "
            + VOLUNTEER_PREFIX_AVAILABLE_DATE + "AVAILABLE_DATE "
            + VOLUNTEER_PREFIX_START_TIME + "START_TIME "
            + VOLUNTEER_PREFIX_END_TIME + "END_TIME "
            + "Example: " + COMMAND_WORD + " "
            + "type/volunteer "
            + VOLUNTEER_PREFIX_NAME + "John Doe "
            + VOLUNTEER_PREFIX_PHONE + "98765432 "
            + VOLUNTEER_PREFIX_EMAIL + "johndoe@example.com "
            + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2020-10-10"
            + VOLUNTEER_PREFIX_START_TIME + "11:00 "
            + VOLUNTEER_PREFIX_END_TIME + "12:00 ";


    public VolunteerAddCommand(Volunteer volunteer) {
        requireNonNull(volunteer);
        this.toAdd = volunteer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasVolunteer(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_VOLUNTEER);
        }

        model.addVolunteer(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }
}
