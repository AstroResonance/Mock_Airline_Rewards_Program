import java.io.FileReader;
import java.io.IOException;
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
    /**
     * This is a scanner object I can use throughout the whole program.
     */
    private static final Scanner scanner = new Scanner(System.in);


    /**
     * This method "main" is where my switch statement is and where most of the user prompts are, as well as
     * the calling of methods that are attached to those prompts. This is the main working piece of this code.
     * @return      void
     * @param       "String array of arguments."
     * @see         "Most of the User prompts as well as confirmations that the methods worked."
     */

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
                    String displayOutput = displayNewMemberInfo(flightRecord.getDate(), flightRecord.getMemberID(), flightRecord.getFlightMiles());
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


    /**
     * This method "readFile" reads the file and assigns each scan
     * into a new arrayList "fileInfo" that is categorized by class.
     * @return      Array list fileInfo.
     */


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

    /**
     * This method "userInput" displays the main menu options and
     * returns the user's input in the form of a basic type integer.
     * @return      The user's input in the form basic type integer.
     * @see         Main Menu Options.
     */

    public static int userInput() {
        System.out.println("Enter 1 to Search by Member ID.");
        System.out.println("Enter 2 to Learn More About Our Rewards!");
        System.out.println("Enter 3 to Sign Up.");
        System.out.println("Enter 4 to Input Flight Miles (Members Only).");
        System.out.println("Enter 0 to Exit this program.");
        Scanner object = new Scanner(System.in);
        return object.nextInt();
    }


    /**
     * This method "idMember" takes in the list memberFlights and
     * iterates to see if there is a match.
     * @return      memberID in the form basic type integer.
     * @param       memberFlights array list
     */


    public static int iDMember(final List<FlightRecord> memberFlights) {
        int memberID = 0;
        for (final FlightRecord flightRecord : memberFlights) {

            memberID = flightRecord.getMemberID();
        }

        return memberID;
    }


    /**
     * This method "searchFunction" takes in the list flightRecords and
     * iterates to see if there is a match with user input, if so, it adds
     * every instance of the memberID in flightRecord and assigns it to the new
     * list "memberFLights".
     * @return      List of type FlightRecord called "memberFLights".
     * @param       flightRecords array list.
     * @see         "Print Statement asking for the member ID."
     */


    public static List<FlightRecord> searchFunction(final List<FlightRecord> flightRecords) {

        final List<FlightRecord> memberFlights = new ArrayList<>();
        System.out.println("\nPlease enter your Member ID.\n");
        final int memberId = scanner.nextInt();
        for (final FlightRecord flightRecord : flightRecords) {
            if (memberId == flightRecord.getMemberID()) {
                memberFlights.add(flightRecord);
            }
        }
        return memberFlights;
    }


    /**
     * This method "yearSumMiles" takes in the list "flightRecord" and first
     * iterates through the year to find the max year,
     * then it iterates again and adds all the miles up
     * that the user flow in that year.
     * @return      Basic type integer "sum".
     * @param       flightRecords array list.
     */


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

    /**
     * This method "totSumMiles" takes in the new condensed "memberFlights"
     * list that already has the user's condensed information on it, fo this
     * method just adds all the miles ever flown by the user.
     * @return      Basic type integer "add".
     * @param       memberFlights array list.
     */


    public static int totSumMiles(final List<FlightRecord> memberFlights) {
        int add = 0;
        for (final FlightRecord flightRecord : memberFlights) {
            add += flightRecord.getFlightMiles();
        }
        return add;
    }

    /**
     * This method "joinDate" takes in the list "flightRecords" and
     * iterates through the loop to set the variable "joinDate"
     * to the lowest possible year in the list, which is subsequently
     * the user's join date.
     * @return      Type Local Date "joinDate".
     * @param       flightRecords  array list.
     */


    public static LocalDate joinDate(final List<FlightRecord> flightRecords) {

        LocalDate joinDate = LocalDate.MAX;

        for (final FlightRecord flightRecord : flightRecords) {
            if (flightRecord.getDate().getYear() < joinDate.getYear()) {
                joinDate = flightRecord.getDate();
            }
        }

        return joinDate;
    }


    /**
     * This method "rewardTier" takes in the sum of the miles from the method "currentYearSumMiles"
     * or "prevYearSumMiles" depending on the if-else statement in the main switch statement.
     * and runs through if statements, returning a String if one is true.
     * @return      A String which in this case is telling the user their tier, or
     * how many miles they need to reach the gold tier.
     * @param       sum
     */


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

    /**
     * This method "prevRewardTier" takes in the sum of the miles from the method "yearBeforeSumMiles"
     * and runs through if statements, returning a String if one is true.
     * @return      A String which in this case is telling the user their tier, or
     * telling them they aren't in a tier.
     * @param       sum
     */


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


    /**
     * This method "milesNeeded" is just a calculation method that takes in the "sum" variable that
     * either "rewardTier" or "prevRewardTier" took in, depending on the mthod that is getting called.
     * @return      The gold tier minus whatever sum is passed in.
     * @param       sum
     */


    public static int milesNeeded(int sum) {
        return (Rewards.gold - sum);
    }


    /**
     * This method "currentYearSumMiles" first iterates through the array list to find
     * the highest year, then iterates again to add all the miles the user flew in
     * that highest year.
     * @return      sum
     * @param       memberFlights array list.
     */


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


    /**
     * This method "prevYearSumMiles" first iterates through the array list to find
     * the highest year minus one year, then iterates again to add all the miles the user flew in
     * that year.
     * @return      sum
     * @param       memberFlights array list.
     */


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


    /**
     * This method "yearBeforeSumMiles" first iterates through the array list to find
     * the highest year minus two years, then iterates again to add all the miles the user flew in
     * that year.
     * @return      sum
     * @param       memberFlights array list.
     */


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


    /**
     * This method "displayRewards" is just a huge String type statement I made to prevent too much clutter
     * in my original switch statement.
     * @return      String
     */


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


    /**
     * This method "createAccount" gets the user's input by calling on other methods that act as input validation.
     * After getting the user's input, this calls and returns the FlightRecord class that assigns each input into the
     * list using the class.
     * @return  new FlightRecord cases inputDate, randomIdNumber, and userFlightMiles.
     */


    public static FlightRecord createAccount() {
        System.out.println("Please enter the date as (YYYY-MM-DD).");
        LocalDate inputDate = userInputDate();
        int randomIdNumber = memberIDGenerator();
        System.out.println("How many miles is your current flight?");
        int userFlightMiles = userInputInt();
        return new FlightRecord(inputDate,randomIdNumber,userFlightMiles);
    }


    /**
     * This method "UserInputInt" is just user input and input validation for the "createAccount" method.
     * @return      user input
     */


    public static int userInputInt(){
        while(true) {
            try {
                 return scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("Not a valid Integer.");
            }
        }
    }


    /**
     * This method "userInputDate" gets the date from the user and then
     * validates the input for the method "createAccount".
     * @return      The user inputted date.
     */


    public static LocalDate userInputDate(){
        while(true) {
            try {
                return LocalDate.parse(scanner.next());
            } catch (DateTimeParseException exception) {
                System.out.println("Not a valid date.");
            }
        }
    }


    /**
     * This method "memberIDGenerator" makes a random Member ID that
     * gets called by the "createAccount()" method.
     * @return      returns the new randomly generated memberID.
     */


    public static int memberIDGenerator(){
        Random newID = new Random();
        int upperbound = 1000;
        return newID.nextInt(upperbound);
    }


    /**
     * This method "displayNewMemberInfo" just is a print method so I don't crowd my switch statement too much.
     * This method takes in the user input date, the randomly generated Member ID, and the user inputted flight miles.
     * @return      String
     * @param       inputDate, randomIDNumber, userFlightMiles.
     */

    public static String displayNewMemberInfo(LocalDate inputDate, int randomIdNumber, int userFlightMiles) {
        return "Your new Member Id is: " + randomIdNumber +
                "\nCreated on: " + inputDate +
                "\nThat has " + userFlightMiles + "miles so far.\n";
    }


    /**
     * This method "inputFlightMiles" gets the user input and uses these inputs to make a new instance of the
     * FlightRecord Class, therefore updating the list with the miles flown by the user.
     * @return      New instance of the FlightRecord class.
     * @param       flightRecords array list.
     * @see         "Print Statements that tell the user what to enter or to enter a valid Member ID."
     */

    public static FlightRecord inputFlightMiles(List<FlightRecord> flightRecords) {
        LocalDate todayDate = null;
        System.out.println("Please enter your Member ID.");
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
        return new FlightRecord(todayDate, input, milesFlown);
    }


    /**
     * This method "memberIDFinder" is input validation for the Member ID prompt.
     * @return      The user input.
     * @param       flightRecords array list and input.
     */
    public static int memberIDFinder(List<FlightRecord> flightRecords, int input){
        for (final FlightRecord flightRecord : flightRecords) {
            if (input == flightRecord.getMemberID()) {
                return input;
            }
        }
        return 0;
    }
}