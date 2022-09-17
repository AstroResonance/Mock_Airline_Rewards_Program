import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // userInput function that gathers the user input and returns it to where it's used.
    public static int userInput() {
        Scanner object = new Scanner(System.in);
        int userOption = object.nextInt();
        return userOption;
    }


    public static class FileObjects {
        String date;
        String memberId;
        String flightMiles;
    }


    public static void main(String[] args) throws IOException{
        List<FileObjects> fileInfo = readFile();
        System.out.println("Enter 1 to Search by Member ID.");
        System.out.println("Enter 2 to Learn More About Our Rewards!");
        System.out.println("Enter 0 to Exit this program.");
        int input = userInput();
        while(true) {

            switch (input) {
                case 0:
                    break;
                case 1:
                    searchFunction(fileInfo);
                    break;
                case 2:
                    System.out.println("Learn More About Our Rewards!");
                    break;
                default:
                    System.out.println("Please enter a valid menu option.");
                    break;
            }

        }
    }

    public static List<FileObjects> readFile() throws IOException {
        List<FileObjects> fileInfo = new ArrayList<>();
        Scanner scnr = new Scanner(new FileReader("inputFile.txt"));

        while(scnr.hasNextLine()){
            FileObjects fileObjects = new FileObjects();
            fileObjects.date = scnr.next();
            fileObjects.memberId = scnr.next();
            fileObjects.flightMiles = scnr.next();
            fileInfo.add(fileObjects);
        }
        return fileInfo;
    }

    public static void searchFunction(List<FileObjects> fileInfo){
        System.out.println("Please enter your Member ID.");
        Scanner id = new Scanner(System.in);
        int memberId = id.nextInt();
        
    }
}