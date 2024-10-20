package seedu.address.ui.commandpopup;

import java.util.SortedSet;
import java.util.TreeSet;

public class PopUpCommandsSet {
    public static SortedSet<String> commands() {
        TreeSet<String> commandSet = new TreeSet<>();
        commandSet.add("add");
        commandSet.add("help");
        commandSet.add("editgame");
        commandSet.add("edit");
        commandSet.add("delete");
        commandSet.add("find");
        commandSet.add("list");
        commandSet.add("save");
        commandSet.add("load");
        commandSet.add("clear");
        return commandSet;
    }

}
