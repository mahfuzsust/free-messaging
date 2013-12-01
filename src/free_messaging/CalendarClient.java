package free_messaging;

/**
 *
 * @author Mahfuz
 */
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.extensions.Who;
import com.google.gdata.data.extensions.Who.AttendeeStatus;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CalendarClient {

    private static final String CALENDAR_FEEDS_URL = "http://www.google.com/calendar/feeds";
    private CalendarService calendarService;
    private URL privateFeedUrl;

    public CalendarClient(String username, String password) throws Exception {
        calendarService = new CalendarService("Free Messaging");
        calendarService.setUserCredentials(username, password);
        privateFeedUrl = new URL(CALENDAR_FEEDS_URL + "/default/private/full");
    }

    public void addEvent(String message, Object[] recipients) throws Exception {

        Map<String, String> attendees = new HashMap<String, String>();
        for (int i = 0; i < recipients.length; i++) {
            attendees.put(recipients[i].toString(), AttendeeStatus.EVENT_INVITED);
        }

        CalendarEventEntry myEntry = createEntryObject(message, attendees);

        CalendarEventEntry insertedEntry = calendarService.insert(privateFeedUrl, myEntry);
    }

   
    private CalendarEventEntry createEntryObject(String titleText,
            Map<String, String> attendeeList) {
        // Set title 
        CalendarEventEntry myEntry = new CalendarEventEntry();
        myEntry.setTitle(new PlainTextConstruct(titleText));
        //vazno!!!
        myEntry.setSendEventNotifications(true);


        if (attendeeList != null) {
            for (Map.Entry<String, String> entry : attendeeList.entrySet()) {
                Who who = new Who();
                who.setEmail(entry.getKey());
                who.setAttendeeStatus(entry.getValue());
                myEntry.getParticipants().add(who);
            }
        }
        return myEntry;
    }

}
