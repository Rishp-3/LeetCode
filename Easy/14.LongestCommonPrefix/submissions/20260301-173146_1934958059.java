class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        
        // Compare character by character across all strings
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            
            // Check if this character matches in all other strings
            for (int j = 1; j < strs.length; j++) {
                // If we've reached end of string or characters don't match
                if (i >= strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        
        // If we've gone through all characters, first string is the prefix
        return strs[0];
    }
    
    // Test cases
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        // Test 1
        String[] test1 = {"flower", "flow", "flight"};
        System.out.println(sol.longestCommonPrefix(test1)); // "fl"
        
        // Test 2
        String[] test2 = {"dog", "racecar", "car"};
        System.out.println(sol.longestCommonPrefix(test2)); // ""
        
        // Test 3
        String[] test3 = {"interspecies", "interstellar", "interstate"};
        System.out.println(sol.longestCommonPrefix(test3)); // "inters"
        
        // Test 4
        String[] test4 = {"a"};
        System.out.println(sol.longestCommonPrefix(test4)); // "a"
        
        // Test 5
        String[] test5 = {"ab", "a"};
        System.out.println(sol.longestCommonPrefix(test5)); // "a"
    }
}