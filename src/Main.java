import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    // userInput function that gathers the user input and returns it to where it's used.
    public static int userInput() {
        System.out.println("Enter 1 to Search by Member ID.");
        System.out.println("Enter 2 to Learn More About Our Rewards!");
        System.out.println("Enter 3 to Sign Up.");
        System.out.println("Enter 4 to Input Flight Miles (Members Only).");
        System.out.println("Enter 0 to Exit this program.");
        Scanner object = new Scanner(System.in);
        return object.nextInt();
    }


    public static class FlightRecord {

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

    public static class Rewards {

        private static final int gold = 25000;
        private static final int plat = 50000;
        private static final int platPro = 75000;
        private static final int execPlat = 100000;
        private static final int superExecPlat = 150000;

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
                    System.out.println(
                    "\n-----------------------------------------------------------------------" +
                    "\nWelcome user " + iDMember(memberFlights) + "!" +
                    "\nTotal miles you flew this year: " + yearSumMiles(memberFlights) +
                    "\nTotal miles flown since joining: " + totSumMiles(memberFlights) +
                    "\nDate joined: " + joinDate(memberFlights));
                    if(prevYearSumMiles(memberFlights) == 0){
                        System.out.println(rewardTier(currentYearSumMiles(memberFlights)));
                    }
                    else{
                        System.out.println(rewardTier(prevYearSumMiles(memberFlights)));
                    }
                    System.out.println(
                    prevRewardTier(yearBeforeSumMiles(memberFlights)) + "\n" +
                    "-----------------------------------------------------------------------\n");
                    break;
                case 2:
                    System.out.println(displayRewards());
                    break;

                case 3:
                    final FlightRecord flightRecord = createAccount();
                    fileInfo.add(flightRecord);
                    Files.write(
                        Paths.get("inputFile.txt"),
                        (flightRecord + "\n").getBytes(),
                        StandardOpenOption.APPEND
                    );
                    String displayOutput = displayNewMemberInfo(flightRecord.getDate(), flightRecord.memberID, flightRecord.flightMiles);
                    System.out.println(displayOutput);
                    break;
                case 4:
                    final FlightRecord addingOnToFlightRecord = inputFlightMiles(fileInfo);
                    fileInfo.add(addingOnToFlightRecord);
                    Files.write(
                            Paths.get("inputFile.txt"),
                            (addingOnToFlightRecord + "\n").getBytes(),
                            StandardOpenOption.APPEND
                    );
                    System.out.println("You're all set! Enjoy your flight!");
                    break;
                default:
                    System.out.println("\n\nPlease enter a valid menu option.\n\n");
                    break;
            }

        }
    }

    public static List<FlightRecord> readFile() throws IOException {
        List<FlightRecord> fileInfo = new ArrayList<>();
        Scanner inputFileScanner = new Scanner(new FileReader("inputFile.txt"));

        while (inputFileScanner.hasNextLine() && inputFileScanner.hasNext()) {

            final FlightRecord fileObjects = new FlightRecord(
                    LocalDate.parse(inputFileScanner.next()),
                    inputFileScanner.nextInt(),
                    inputFileScanner.nextInt()
            );

            fileInfo.add(fileObjects);
        }
        return fileInfo;
    }

    public static int iDMember(final List<FlightRecord> memberFlights) {
        int memberID = 0;
        for (final FlightRecord flightRecord : memberFlights) {

            memberID = flightRecord.getMemberID();
        }

        return memberID;
    }

    public static List<FlightRecord> searchFunction(final List<FlightRecord> flightRecords) {

        final List<FlightRecord> memberFlights = new ArrayList<>();
        System.out.println("\nPlease enter your Member ID.\n");
        final int memberId = scanner.nextInt();
        for (final FlightRecord flightRecord : flightRecords) {
            if (memberId == flightRecord.memberID) {
                memberFlights.add(flightRecord);
            }
        }
        return memberFlights;
    }


    public static int yearSumMiles(final List<FlightRecord> flightRecords) {

        int sum = 0;
        int maxYear = 0;

        for (final FlightRecord flightRecord : flightRecords) {
            if (flightRecord.getDate().getYear() > maxYear) {
                maxYear = flightRecord.getDate().getYear();
            }
        }

        for (final FlightRecord flightRecord : flightRecords) {
            if (flightRecord.getDate().getYear() == maxYear) {
                sum += flightRecord.getFlightMiles();
            }
        }

        return sum;
    }

    public static int totSumMiles(final List<FlightRecord> memberFlights) {
        int add = 0;
        for (final FlightRecord flightRecord : memberFlights) {
            add += flightRecord.getFlightMiles();
        }
        return add;
    }

    public static LocalDate joinDate(final List<FlightRecord> flightRecords) {

        LocalDate joinDate = LocalDate.MAX;

        for (final FlightRecord flightRecord : flightRecords) {
            if (flightRecord.getDate().getYear() < joinDate.getYear()) {
                joinDate = flightRecord.getDate();
            }
        }

        return joinDate;
    }
    public static String rewardTier(int sum) {
        if (sum < Rewards.gold) {
            return "You are not in a rewards Tier yet. " + milesNeeded(sum) + " miles needed to obtain gold Status.";
        }
        else if (sum < Rewards.plat) {
            return "Current Membership Tier: Gold";
        }
        else if (sum < Rewards.platPro) {
            return "Current Membership Tier: Platinum";
        }
        else if (sum < Rewards.execPlat) {
            return "Current Membership Tier: Platinum Pro";
        }
        else if (sum < Rewards.superExecPlat) {
            return "Current Membership Tier: Executive Platinum";
        }
        else {
            return "Current Membership Tier: Super Executive Platinum";
        }
    }

    public static String prevRewardTier(int sum) {
        if (sum == 0){
            return "You weren't in a reward tier last year.";
        }
        else if (sum < Rewards.gold) {
            return "You weren't in a reward tier last year. You needed " + milesNeeded(sum) + " miles to obtain gold Status.";
        }
        else if (sum < Rewards.plat) {
            return "Last Year you were in Membership Tier: Gold";
        }
        else if (sum < Rewards.platPro) {
            return "Last Year you were in Membership Tier: Platinum";
        }
        else if (sum < Rewards.execPlat) {
            return "Last Year you were in Membership Tier: Platinum Pro";
        }
        else if (sum < Rewards.superExecPlat) {
            return "Last Year you were in Membership Tier: Executive Platinum";
        }
        else{
            return "Last Year you were in Membership Tier: Super Executive Platinum";
        }
    }

    public static int milesNeeded(int sum) {
        return (Rewards.gold - sum);
    }
    public static int currentYearSumMiles(final List<FlightRecord> memberFlights) {

        int sum = 0;
        int maxYear = 0;

        for (final FlightRecord flightRecord : memberFlights) {
            if (flightRecord.getDate().getYear() > maxYear) {
                maxYear = flightRecord.getDate().getYear();
            }
        }

        for (final FlightRecord flightRecord : memberFlights) {
            if (flightRecord.getDate().getYear() == maxYear) {
                sum += flightRecord.getFlightMiles();
            }
        }
        return sum;
    }
    public static int prevYearSumMiles(final List<FlightRecord> memberFlights) {

        int sum = 0;
        int maxYear = 0;

        for (final FlightRecord flightRecord : memberFlights) {
            if (flightRecord.getDate().getYear() > maxYear) {
                maxYear = flightRecord.getDate().getYear() - 1;
            }
        }

        for (final FlightRecord flightRecord : memberFlights) {
            if (flightRecord.getDate().getYear() == maxYear) {
                sum += flightRecord.getFlightMiles();
            }
        }
        return sum;
    }
    public static int yearBeforeSumMiles(final List<FlightRecord> memberFlights) {

        int sum = 0;
        int maxYear = 0;

        for (final FlightRecord flightRecord : memberFlights) {
            if (flightRecord.getDate().getYear() > maxYear) {
                maxYear = flightRecord.getDate().getYear() - 2;
            }
        }

        for (final FlightRecord flightRecord : memberFlights) {
            if (flightRecord.getDate().getYear() == maxYear) {
                sum += flightRecord.getFlightMiles();
            }
        }
        return sum;
    }
    public static String displayRewards() {

        return "\n---------------------------------------------------------------------------------------------------------------------------\n\n" +
                "Gold(25,000 miles):\n  Gold passengers get special perks such as a seat to sit in during the flight.\n\n" +
               "Platinum(50,000 miles):\n   Platinum passengers get complementary upgrades to padded seats.\n\n" +
               "Platinum Pro(75,000 miles):\n   Platinum Pro is a special sub-tier of Platinum, in which the\n" +
                "padded seats include arm rests.\n\n" +
                "Executive Platinum(100,000 miles):\n   Executive Platinum passengers enjoy perks such as " +
                "complementary upgrades from the cargo hold to main cabin.\n\n" +
                "Super Executive Platinum(150,000 miles):\n   Super Executive Platinum is a special sub-tier " +
                "of Executive Platinum, reserved for the most loyal passengers.\n   To save costs, airline " +
                "management decided to eliminate the position of co-pilot, instead opting to reserve the " +
                "co-pilot’s seat for Super Executive Platinum passengers.\n\n" +
                "---------------------------------------------------------------------------------------------------------------------------\n";
    }
    public static FlightRecord createAccount() {
        System.out.println("Please enter the date as (YYYY-MM-DD).");
        LocalDate inputDate = userInputDate();
        int randomIdNumber = memberIDGenerator();
        System.out.println("How many miles is your current flight?");
        int userFlightMiles = userInputInt();
        return new FlightRecord(inputDate,randomIdNumber,userFlightMiles);
    }

    public static int userInputInt(){
        while(true) {
            try {
                 return scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("Not a valid Integer.");
            }
        }
    }

    public static LocalDate userInputDate(){
        while(true) {
            try {
                return LocalDate.parse(scanner.next());
            } catch (DateTimeParseException exception) {
                System.out.println("Not a valid date.");
            }
        }
    }
    public static int memberIDGenerator(){
        Random newID = new Random();
        int upperbound = 1000;
        return newID.nextInt(upperbound);
    }
    public static String displayNewMemberInfo(LocalDate inputDate, int randomIdNumber, int userFlightMiles) {
        return "Your new Member Id is: " + randomIdNumber +
                "\nCreated on: " + inputDate +
                "\nThat has " + userFlightMiles + "miles so far.\n";
    }
    public static FlightRecord inputFlightMiles(List<FlightRecord> flightRecords) {
        LocalDate todayDate = null;
        System.out.println("PLease enter your Member ID.");
        int input = scanner.nextInt();
        int milesFlown = 0;
            if (input == memberIDFinder(flightRecords,input)) {
                System.out.println("What is today's date?");
                todayDate = userInputDate();
                System.out.println("How many miles are you flying today?");
                milesFlown = userInputInt();
            }
            else{
                System.out.println("Please enter a valid Member ID.");
                inputFlightMiles(flightRecords);
            }
        return new FlightRecord(todayDate, input,milesFlown);
    }
    public static int memberIDFinder(List<FlightRecord> flightRecords, int input){
        for (final FlightRecord flightRecord : flightRecords) {
            if (input == flightRecord.memberID) {
                return input;
            }
        }
        return 0;
    }
}