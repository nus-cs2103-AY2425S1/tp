package seedu.address.logic.commands;

import java.util.ArrayList;

public class CommandHistory extends ArrayList<String> {
    private static final CommandHistory INSTANCE = new CommandHistory();
    private static int pointer = -1;

    public static void addCommand(String command) {
        INSTANCE.add(command);
        pointer = INSTANCE.size();
    }

    public static String getPreviousPointerCommand() {
        if (INSTANCE.isEmpty()) {
            return "";
        }

        if (pointer == 0) {
            return INSTANCE.get(pointer);
        }

        return INSTANCE.get(--pointer);
    }

    public static String getNextPointerCommand() {
        if (INSTANCE.isEmpty() || pointer >= INSTANCE.size() - 1) {
            return "";
        }

        return INSTANCE.get(++pointer);
    }
}
