package Placementprep.LLDs.practice;

import java.util.HashMap;

import Placementprep.LLDs.LRUCache;

public class LRUcache<K,V> {

    private Integer capacity;
    private HashMap<K,Node> map;
    Node head,tail;


    public class Node{
        K key;
        V value;
        Node next,prev;

        Node(K key,V value){
            this.key = key;
            this.value = value;
            this.next=null;
            this.prev=null;
        }
    }

    LRUcache(int capacity){
        this.map = new HashMap<>();
        this.capacity = capacity;

        head = new Node(null, null); // dummy head
        head = new Node(null, null); // dummy tail
        head.next = tail;
        tail.prev = head;
    }

    public V get(K key){
        Node node = map.get(key);
        if(node==null) return null;

        moveToHead(node);
        return node.value;
    }

    public void put(K key, V value){
        Node node = map.get(key);
        if(node!=null){
            node.value=value;
            moveToHead(node);
        }else{
            if(map.size()>=capacity){
                Node lrNode = tail.prev;
                removeNode(lrNode);
                map.remove(lrNode.key);
            }

            node = new Node(key, value);
            addNodeToHead(node);
            map.put(key, node);
        }
    }



    private void moveToHead(Node node){
        removeNode(node);
        addNodeToHead(node);
    }

    private void removeNode(Node node){
        node.prev.next=node.next;
        node.next.prev=node.prev;
    }

    private void addNodeToHead(Node node){
        node.next=head.next;
        node.prev=head;
        
        head.next=node;
        node.next.prev=node;
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
