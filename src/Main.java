import java.io.FileReader;
import java.io.IOException;
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
       for(FileObjects z : fileInfo){
           System.out.println(z.date);
           System.out.println(z.memberId);
           System.out.println(z.flightMiles);
       }
       return fileInfo;
    }


    public static void main(String[] args) throws IOException{

        System.out.println("Enter 1 to Search by Member ID.");
        System.out.println("Enter 2 to Learn More About Our Rewards!");
        System.out.println("Enter 0 to Exit this program.");
        int input = userInput();
        boolean i;
        do {

            switch (input) {
                case 0:
                    i = true;
                    break;
                case 1:
                    readFile();
                    i = true;
                    break;
                case 2:
                    System.out.println("Learn More About Our Rewards!");
                    i = true;
                    break;
                default:
                    System.out.println("Please enter a valid menu option.");
                    i = true;
                    break;
            }

        }while(!i);
    }
}