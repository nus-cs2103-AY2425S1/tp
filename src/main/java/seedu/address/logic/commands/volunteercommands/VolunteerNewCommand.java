package seedu.address.logic.commands.volunteercommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.AddressBookParser.VOLUNTEER_COMMAND_INDICATOR;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_AVAILABLE_DATE;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_PHONE;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.volunteer.Volunteer;

/**
 * Adds a volunteer to the system.
 */
public class VolunteerNewCommand extends Command {
    public static final String COMMAND_WORD = "new";
    private static final String MESSAGE_DUPLICATE_VOLUNTEER = "This volunteer already exists!";
    private static final String MESSAGE_SUCCESS = "Volunteer added successfully!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a volunteer to the system. "
            + "Parameters: "
            + VOLUNTEER_PREFIX_NAME + "NAME "
            + VOLUNTEER_PREFIX_PHONE + "PHONE "
            + VOLUNTEER_PREFIX_EMAIL + "EMAIL "
            + VOLUNTEER_PREFIX_AVAILABLE_DATE + "AVAILABLE_DATE "
            + "Example: " + VOLUNTEER_COMMAND_INDICATOR + " " + COMMAND_WORD + " "
            + VOLUNTEER_PREFIX_NAME + "John Doe "
            + VOLUNTEER_PREFIX_PHONE + "98765432 "
            + VOLUNTEER_PREFIX_EMAIL + "johndoe@example.com "
            + VOLUNTEER_PREFIX_AVAILABLE_DATE + "2020-10-10 ";
    // Example: /v new n/John Doe p/98765432 em/johndoe@example.com d/2024-10-15 s/09:00 e/17:00
    private final Volunteer toAdd;

    /**
     * Constructs a {@code VolunteerNewCommand} that adds the specified {@code Volunteer} to the system.
     *
     * @param volunteer The volunteer to be added. Must not be null.
     */
    public VolunteerNewCommand(Volunteer volunteer) {
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
