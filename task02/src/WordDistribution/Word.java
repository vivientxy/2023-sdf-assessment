package WordDistribution;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Word {
    private final String firstWord;
    private Map<String, Integer> secondWordCount = new HashMap<>();;
    // private Map<String, Integer> secondWordProbability = new HashMap<>();


    public Word(String firstWord) {
        this.firstWord = firstWord;
    }

    public Word(String firstWord, String secondWord) {
        this.firstWord = firstWord;
        this.secondWordCount.put(secondWord, 1);
    }

    public String getFirstWord() {
        return firstWord;
    }

    public Map<String, Integer> getSecondWordCount() {
        return secondWordCount;
    }

    // add second word count to a first word object
    public void addSecondWordCount(String secondWord) {
        if (this.secondWordCount.containsKey(secondWord)) {
            int count = this.secondWordCount.get(secondWord) + 1;
            this.secondWordCount.put(secondWord, count);
        } else {
            this.secondWordCount.put(secondWord, 1);
        }
    }

    // print probability of each 2nd word passed in
    public void printSecondWordProbability() {
        // get total count
        float totalCount = this.secondWordCount.values().stream().mapToInt(i->i).sum();
        System.out.println(this.firstWord);
        // print probability of each word
        for (var secondWord : this.secondWordCount.entrySet()) {
            System.out.println("\t" + secondWord.getKey() + " " + secondWord.getValue()/totalCount);
        }
    }

    // print word distribution
    public void printWordDistribution() {
        System.out.println(this.firstWord);
        // print probability of each word
        for (var secondWord : this.secondWordCount.entrySet()) {
            System.out.println("\t" + secondWord.getKey() + " " + secondWord.getValue());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Word)) {
            return false;
        }
        Word word = (Word) o;
        return Objects.equals(firstWord, word.firstWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstWord);
    }



}
