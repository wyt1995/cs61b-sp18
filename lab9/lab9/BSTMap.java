package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            return p.value;
        } else if (cmp < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            p.value = value;
        } else if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Null key/value not allowed.");
        }
        this.root = putHelper(key, value, root);
        this.size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> setOfKeys = new HashSet<>();
        addToSet(root, setOfKeys);
        return setOfKeys;
    }

    private void addToSet(Node p, Set<K> setOfKeys) {
        if (p == null) {
            return;
        }
        addToSet(p.left, setOfKeys);
        setOfKeys.add(p.key);
        addToSet(p.right, setOfKeys);
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        V valueInMap = get(key);
        if (valueInMap == null) {
            return null;
        }
        this.root = removeHelper(key, root);
        this.size -= 1;
        return valueInMap;
    }

    private Node removeHelper(K key, Node p) {
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = removeHelper(key, p.left);
        } else if (cmp > 0) {
            p.right = removeHelper(key, p.right);
        } else {
            if (p.left == null && p.right == null) {
                return null;
            } else if (p.left == null) {
                return p.right;
            } else if (p.right == null) {
                return p.left;
            } else {
                Node maxKey = maxKey(p.left);
                maxKey.left = removeHelper(maxKey.key, p.left);
                maxKey.right = p.right;
                p = maxKey;
            }
        }
        return p;
    }

    private Node maxKey(Node p) {
        if (p.right == null) {
            return p;
        }
        return maxKey(p.right);
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value. Returns the VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key, V value) {
        V valueInMap = get(key);
        if (value.equals(valueInMap)) {
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return this.keySet().iterator();
    }
}
