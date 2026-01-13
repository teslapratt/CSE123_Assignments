// Tesla Pratt
// 01/12/2026
// CSE 123 
// C0: Search Engine
// TA: Elden Martial
// This class is the book class which allows the user to create a
// book with a title, authors, and rating. The user can also get the
// book's average rating, total number of ratings, and the book's content.
// Additionally, books can be compared to other books using the average rating
// to determine which book is "better".

import java.util.*;

public class Book implements Media, Comparable<Book>{
    
    private String title;
    private List<String> authors;
    private List<Integer> ratings;

    private Scanner content;

    private List<String> bookContent;

    public Book(String title, List<String> authors, Scanner content){
        this.title = title;
        this.ratings = new ArrayList<>();
        this.bookContent = new ArrayList<>();
        this.authors = new ArrayList<>(authors);
        this.content = content;
    }

    //B: Allows user to access the book's title
    //E: N/A
    //R: Returns the book title
    //P: N/A
    public String getTitle(){
        return title;
    }

    //B: Allows user to access the book's author/authors
    //E: N/A
    //R: Returns the book author/authors
    //P: N/A
    public List<String> getArtists(){
        return new ArrayList<String>(authors);
    }

    //B: Allows user to add an integer score "rating" to a book
    //E: N/A
    //R: N/A
    //P: N/A
    public void addRating(int score){
        ratings.add(score);
    }

    //B: Allows user to get the number of ratings a book has
    //E: N/A
    //R: Returns the number of ratings the book has
    //P: N/A
    public int getNumRatings(){
        return ratings.size();
    }

    //B: Allows user to get the book's average rating
    //E: N/A
    //R: Returns a decimal value of the book's average rating
    //P: N/A
    public double getAverageRating(){
        double total = 0;
        if(ratings.size() == 0){
            return 0;
        }
        
        for(int i=0; i<ratings.size(); i++){
            total += ratings.get(i);
        }

        return total/ratings.size();
    }


    //B: Allows user to get the contents of the book
    //E: N/A
    //R: Returns the book content
    //P: N/A
    public List<String> getContent(){
        while(content.hasNext()){
            bookContent.add(content.next());
        }

        return new ArrayList<String>(bookContent);
    }

    //B: Converts all information about the book into an easy to read format
    //E: N/A
    //R: Returns string containing book's title, author/authors, average rating,
    //   and number of ratings.
    //P: N/A
    public String toString(){
        if(!ratings.isEmpty()){
            return title + " by " + authors + ": " + (double)Math.round(getAverageRating()*100)/100 + " (" + getNumRatings() + " ratings)";
        } else{
            return title + " by " + authors;
        }
    }

    //B: Allows user to compare books to each other depending on which has a 
    //   higher average rating.
    //E: N/A
    //R: Returns -1 if the current book is worse than other, positive one if the current book
    //   is greater than the other, and zero if the two books have the same average rating.
    //P: N/A
    public int compareTo(Book other){
        if(other.getAverageRating() > getAverageRating()){
            return -1;
        } else if(other.getAverageRating() < getAverageRating()){
            return 1;
        } else{
            return 0;
        }
    }

}