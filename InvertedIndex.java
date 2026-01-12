import java.util.*;

public class InvertedIndex {
    public static void main(String[] args) {
        List<Media> docs = List.of(
            new Book("Mistborn", List.of("Brandon Sanderson"),
                     new Scanner("Epic fantasy worldbuildling content")),
            new Book("Fahrenheit 451", List.of("Ray Bradbury"),
                     new Scanner("Realistic \"sci-fi\" content")),
            new Book("The Hobbit", List.of("J.R.R. Tolkein"),
                     new Scanner("Epic fantasy quest content"))
        );
        
        Map<String, Set<Media>> result = createIndex(docs);
        System.out.println(docs);
        System.out.println();
        System.out.println(result);
    }

    // TODO: Write and document your createIndex method here
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

}
