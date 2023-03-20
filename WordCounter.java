import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;


public class WordCounter{

    StringBuffer text;
    static String stopWord;
    

    public static int processText(StringBuffer text, String stopWord) throws InvalidStopwordException, TooSmallText{
        int count = 0;
        String[] words = text.toString().split("[\\s+--]+");
        if (stopWord == null){
            count = words.length;
            if (count<5){
                count += 1;
                throw new TooSmallText("Only found " + count + " words.");
            }
            else{
                return count;
            }
        }
        if (words.length<5){
            throw new TooSmallText("Only found " + words.length + " words.");
        }
        else{
            int index = text.indexOf(stopWord);
            if (index >= 0 ){
                for (String word:words){
                    if (word.equals(stopWord)){
                        count++;
                        break;
                    }
                    else{
                        count++;
                    }
                }
                if (count<5){
                    count = words.length;
                    throw new TooSmallText("Only found " + count + " words.");
                }
            }
            else{
                throw new InvalidStopwordException("Couldn't find stopword: " + stopWord);
            }
        }
        return count;
    }

    public static StringBuffer processFile (String path) throws EmptyFileException, FileNotFoundException{
        StringBuffer buffer = new StringBuffer();
        File file = new File(path);
        Scanner sc = new Scanner(System.in);
        while (!file.exists()){
            System.out.println("File not found. Please enter a valid file.");
            path = sc.nextLine();
            file = new File(path);
        }
        if (file.length() ==  0){
            throw new EmptyFileException(file + " was empty");
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();

            while (line != null){
                buffer.append(line);
                line = reader.readLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return buffer;
    }

    public static void main (String[] args){
        StringBuffer text = new StringBuffer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Input 1 to process a file or 2 to process text");
        int input = sc.nextInt();
        if (input != 1 && input != 2){
            System.out.println("Invalid Input. Try again");
            System.out.println("Input 1 to process a file or 2 to process text");
            input = sc.nextInt();
        }
        if (input == 1){
            try{
                System.out.println("Please enter file path:");
                String path = sc.nextLine();
                text = processFile(path);
            }
            catch (EmptyFileException e){
                System.out.println(e.getMessage());
            }
            catch (FileNotFoundException e){
                System.out.println("File not found. Try again.");
            }
        }
        else if (input == 2){
            System.out.println("Please enter the text:");
            text.append(sc.nextLine());
        }
        else{
            System.out.println("Invalid option. Try again.");
        }

        try{
            System.out.println("Word count: " + processText(text, stopWord));
        }
        catch (InvalidStopwordException e){
            System.out.println(e.getMessage());
            System.out.println("Enter a new stopword: ");
            stopWord = sc.nextLine();
        }
        catch (TooSmallText e){
            System.out.println(e.getMessage());
        }
    }
}