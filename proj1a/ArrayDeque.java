public class ArrayDeque<T> {
    /** Instance variables */
    private T[] items;
    private int size;
    private int arraySize;
    private int firstIndex;
    private final int startSize = 8;

    /** Constructor for an empty linked list deque. */
    public ArrayDeque() {
        this.items = (T[]) new Object[startSize];
        this.size = 0;
        this.arraySize = startSize;
        this.firstIndex = 0;
    }

    /**
     * Get the index in the underlying array of the previous item.
     * @param index the index of the current item.
     */
    private int prevIndex(int index) {
        int prev = index - 1;
        if (prev < 0) {
            prev += arraySize;
        }
        return prev;
    }

    /**
     * Get the index in the underlying array of the next item.
     * @param index the index of the current item.
     */
    private int nextIndex(int index) {
        int next = index + 1;
        if (next >= arraySize) {
            next -= arraySize;
        }
        return next;
    }

    /**
     * Establish the relation between deque index and array index.
     * @param dequeIndex the index in the array deque.
     * @return the index in the underlying array of the same item.
     */
    private int arrayIndex(int dequeIndex) {
        int index = dequeIndex + firstIndex;
        if (index < 0) {
            index += arraySize;
        } else if (index >= arraySize) {
            index -= arraySize;
        }
        return index;
    }

    /**
     * Return the index of the last item in the array.
     */
    private int lastIndex() {
        return arrayIndex(size - 1);
    }

    private double loadFactor() {
        return (double) size / arraySize;
    }

    private void resize(int capacity) {
        T[] resized = (T[]) new Object[capacity];
        int index = firstIndex;
        for (int i = 0; i < size; i++) {
            resized[i] = items[index];
            index = nextIndex(index);
        }
        this.items = resized;
        this.arraySize = capacity;
        this.firstIndex = 0;
    }

    /**
     * Add an item to the front of the deque.
     * @param item generic item of type T.
     */
    public void addFirst(T item) {
        if (size == arraySize) {
            resize(size * 2);
        }
        int index = prevIndex(firstIndex);
        items[index] = item;
        firstIndex = index;
        size += 1;
    }

    /**
     * Add an item to the back of the deque.
     * @param item generic item of type T.
     */
    public void addLast(T item) {
        if (size == arraySize) {
            resize(size * 2);
        }
        int index = nextIndex(lastIndex());
        items[index] = item;
        size += 1;
    }

    /**
     * @return true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return the number of items in the deque.
     */
    public int size() {
        return this.size;
    }

    /**
     * Prints all the items in the deque from first to last,
     * each separated by a space character.
     */
    public void printDeque() {
        StringBuilder deque = new StringBuilder();
        int end = nextIndex(lastIndex());
        for (int i = firstIndex; i != end; i = nextIndex(i)) {
            deque.append(items[i]);
            deque.append(" ");
        }
        System.out.println(deque.toString());
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T firstItem = items[firstIndex];
        items[firstIndex] = null;
        size -= 1;
        firstIndex = nextIndex(firstIndex);
        if (arraySize > startSize && loadFactor() < 0.25) {
            resize(size / 2);
        }
        return firstItem;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int lastIndex = lastIndex();
        T lastItem = items[lastIndex];
        items[lastIndex] = null;
        size -= 1;
        if (arraySize > startSize && loadFactor() < 0.25) {
            resize(size / 2);
        }
        return lastItem;
    }

    /**
     * Gets the item at the given index. If no such item exists, returns null.
     * @param index the index of the deque, where 0 is the front, 1 is the next, and so forth.
     * @return the item of type T.
     */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return items[arrayIndex(index)];
    }
}
