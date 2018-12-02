package coe1530.eatyourleftovers.dummy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Encapsulation of information for a single event in the user's ToDo list
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ToDoList {

    /**
     * An array of items.
     */
    public static final List<ToDoItem> ITEMS = new ArrayList<ToDoItem>();

    /**
     * A map of items, by ID.
     */
    public static final Map<String, ToDoItem> ITEM_MAP = new HashMap<String, ToDoItem>();

    private static final int COUNT = 0;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createItem("Sample ToDoItem", 60, COUNT-i, "This is an example item."));
        }
    }

    private static void addItem(ToDoItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }

    private static void removeItem(ToDoItem item) {
        ITEMS.remove(item);
        ITEM_MAP.remove(item.name);
    }

    private static ToDoItem createItem(String name, int duration, int priority, String details) {
        return new ToDoItem(name, duration, priority, makeDetails(name, details));
    }

    private static String makeDetails(String name, String details) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about "+name+" : ").append(details);
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ToDoItem {
        public final String name; //name of task
        public int duration; //anticipated time to complete task (minute based)
        public int priority; //priority of task
        public final String details; //additional information about task

        public ToDoItem(String name, int duration, int priority, String details) {
            this.name = name;
            this.duration = duration;
            this.priority = priority;
            this.details = details;

            addItem(this);
        }

        @Override
        public String toString() {
            return name;
        }
    }
}