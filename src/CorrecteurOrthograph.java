import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class CorrecteurOrthograph {
    private  HashSet<String> dictionary = new HashSet<>();
    private static HashMap<String, ArrayList<String>> trigrams = new HashMap<>();


    public CorrecteurOrthograph(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            dictionary.add(st.toLowerCase(Locale.ROOT));
            st = "<" + st +">";

            for(int i = 0; i < st.length() - 2; i++) {
                if(!trigrams.containsKey(st.substring(i, i + 3))){
                    ArrayList<String> wordsWithTrigram = new ArrayList<>();
                    wordsWithTrigram.add(st);
                    trigrams.put(st.substring(i, i + 3),wordsWithTrigram);
                }else{
                    trigrams.get(st.substring(i, i + 3)).add(st);
                }
            }
        }
        br.close();
    }

    public String correct(String word){
        if(dictionary.contains(word))return word;
        Trigram trigram = new Trigram(trigrams);
        return trigram.correct(word, trigram.mostCommons(word));
    }

    public void correctAll(String text) throws IOException {
        File file = new File(text);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            System.out.println(st + " == " + correct(st));
        }
        br.close();
    }
}
