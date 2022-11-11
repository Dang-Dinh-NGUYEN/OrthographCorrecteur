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
        String st = "";
        for (String key : trigrams.keySet()) {
            st += key + " = " +  trigrams.get(key) + ",";
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

    public ArrayList<String> mostCommons(String word) {
        ArrayList<String> wordsWithCommonTrigrams = new ArrayList<>();
        HashMap<String,Integer> count = new HashMap<>();

        for(String trigram : trigramsOfWord(word)){
            try{
                for(String w :trigrams.get(trigram)) {
                    if (!count.containsKey(w)) {
                        count.put(w,1);
                    } else {
                        count.put(w, count.get(w) + 1);
                    }
                }
                wordsWithCommonTrigrams.addAll(trigrams.get(trigram));
            }catch (NullPointerException e){ continue;}
        }

        ArrayList<String> mostCommons = new ArrayList<>();
        int cmp = 0;
        while (cmp <= 100 && cmp < count.size()){
            mostCommons.add(count.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey());
            count.remove(count.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey());
            cmp++;
        }

        System.out.print(mostCommons.subList(0,5) + " -> ");
        return mostCommons;
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
}
