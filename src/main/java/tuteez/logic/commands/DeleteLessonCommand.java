package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import tuteez.commons.core.index.Index;
import tuteez.logic.Messages;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Person;
import tuteez.model.person.lesson.Lesson;

/**
 * Deletes a lesson from a specified student.
 */
public class DeleteLessonCommand extends LessonCommand {
    public static final String DELETE_LESSON_PARAM = "-d";

    private final Index lessonIndex;

    /**
     * Deletes a Lesson with the specified {@code lessonIndex} from the student with the {@code personIndex}
     * of the displayed list.
     * @param personIndex The Index of the student in the displayed list to delete the lesson from
     * @param lessonIndex Index of the lesson to be deleted
     */
    public DeleteLessonCommand(Index personIndex, Index lessonIndex) {
        super(personIndex);
        this.lessonIndex = lessonIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToUpdate = getPersonFromModel(model);

        Set<Lesson> lessons = new HashSet<>(personToUpdate.getLessons());
        if (lessonIndex.getZeroBased() < 0 || lessonIndex.getZeroBased() >= lessons.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_INDEX);
        }
        Lesson lessonToDelete = new ArrayList<>(lessons).get(lessonIndex.getZeroBased());

        Person updatedPerson = deleteLessonFromPerson(personToUpdate, lessonToDelete);
        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format("Deleted lesson at index %1$s from %2$s: %3$s",
                lessonIndex.getOneBased(), personToUpdate.getName(), lessonToDelete.toString()));
    }

    private Person deleteLessonFromPerson(Person person, Lesson lessonToDelete) {
        assert person != null;
        assert lessonToDelete != null;
        Set<Lesson> updatedLessons = new HashSet<>(person.getLessons());
        updatedLessons.remove(lessonToDelete);
        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getTelegramUsername(), person.getTags(), updatedLessons, person.getRemarkList());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteLessonCommand)) {
            return false;
        }

        DeleteLessonCommand otherDeleteLessonCommand = (DeleteLessonCommand) other;
        return personIndex.equals(otherDeleteLessonCommand.personIndex)
                && lessonIndex.equals(otherDeleteLessonCommand.lessonIndex);
    }

}
