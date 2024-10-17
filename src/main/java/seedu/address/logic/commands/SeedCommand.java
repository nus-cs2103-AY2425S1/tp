package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

/**
 * Seeds dummy data into the SocialBook.
 */
public class SeedCommand extends Command {

    public static final String COMMAND_WORD = "seed";

    public static final String MESSAGE_SUCCESS = "Seeded sample data";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Seeds dummy data into the SocialBook.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        for (Person person : SampleDataUtil.getSamplePersons()) {
            if (model.hasPerson(person)) {
                continue;
            }
            model.addPerson(person);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
