package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_TIME;

import seedu.address.logic.commands.ScheduleCommand;

/**
 * A utility class for Schedule.
 */
public class ScheduleUtil {

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getScheduleDescriptorDetails(ScheduleCommand.ScheduleDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getScheduleName().ifPresent(
                scheduleName -> sb.append(PREFIX_SCHEDULE_NAME).append(scheduleName).append(" "));
        descriptor.getDateString().ifPresent(
                scheduleDate -> sb.append(PREFIX_SCHEDULE_DATE).append(scheduleDate).append(" "));
        descriptor.getTimeString().ifPresent(
                scheduleTime -> sb.append(PREFIX_SCHEDULE_TIME).append(scheduleTime).append(" "));
        return sb.toString();
    }
}
