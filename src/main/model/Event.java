package model;

import java.util.Calendar;
import java.util.Date;

// This code was inspired by the Alarm System application from
// github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    public Event(String description) {
        this.description = description;
        dateLogged = Calendar.getInstance().getTime();
    }

    public String getDescription() {
        return description;
    }

    public Date getDateLogged() {
        return dateLogged;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() == this.getClass()) {
            return false;
        }
        Event otherEvent = (Event) other;
        return this.dateLogged.equals(otherEvent.dateLogged)
            && this.description.equals(otherEvent.description);
    }

    public int hashCode() {
        return HASH_CONSTANT * dateLogged.hashCode() + description.hashCode();
    }

    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
