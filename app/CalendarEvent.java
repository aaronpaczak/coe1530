/**
 * class to encapsulate calendar event information
 * applies to both ToDoItems and Google Calendar events
 *
 * M. Klena
 * 11/16/18
 */
public class CalendarEvent
{
    //variables
    public String name; //name of event
    public int startTime; //beginning time of event
    public int endTime; //ending time of event
    public String description; //additional event informaiton (location, info, etc.)

    //constructor
    public CalendarEvent(String n,int start,int end,String desc)
    {
        name=n;
        startTime=start;
        endTime=end;
        description=desc;
    }

    public String getName()
    {
        return this.name;
    }
}