package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;
import static tuteez.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tuteez.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;
import static tuteez.logic.parser.CliSyntax.PREFIX_NAME;
import static tuteez.logic.parser.CliSyntax.PREFIX_PHONE;
import static tuteez.logic.parser.CliSyntax.PREFIX_TAG;
import static tuteez.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.commons.util.ToStringBuilder;
import tuteez.logic.Messages;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Person;
import tuteez.model.person.lesson.Lesson;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to the address book. "
            + "Compulsory Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE ; "
            + "Optional Parameters:"
            + " [" + PREFIX_EMAIL + "EMAIL]"
            + " [" + PREFIX_ADDRESS + "ADDRESS]"
            + " [" + PREFIX_TELEGRAM + "TELEGRAM_USERNAME]"
            + " [" + PREFIX_TAG + "TAG]..."
            + " [" + PREFIX_LESSON + "LESSON]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@gmail.com "
            + PREFIX_ADDRESS + "Clementi "
            + PREFIX_TELEGRAM + "john_doe "
            + PREFIX_TAG + "Physics "
            + PREFIX_TAG + "Secondary 4 "
            + PREFIX_LESSON + "monday 0900-1100 "
            + PREFIX_LESSON + "saturday 0800-1000\n"
            + "Note: Parameters marked optional can be omitted.";

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";
    public static final String MESSAGE_CLASHING_LESSON = "This time slot clashes with the following lessons:";
    public static final String MESSAGE_NEW_LESSONS_CLASH = "Added lessons clash with one another";

    private final Person toAdd;
    private final Logger logger = LogsCenter.getLogger(AddCommand.class);

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_PERSON);
        }

        List<Lesson> lessonList = toAdd.getLessons();
        if (Lesson.hasClashingLessonWithinList(lessonList)) {
            logger.info("There are clashing lessons within the following list: " + lessonList);
            throw new CommandException(MESSAGE_NEW_LESSONS_CLASH);
        }

        Map<Person, ArrayList<Lesson>> resultMap = findAllClashingLessonsMap(model, lessonList);

        if (!resultMap.isEmpty()) {
            String logMessage = String.format("Student: %s | Lessons: %s | Conflict: Clashes with "
                    + "another student's lesson, clashes logged below", toAdd.getName(), toAdd.getLessons().toString());
            logger.info(logMessage);

            String clashMsg = generateClashMessage(resultMap);
            logger.info(clashMsg);
            throw new CommandException(clashMsg);
        }

        model.addPerson(toAdd);
        logger.info("Student Added - " + toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    /**
     * Finds all lessons that clash with the provided list of lessons for different students.
     *
     * <p>This method checks each lesson in the given list against the existing lessons in the model
     * to identify any scheduling conflicts with lessons for other students. If any clashes are found,
     * they are added to a map with the student and their conflicting lessons.</p>
     *
     * @param model The model, representing in-memory model for app
     * @param lessonList The list of lessons to check for clashes.
     * @return A map where each key is a {@code Person} representing a student, and the value is an
     *         {@code ArrayList} of {@code Lesson} objects that are allocated to student and
     *         conflict with the given lessons.
     */
    public Map<Person, ArrayList<Lesson>> findAllClashingLessonsMap(Model model, List<Lesson> lessonList) {
        Map<Person, ArrayList<Lesson>> resultMap = new HashMap<>();
        for (Lesson lesson: lessonList) {
            assert lesson != null;
            Map<Person, ArrayList<Lesson>> clashingLessons = model.getClashingLessons(lesson);

            clashingLessons.forEach((person, lessons) -> {
                assert !person.equals(toAdd) : "By this point a duplicate person should be flagged out";
                resultMap.computeIfAbsent(person, k -> new ArrayList<>()).addAll(lessons);
            });
        }
        return resultMap;
    }

    /**
     * Generates a message listing all clashing lessons for each student.
     *
     * <p>This method formats a user-friendly message based on the provided map of students and their
     * clashing lessons. The message includes each student's name followed by the day and time of their
     * clashing lessons.</p>
     *
     * @param studentLessonMap A map where each key is a {@code Person} (student) and the value is an
     *                         {@code ArrayList} of {@code Lesson} objects representing clashing lessons.
     * @return A formatted {@code String} message listing each student and their clashing lessons.
     */
    public String generateClashMessage(Map<Person, ArrayList<Lesson>> studentLessonMap) {
        StringBuilder clashMsg = new StringBuilder(MESSAGE_CLASHING_LESSON).append("\n");
        studentLessonMap.keySet().forEach(student -> {
            clashMsg.append(student.getName()).append(": ");

            studentLessonMap.get(student).forEach(ls -> clashMsg.append(ls.getDayAndTime()).append(" "));
            clashMsg.append("\n");
        });
        return clashMsg.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
