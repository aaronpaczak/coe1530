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
public class CalendarList {

    /**
     * An array of items.
     */
    public static final List<CalendarItem> ITEMS = new ArrayList<CalendarItem>();

    /**
     * A map of items, by ID.
     */
    public static final Map<String, CalendarItem> ITEM_MAP = new HashMap<String, CalendarItem>();

    private static final int COUNT = 0;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createItem("15:00", "15:50", "This is an example of a CalendarItem."));
        }
    }

    private static void addItem(CalendarItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.title, item);
    }

    private static void removeItem(CalendarItem item) {
        ITEMS.remove(item);
        ITEM_MAP.remove(item.title);
    }

    private static CalendarItem createItem(String startTime, String endTime, String title) {
        return new CalendarItem(startTime, endTime, title);
    }

//    private static String makeDetails(String name, String details) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about "+name+" : ").append(details);
//        return builder.toString();
//    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class CalendarItem {
        public String startTime;
        public String endTime;
        public String title;

        public CalendarItem(String startTime, String endTime, String title) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.title = title;

            addItem(this);
        }

        @Override
        public String toString() {
            return title;
        }
    }
}