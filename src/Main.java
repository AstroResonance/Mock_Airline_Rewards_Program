import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // userInput function that gathers the user input and returns it to where it's used.
    public static int userInput() {
        System.out.println("Enter 1 to Search by Member ID.");
        System.out.println("Enter 2 to Learn More About Our Rewards!");
        System.out.println("Enter 0 to Exit this program.");
        Scanner object = new Scanner(System.in);
        int userOption = object.nextInt();
        return userOption;
    }


    public static class FlightRecord {
        String date;
        int memberId;
        int flightMiles;
    }


    public static void main(String[] args) throws IOException{
        List<FlightRecord> fileInfo = readFile();
        while(true) {
            int input = userInput();
            switch (input) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    List<FlightRecord> memberFlights = searchFunction(fileInfo);
                    if (memberFlights.isEmpty()){
                        System.out.println("\n\nMember ID is not valid.\n\n");
                        continue;
                    }
                    System.out.println("Total Miles Flown by MemberID: " + input + totSumMiles(memberFlights));
                    break;
                case 2:
                    System.out.println("Learn More About Our Rewards!");
                    break;
                default:
                    System.out.println("\n\nPlease enter a valid menu option.\n\n");
                    break;
            }

        }
    }

    public static List<FlightRecord> readFile() throws IOException {
        List<FlightRecord> fileInfo = new ArrayList<>();
        Scanner scnr = new Scanner(new FileReader("inputFile.txt"));

        while(scnr.hasNextLine()){
            FlightRecord fileObjects = new FlightRecord();
            fileObjects.date = scnr.next();
            fileObjects.memberId = scnr.nextInt();
            fileObjects.flightMiles = scnr.nextInt();
            fileInfo.add(fileObjects);
        }
        return fileInfo;
    }

    public static List<FlightRecord> searchFunction(List<FlightRecord> flightRecords)
    {
        List<FlightRecord> memberFlights = new ArrayList<>();
        System.out.println("Please enter your Member ID.");
        Scanner scanner = new Scanner(System.in);
        int memberId = scanner.nextInt();
        for (FlightRecord flightRecord : flightRecords) {
            if(memberId == flightRecord.memberId) {
                memberFlights.add(flightRecord);
            }
        }
        return memberFlights;
    }

    public static int totSumMiles(List<FlightRecord> memberFlights){
        int sum = 0;
        for (FlightRecord memberFlight : memberFlights) {
            sum += memberFlight.flightMiles;
        }
        return sum;
    }
}

