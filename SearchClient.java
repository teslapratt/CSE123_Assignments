// Tesla Pratt
// 01/12/2026
// CSE 123 
// C0: Search Engine
// TA: Elden Martial
// This class allows users to find and rate books within BOOK_DIRECTORY
// containing certain terms

import java.io.*;
import java.util.*;

public class SearchClient {
    public static final String BOOK_DIRECTORY = "./books";
    private static final Random RAND = new Random();

    // Some class constants you can play around with to give random ratings to the uploaded books!
    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 5;
    public static final int MIN_NUM_RATINGS = 1;
    public static final int MAX_NUM_RATINGS = 100;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        List<Media> media = new ArrayList<>(loadBooks());

        Map<String, Set<Media>> index = createIndex(media);

        System.out.println("Welcome to the CSE 123 Search Engine!");
        String command = "";
        while (!command.equalsIgnoreCase("quit")) {
            System.out.println("What would you like to do? [Search, Rate, Quit]");
            System.out.print("> ");
            command = console.nextLine();

            if (command.equalsIgnoreCase("search")) {
                searchQuery(console, index);
            } else if (command.equalsIgnoreCase("rate")) {
                addRating(console, media);
            } else if (!command.equalsIgnoreCase("quit")) {
                System.out.println("Invalid command, please try again.");
            }
        }
        System.out.println("See you next time!");
    }

    //B: This method takes a list of media and creates a mapping from each word found
    //   within the media to the specific books/media that contain that word.
    //E: N/A
    //R: Returns sorted information that allows us to easily access all the media that 
    //   contain a given word.
    //P: docs: a list of all the media we want to review and organize into groups by certain 
    //         contained words within the media.
    public static Map<String, Set<Media>> createIndex(List<Media> docs) {
        //get content from each book in the list of media. check if the word exists in the map,
        //if it does exist in the map, add the book as a value for the word which is the key
        Map<String, Set<Media>> result = new TreeMap<>();

        //iterate through each book in the list
        for(int i=0; i<docs.size(); i++){
            //iterate through each word of the book's content
            Media currMedia = docs.get(i);
            List<String> currLine = currMedia.getContent();

            for(int j=0; j<currLine.size(); j++){
                String word = currLine.get(j).toLowerCase();
                
                if(!result.containsKey(word)){
                    Set<Media> currSet = new HashSet<Media>();
                    currSet.add(currMedia);
                    result.put(word, currSet);
                } else{
                    result.get(word).add(currMedia);
                }
            }
        }

        return result;
    }

    //B: Allows users to input a search query that will run through all the media
    //   in a given group of data, and return a set of all the media that the search is
    //   is relevent to. 
    //E: N/A
    //R: Returns a set of media that the search is applicable to
    //P: index - sorted information that contains media sorted into sets based on what words
    //           they contain
    //   query - a string inputted by the user that indicates what they want to search
    //           the index mapping for
    public static Set<Media> search(Map<String, Set<Media>> index, String query) {
        query = query.toLowerCase();
        if(index.containsKey(query)){
            return new TreeSet<>(index.get(query));
        }

        return new TreeSet<>();
    }
    
    // Allows the user to search a specific query using the provided 'index' to find appropriate
    //  Media entries.
    //
    // Parameters:
    //   console - the Scanner to get user input from. Should be non-null
    //   index - an inverted index mapping terms to the Set of media containing those terms.
    //           Should be non-null
    public static void searchQuery(Scanner console, Map<String, Set<Media>> index) {
        System.out.println("Enter query:");
        System.out.print("> ");
        String query = console.nextLine();

        Set<Media> result = search(index, query);
        
        if (result.isEmpty()) {
            System.out.println("\tNo results!");
        } else {
            for (Media m : result) {
                System.out.println("\t" + m.toString());
            }
        }
    }

    // Allows the user to add a rating to one of the options wthin 'media'
    //
    // Parameters:
    //   console - the Scanner to get user input from. Should be non-null.
    //   media - list of all media options loaded into the search engine. Should be non-null.
    public static void addRating(Scanner console, List<Media> media) {
        for (int i = 0; i < media.size(); i++) {
            System.out.println("\t" + i + ": " + media.get(i).toString());
        }
        System.out.println("What would you like to rate (enter index)?");
        System.out.print("> ");
        int choice = Integer.parseInt(console.nextLine());
        if (choice < 0 || choice >= media.size()) {
            System.out.println("Invalid choice");
        } else {
            System.out.println("Rating [" + media.get(choice).getTitle() + "]");
            System.out.println("What rating would you give?");
            System.out.print("> ");
            int rating = Integer.parseInt(console.nextLine());
            media.get(choice).addRating(rating);
        }
    }

    // Loads all books from BOOK_DIRECTORY. Assumes that each book starts with two lines -
    //      "Title: " which is followed by the book's title
    //      "Author: " which is followed by the book's author
    // Exceptions:
    //   FileNotFoundException - if BOOK_DIRECTORY does not exist or is not a directory
    // Returns:
    //   A list of all book objects corresponding to the ones located in BOOK_DIRECTORY
    public static List<Media> loadBooks() throws FileNotFoundException {
        List<Media> ret = new ArrayList<>();
        
        File dir = new File(BOOK_DIRECTORY);
        for (File f : dir.listFiles()) {
            Scanner sc = new Scanner(f, "utf-8");
            String title = sc.nextLine().substring("Title: ".length());
            List<String> author = List.of(sc.nextLine().substring("Author: ".length()));

            Media book = new Book(title, author, sc);

            // Adds random ratings to 'book' based on the class constants. 
            // Feel free to comment this out.
            int minRating = RAND.nextInt(MAX_RATING - MIN_RATING + 1) + MIN_RATING;
            addRatings(minRating, 
                       Math.min(MAX_RATING,RAND.nextInt(MAX_RATING - minRating + 1) + minRating),
                       RAND.nextInt(MAX_NUM_RATINGS - MIN_NUM_RATINGS) + MIN_NUM_RATINGS, book);
            ret.add(book);
        }

        return ret;
    }

    // Adds ratings to the provided media numRatings amount of times. Each rating is a random int
    // between minRating and maxRating (inclusive).
    private static void addRatings(int minRating, int maxRating, int numRatings, Media media) {
        for (int i = 0; i < numRatings; i++) {
            media.addRating(RAND.nextInt(maxRating - minRating + 1) + minRating);
        }
    }
}

