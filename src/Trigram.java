import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class Trigram {
    private HashMap<String, HashSet<String>> trigramsOfEach = new HashMap<>();

    public Trigram(HashMap<String, HashSet<String>> trigramsOfEach ){
        this.trigramsOfEach = trigramsOfEach;
    }

    public String toString(){
        String st = "";
        for (String key : trigramsOfEach.keySet()) {
            st += key + " = " +  trigramsOfEach.get(key) + ",";
        }
        return st;
    }

    public ArrayList<String> trigramsOfWord(String word){
        ArrayList trigramsOfWord = new ArrayList();
        word = "<" + word +">";
        for(int i = 0; i < word.length() - 2; i++)
            trigramsOfWord.add(word.substring(i,i + 3));
        return trigramsOfWord;
    }

    public String correct( String word, ArrayList<String> mostCommons){
        int smallestDistance = Levenshtein.distanceLevenshtein(word, mostCommons.get(0));
        int indexOfSmallestDistance = 0;
        for(int i = 1; i < mostCommons.size(); i++) {
            if (Levenshtein.distanceLevenshtein(word, mostCommons.get(i)) < smallestDistance) {
                smallestDistance = Levenshtein.distanceLevenshtein(word, mostCommons.get(i));
                indexOfSmallestDistance = i;
            }
        }
        return mostCommons.get(indexOfSmallestDistance);
    }

    public ArrayList<String> mostCommons(String word) {
        ArrayList<String> mostCommons = new ArrayList<>();
        HashMap<String,Integer> count = new HashMap<>() ;
        for(String key : trigramsOfEach.keySet()) {
            ArrayList<String> common = new ArrayList<String>(trigramsOfWord(word));
            common.retainAll(trigramsOfEach.get(key));
            if(common.size() > 0) {
                count.put(key,common.size());
            }
        }
        Collections.sort(mostCommons);
        int i = 0;
        while (i < 5 && count.size() != 0) {
            String mostOccurrent = Collections.max(count.entrySet(),Comparator.comparingInt(Map.Entry::getValue)).getKey();
            mostCommons.add(mostOccurrent);
            count.remove(mostOccurrent);
            i++;
        }
        return mostCommons;
    }
}
