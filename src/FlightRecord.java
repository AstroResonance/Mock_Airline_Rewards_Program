import java.time.LocalDate;


/**
 * This class "FlightRecord" has members that are used for going through the array lists and adding values to those
 * array lists. I can also call specific values in the array through the members of this class.

 */

public class FlightRecord {

    private final LocalDate date;
    private final int memberID, flightMiles;

    public FlightRecord(
            final LocalDate date,
            final int memberID,
            final int flightMiles
    ) {
        this.date = date;
        this.memberID = memberID;
        this.flightMiles = flightMiles;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMemberID() {
        return memberID;
    }

    public int getFlightMiles() {
        return flightMiles;
    }

    @Override
    public String toString() {
        return date + " " + memberID + " " + flightMiles;
    }
}