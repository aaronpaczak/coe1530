package coe1530.eatyourleftovers;
import android.util.Log;

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
     * An array of Calendar Items that appear on the list in the recycler view.
     * - gets updated when the user clicks a date on the calendar view
     * The second one is a list of calendar items for today
     * - gets updated when the user clicks update calendar button
     */
    public static final List<CalendarItem> SHOWN_ITEMS = new ArrayList<CalendarItem>();
    public static final List<CalendarItem> TODAYS_ITEMS = new ArrayList<CalendarItem>();

    /**
     * A map of items, by ID.
     */
    public static final Map<String, CalendarItem> SHOWN_MAP = new HashMap<String, CalendarItem>();
    public static final Map<String, CalendarItem> TODAY_MAP = new HashMap<String, CalendarItem>();

    private static final int COUNT = 0;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createItem("15:00", "15:50", "This is an example of a CalendarItem.", false), false);
        }
    }

    private static void addItem(CalendarItem item, boolean today) {
        if (today) {
            TODAYS_ITEMS.add(item);
            TODAY_MAP.put(item.title, item);
        } else {
            SHOWN_ITEMS.add(item);
            SHOWN_MAP.put(item.title, item);
        }
    }

    private static void removeItem(CalendarItem item, boolean today) {
        if (today) {
            TODAYS_ITEMS.remove(item);
            TODAY_MAP.remove(item.title);
        } else {
            SHOWN_ITEMS.remove(item);
            SHOWN_MAP.remove(item.title);
        }
    }

    private static CalendarItem createItem(String startTime, String endTime, String title, boolean today) {
        return new CalendarItem(startTime, endTime, title, today);
    }

    // Calendar Item for display purposes.... MUCH more is need to add an event ot calendar.
    public static class CalendarItem {
        public final String startTime;
        public String endTime;
        public String title;

        public CalendarItem(String startTime, String endTime, String title, boolean today) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.title = title;

            addItem(this, today);
            Log.w("CALLISTLEN", "today: " + TODAYS_ITEMS.size() + " shown: " + SHOWN_ITEMS.size());
        }

        @Override
        public String toString() {
            return title;
        }
    }
}