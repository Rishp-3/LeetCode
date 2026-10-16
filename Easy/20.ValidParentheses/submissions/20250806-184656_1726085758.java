class Solution {
  public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    // mapping closing -> opening bracket
    Map<Character, Character> map = new HashMap<>();
    map.put(')', '(');
    map.put('}', '{');
    map.put(']', '[');

    for (char c : s.toCharArray()) {
      if (map.containsKey(c)) {
        if (stack.isEmpty() || stack.pop() != map.get(c)) {
          return false;
        }
      } else {
        stack.push(c);
      }
    }
    return stack.isEmpty();
  }
}
