public class OffByN implements CharacterComparator {
    private final int offset;

    public OffByN(int N) {
        offset = N;
    }

    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == offset;
    }
}
