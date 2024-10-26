package seedu.address.logic;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    private final List<String> history;
    private int currentIndex;

    public CommandHistory() {
        history = new ArrayList<>();
        currentIndex = -1;
    }

    public void addCommand(String command) {
        if (!command.isBlank() && (history.isEmpty() || !history.get(history.size() - 1).equals(command))) {
            history.add(command);
        }
        currentIndex = history.size();
    }

    public String getPreviousCommand() {
        if (currentIndex > 0) {
            currentIndex--;
            return history.get(currentIndex);
        }
        return history.isEmpty() ? "" : history.get(0);
    }

    public String getNextCommand() {
        if (currentIndex < history.size() - 1) {
            currentIndex++;
            return history.get(currentIndex);
        } else {
            currentIndex = history.size();
            return "";
        }
    }
}
