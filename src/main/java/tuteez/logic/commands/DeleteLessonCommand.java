package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Person;
import tuteez.model.person.lesson.Lesson;

/**
 * Deletes a lesson from a specified student.
 */
public class DeleteLessonCommand extends LessonCommand {
    public static final String MESSAGE_SUCCESS = "Deleted lessons from %1$s:\n%2$s";
    public static final String MESSAGE_INVALID_LESSON_INDEX = "The following lesson indices are invalid:\n%s";
    public static final String MESSAGE_USAGE = "Delete lessons by index in displayed student list: "
            + COMMAND_WORD_DELETE
            + " (short form: " + COMMAND_WORD_DELETE_ALT + ")"
            + " INDEX li/LESSON_INDEX [li/LESSON_INDEX]...\n"
            + "Example: " + COMMAND_WORD_DELETE + " 1 li/1 li/2";

    private final List<Index> lessonIndices;

    /**
     * Deletes a Lesson with the specified {@code lessonIndex} from the student with the {@code studentIndex}
     * of the displayed list.
     * @param personIndex The Index of the student in the displayed list to delete the lesson from
     * @param lessonIndices A List of Index's of the lesson to be deleted
     */
    public DeleteLessonCommand(Index personIndex, List<Index> lessonIndices) {
        super(personIndex);
        this.lessonIndices = lessonIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToUpdate = getPersonFromModel(model);

        List<Lesson> currentLessons = new ArrayList<>(personToUpdate.getLessons().stream()
                .sorted(new Lesson.LessonComparator())
                .toList());
        List<Lesson> lessonsToDelete = new ArrayList<>();
        List<Index> invalidIndices = new ArrayList<>();

        for (Index index : lessonIndices) {
            if (index.getZeroBased() < 0 || index.getZeroBased() >= currentLessons.size()) {
                invalidIndices.add(index);
                continue;
            }
            lessonsToDelete.add(currentLessons.get(index.getZeroBased()));
        }

        if (!invalidIndices.isEmpty()) {
            throw new CommandException(generateInvalidIndexMessage(invalidIndices));
        }

        currentLessons.removeAll(lessonsToDelete);

        Person updatedPerson = updatePersonLessons(personToUpdate, currentLessons);
        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateLastViewedPerson(updatedPerson);

        return new CommandResult(generateResultMessage(personToUpdate, lessonsToDelete));
    }

    private Person updatePersonLessons(Person person, List<Lesson> updatedLessons) {
        assert person != null;
        assert updatedLessons != null;
        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getTelegramUsername(), person.getTags(), updatedLessons, person.getRemarkList());
    }

    private String generateResultMessage(Person personToUpdate, List<Lesson> deletedLessons) {
        return String.format(MESSAGE_SUCCESS, personToUpdate.getName(), formatLessonList(deletedLessons));
    }

    private String formatLessonList(List<Lesson> lessons) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lessons.size(); i++) {
            sb.append("• ").append(lessons.get(i).toString());
            if (i < lessons.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private String generateInvalidIndexMessage(List<Index> invalidIndices) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < invalidIndices.size(); i++) {
            sb.append(invalidIndices.get(i).getOneBased());
            if (i < invalidIndices.size() - 1) {
                sb.append(", ");
            }
        }
        return String.format(MESSAGE_INVALID_LESSON_INDEX, sb.toString());
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
                && lessonIndices.equals(otherDeleteLessonCommand.lessonIndices);
    }

}
