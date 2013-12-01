package free_messaging;

/**
 *
 * @author Mahfuz
 */
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.Who;
import com.google.gdata.data.extensions.Who.AttendeeStatus;

import java.net.URL;
import java.util.HashMap;

public class Controler {
    private HashMap<String, String> initData ;
    private CalendarClient client = null;
    
// max number of characters in one SMS.
    private int numOfChars;
    
    private int numOfSMSMessages;
    
    private static Controler instance;
    
    private Controler() {
        initData = new HashMap<String, String>();
        client = null;
    }
    
    static Controler getInstance()
    {
        if (instance == null) instance =  new Controler();
        return instance;
    }
    
    protected void setProperty(String key, String value)
    {
        initData.put(key,value);
    }
    
    protected String getProperty(String key)
    {
        String value = (String)initData.get(key);
        return value;
    }

    public CalendarClient getClient() {
        return client;
    }

    public void setClient(CalendarClient client) {
        this.client = client;
    }

    public int getNumOfChars() {
        return numOfChars;
    }

    public void setNumOfChars(int numOfChars) {
        this.numOfChars = numOfChars;
    }

    public int getNumOfSMSMessages() {
        return numOfSMSMessages;
    }

    public void setNumOfSMSMessages(int numOfSMSMessages) {
        this.numOfSMSMessages = numOfSMSMessages;
    }
    
}
