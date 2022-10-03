package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// This code was inspired by the Alarm System application from
// github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;

    private EventLog() {
        events = new ArrayList<Event>();
    }

    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    public void logEvent(Event e) {
        events.add(e);
    }

    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared"));
    }

    public Iterator<Event> iterator() {
        return events.iterator();
    }


}
