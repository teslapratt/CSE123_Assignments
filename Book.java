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

    public String getTitle(){
        return title;
    }

    public List<String> getArtists(){
        return new ArrayList<String>(authors);
    }

    public void addRating(int score){
        ratings.add(score);
    }

    public int getNumRatings(){
        return ratings.size();
    }

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


    public List<String> getContent(){
        while(content.hasNext()){
            bookContent.add(content.next());
        }

        return new ArrayList<String>(bookContent);
    }

    public String toString(){
        if(!ratings.isEmpty()){
            return title + " by " + authors + ": " + (double)Math.round(getAverageRating()*100)/100 + " (" + getNumRatings() + " ratings)";
        } else{
            return title + " by " + authors;
        }
    }

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