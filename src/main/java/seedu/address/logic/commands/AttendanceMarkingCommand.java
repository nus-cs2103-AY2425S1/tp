package seedu.address.logic.commands;

import java.util.List;

import seedu.address.model.person.Person;

/**
 * Represents type of command about attendance marking, namely MarkAttendanceCommand and UnmarkAttendanceCommand.
 */
public abstract class AttendanceMarkingCommand extends Command {
    public static final String DISPLAY_MEMBER = "%1$s(tele: %2$s)";
    public static List<String> displayMembers(List<Person> members) {
        return members.stream()
                .map(p -> String.format(DISPLAY_MEMBER, p.getName(), p.getTelegram())).toList();
    }
}
