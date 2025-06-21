package Placementprep.LLDs;

import java.util.HashMap;

public class LRUCache<K, V> {
    private class Node {
        K key;
        V value;
        Node prev, next;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private final int capacity;
    private final HashMap<K, Node> map;
    private final Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();

        head = new Node(null, null); // dummy head
        tail = new Node(null, null); // dummy tail
        head.next = tail;
        tail.prev = head;
    }

    public V get(K key) {
        Node node = map.get(key);
        if (node == null) return null;

        moveToHead(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node node = map.get(key);

        if (node != null) {
            node.value = value;
            moveToHead(node);
        } else {
            if (map.size() == capacity) {
                Node lru = tail.prev;
                removeNode(lru);
                map.remove(lru.key);
            }

            Node newNode = new Node(key, value);
            map.put(key, newNode);
            addToHead(newNode);
        }
    }

    // Utility methods

    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addToHead(Node node) {
        node.next = head.next;
        head.next.prev = node;

        head.next = node;
        node.prev = head;
    }

    // Optional: for testing/debugging
    public void printCacheState() {
        Node curr = head.next;
        while (curr != tail) {
            System.out.print("[" + curr.key + "=" + curr.value + "] ");
            curr = curr.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LRUCache<Integer,String> lruCache = new LRUCache<>(4);
        lruCache.put(1, "sahil");
        lruCache.put(2, "sahil");
        lruCache.put(3, "sahil");
        lruCache.put(4, "sahil");
        lruCache.printCacheState();
        lruCache.put(5, "sahil");
        lruCache.printCacheState();
        System.out.println(lruCache.get(2));
        lruCache.printCacheState();
        lruCache.put(1, "sahil2");
        lruCache.put(1, "sahil3");
        lruCache.printCacheState();
        lruCache.put(1, "sahil");
        lruCache.printCacheState();
    }
}
