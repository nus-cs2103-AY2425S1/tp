package seedu.address.ui.commandpopup;

import java.util.SortedSet;
import java.util.TreeSet;

public class PopUpCommandsSet {
    public static SortedSet<String> commandSet() {
        TreeSet<String> commandSet = new TreeSet<>();
        commandSet.add("help");
        commandSet.add("editgame");
        commandSet.add("edit");

        return commandSet;
    }

}
