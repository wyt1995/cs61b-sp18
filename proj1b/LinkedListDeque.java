public class LinkedListDeque<T> implements Deque<T> {
    /**
     * A helper class that represents a node in the linked list.
     * A node has an item, as well as previous and next pointers.
     */
    private class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    /** Instance variables */
    private Node sentinel;
    private int size;

    /** Constructor for an empty linked list deque. */
    public LinkedListDeque() {
        this.sentinel = new Node(null, null, null);
        this.sentinel.prev = this.sentinel;
        this.sentinel.next = this.sentinel;
        this.size = 0;
    }

    /**
     * Add an item to the front of the deque.
     * @param item generic item of type T.
     */
    public void addFirst(T item) {
        Node firstItem = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = firstItem;
        sentinel.next = firstItem;
        size += 1;
    }

    /**
     * Add an item to the back of the deque.
     * @param item generic item of type T.
     */
    public void addLast(T item) {
        Node lastItem = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = lastItem;
        sentinel.prev = lastItem;
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
        Node ptr = sentinel.next;
        while (ptr != sentinel) {
            deque.append(ptr.item);
            deque.append(" ");
            ptr = ptr.next;
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
        T firstItem = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
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
        T lastItem = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return lastItem;
    }

    /**
     * Gets the item at the given index. If no such item exists, returns null.
     * @param index the index of the deque, where 0 is the front, 1 is the next, and so forth.
     * @return the item of type T.
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node ptr = sentinel.next;
        while (index > 0) {
            ptr = ptr.next;
            index -= 1;
        }
        return ptr.item;
    }

    /**
     * A recursive solution of the get method.
     */
    public T getRecursive(int index) {
        return getHelper(index, sentinel.next);
    }

    private T getHelper(int index, Node ptr) {
        if (index == 0) {
            return ptr.item;
        }
        return getHelper(index - 1, ptr.next);
    }
}
