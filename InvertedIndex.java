// Tesla Pratt
// 01/12/2026
// CSE 123 
// C0: Search Engine
// TA: Elden Martial
// This class maps content to the location where the content can be found.
// In our case, we create a bank of a bunch of words and indicate which books
// are associated with that specific word. 

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

    //B: This method takes a list of media and creates a mapping from each word found
    //   within the media to the specific books/media that contain that word.
    //E: N/A
    //R: Returns information in a format that allows us to easily access all the media 
    //   that contain a given word.
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

}
