package seedu.address.logic.commands;

import seedu.address.model.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "list";

    private String name;
    private LocalDateTime date;
    public ScheduleCommand(String name, String date) {
        this.name = name;
        this.date = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult("todo");
    }
}
