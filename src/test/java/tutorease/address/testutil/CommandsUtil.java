package tutorease.address.testutil;

import static tutorease.address.logic.commands.CommandTestUtil.DURATION_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.FEE_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.START_DATE_TIME_DESC;
import static tutorease.address.logic.commands.CommandTestUtil.STUDENT_ID_DESC;

import tutorease.address.commons.core.index.Index;
import tutorease.address.logic.commands.AddContactCommand;
import tutorease.address.logic.commands.ContactCommand;
import tutorease.address.logic.commands.DeleteContactCommand;
import tutorease.address.logic.commands.EditContactCommand;
import tutorease.address.logic.commands.FindContactCommand;
import tutorease.address.logic.commands.LessonCommand;
import tutorease.address.model.person.Person;

/**
 * A utility class for Commands.
 */
public class CommandsUtil {
    /**
     * Returns an uppercase add command string for adding the {@code person}.
     *
     * @param person Person object.
     * @return String of the uppercase add command.
     */
    public static String getUpperCaseAddContactCommand(Person person) {
        return ContactCommand.COMMAND_WORD.toUpperCase() + " " + AddContactCommand.COMMAND_WORD
                + " " + PersonUtil.getPersonDetails(person);
    }

    /**
     * Returns an uppercase delete command string for deleting {@code index}.
     *
     * @param index Index of the person to be deleted.
     * @return String of the uppercase delete command.
     */
    public static String getUpperCaseDeleteContactCommand(Index index) {
        return ContactCommand.COMMAND_WORD.toUpperCase() + " " + DeleteContactCommand.COMMAND_WORD
                + " " + index.getOneBased();
    }

    /**
     * Returns an uppercase edit command string for editing {@code descriptor} at the specified {@code index}.
     *
     * @param commandWord Command word.
     * @param descriptor EditPersonDescriptor object.
     * @param index Index of the person to be edited.
     * @return String of the uppercase edit command.
     */
    public static String getUpperCaseEditContactCommand(String commandWord,
                                                        EditContactCommand.EditPersonDescriptor descriptor,
                                                        Index index) {
        return ContactCommand.COMMAND_WORD + " "
                + commandWord + " " + index.getOneBased() + " "
                + PersonUtil.getEditPersonDescriptorDetails(descriptor);
    }

    /**
     * Returns an uppercase find command string for finding {@code keywords}.
     *
     * @param keywords Keywords to search for.
     * @return String of the uppercase find command.
     */
    public static String getUpperCaseFindContactCommand(String keywords) {
        return ContactCommand.COMMAND_WORD.toUpperCase() + " " + FindContactCommand.COMMAND_WORD
                + " " + keywords;
    }

    /**
     * Returns an uppercase or lowercase list command string.
     *
     * @return String of the uppercase list command.
     */
    public static String getLessonAddCommand(String commandWord) {
        return LessonCommand.COMMAND_WORD
                + " "
                + commandWord
                + " " + STUDENT_ID_DESC
                + " " + FEE_DESC
                + " " + START_DATE_TIME_DESC
                + " " + DURATION_DESC;
    }

}
