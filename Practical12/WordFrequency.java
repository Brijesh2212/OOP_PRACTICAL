import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordFrequency {
    public static void main(String[] args) {
        String paragraph = "java is versatile and java is powerful java is popular";
        String[] words = paragraph.toLowerCase().split("\\s+");

        Map<String, Integer> freqMap = new HashMap<>();
        for (String w : words) {
            freqMap.put(w, freqMap.getOrDefault(w, 0) + 1);
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(freqMap.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        System.out.println("Word frequencies (highest first):");
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
