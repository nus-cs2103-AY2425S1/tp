package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import seedu.address.model.Model;

public class AggGradeCommand extends Command {
    public static final String COMMAND_WORD = "aggGrade";
    public static final Map<String, Operation> OPERATION_TRANSLATE = Collections.unmodifiableMap(
            Map.of("median", Operation.MEDIAN));


    public enum Operation {
        MEDIAN
    }

    private Operation operation;

    public AggGradeCommand(Operation operation) {
        requireNonNull(operation);
        this.operation = operation;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        switch (this.operation) {
        case MEDIAN:
            return new CommandResult("THIS IS MEDIAN");
        }
        throw new IllegalStateException();
    }
}
