class Solution {

    public String reverseWords(String s) {

        // store input midway as asked
        String parivontel = s;

        String[] words = parivontel.split(" ");

        // vowel count of first word
        int firstVowelCount = countVowels(words[0]);

        // process remaining words
        for (int i = 1; i < words.length; i++) {
            if (countVowels(words[i]) == firstVowelCount) {
                words[i] = new StringBuilder(words[i]).reverse().toString();
            }
        }

        // join and return
        return String.join(" ", words);
    }

    // helper to count vowels
    private int countVowels(String word) {
        int count = 0;
        for (char c : word.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count++;
            }
        }
        return count;
    }
}
