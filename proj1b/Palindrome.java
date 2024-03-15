public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        int strlen = word.length();
        for (int i = 0; i < strlen; i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        return isPalindrome(wordToDeque(word));
    }

    private boolean isPalindrome(Deque<Character> chars) {
        if (chars.isEmpty() || chars.size() == 1) {
            return true;
        }
        char first = chars.removeFirst();
        char last = chars.removeLast();
        return first == last && isPalindrome(chars);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindrome(wordToDeque(word), cc);
    }

    private boolean isPalindrome(Deque<Character> chars, CharacterComparator cc) {
        if (chars.isEmpty() || chars.size() == 1) {
            return true;
        }
        char first = chars.removeFirst();
        char last = chars.removeLast();
        return cc.equalChars(first, last) && isPalindrome(chars, cc);
    }
}
