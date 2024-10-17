package seedu.address.logic.commands;

public enum CommandKeywords {
    add,
    addf,
    appt,
    clear,
    delete,
    edit,
    exit,
    find,
    help,
    list;

    public static boolean isValidCommandKeyword(String keyword) {
        for (CommandKeywords command : CommandKeywords.values()) {
            if (command.name().equalsIgnoreCase(keyword)) {
                return true;
            }
        }
        return false;
    }
}
