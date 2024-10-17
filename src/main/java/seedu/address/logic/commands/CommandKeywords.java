package seedu.address.logic.commands;

public enum CommandKeywords {
    add,
    addf,
    edit,
    delete,
    clear,
    find,
    list,
    exit,
    appt;

    public static boolean isValidCommandKeyword(String keyword) {
        for (CommandKeywords command : CommandKeywords.values()) {
            if (command.name().equalsIgnoreCase(keyword)) {
                return true;
            }
        }
        return false;
    }
}
