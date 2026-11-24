import java.util.Date;

public class Milestone {

    private Hub hub;
    private String time;

    // Constructor 1: a new event. The time is now
    public Milestone(Hub hub) {
        this.hub = hub;
        this.time = new Date().toString(); // the time now, as text
    }

    // Constructor 2: an old event that we read from the status log. The time is given
    public Milestone(Hub hub, String time) {
        this.hub = hub;
        this.time = time;
    }

    public Hub getHub() {
        return hub;
    }

    public String getTime() {
        return time;
    }
}