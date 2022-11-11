import com.sun.nio.sctp.AbstractNotificationHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class Trigram {
    private HashMap<String, ArrayList<String>> trigrams = new HashMap<>();

    public Trigram(HashMap<String, ArrayList<String>> trigrams ){
        this.trigrams = trigrams;
    }

    public String toString(){
        StringBuilder st = new StringBuilder();
        for (String key : trigrams.keySet()) {
            st.append(key).append(" = ").append(trigrams.get(key)).append(",");
        }
        return st.toString();
    }

    public ArrayList<String> trigramsOfWord(String word){
        ArrayList <String>trigramsOfWord = new ArrayList<>();
        word = "<" + word +">";
        for(int i = 0; i < word.length() - 2; i++)
            trigramsOfWord.add(word.substring(i,i + 3));
        return trigramsOfWord;
    }

    public ArrayList<String> mostCommons(String word) {
        HashMap<String,Integer> count = new HashMap<>();
        for(String trigram : trigramsOfWord(word)){
            try{
                for(String w :trigrams.get(trigram)) {
                    count.put(w, count.containsKey(w) ? count.get(w) + 1 : 1);
                }
            }catch (NullPointerException ignored){}
        }

        ArrayList<String> mostCommons = new ArrayList<>();
        int cmp = 0;
        while (cmp <= 100 && cmp < count.size()){
            String mostOccurring = count.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
            mostCommons.add(mostOccurring);
            count.remove(mostOccurring);
            cmp++;
        }
        return mostCommons;
    }

    public String correct( String word, ArrayList<String> mostCommons){
        String corrected = mostCommons.get(0);
        for(int i = 1; i < mostCommons.size(); i++) {
            if (Levenshtein.distanceLevenshtein(word,mostCommons.get(i)) < Levenshtein.distanceLevenshtein(word,corrected)) {
                corrected = mostCommons.get(i);
            }
        }
        System.out.print(mostCommons.subList(0,5) + " -> ");
        return corrected;
    }
}
