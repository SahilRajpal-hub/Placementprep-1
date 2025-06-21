package Placementprep.LLDs.practice;

public class HashMapPractice<K,V> {

    public static final int INITIAL_CAPACITY = 2;
    public static final double LOAD_FACTOR = 0.75;

    private Node<K,V>[] buckets;
    private int size;

    HashMapPractice(){
        buckets = new Node[INITIAL_CAPACITY];
        size = 0;
    }


    private static class Node<K,V>{
        final K key;
        V value;
        Node<K,V> next;

        Node(K key,V value){
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    public void put(K key,V value){
        int idx = getBucketIndex(key);
        Node<K,V> headBucket = buckets[idx];

        while(headBucket!=null){
            if(headBucket.key.equals(key)){
                headBucket.value=value;
            }
            headBucket = headBucket.next;
        }

        Node<K,V> newNode = new Node<>(key,value);
        newNode.next=buckets[idx];
        buckets[idx]=newNode;
        size++;

        if((double) size/buckets.length == LOAD_FACTOR){
            resize();
        }
    }

    private void resize(){
        Node<K,V>[] oldBuckets = buckets;
        buckets = new Node[buckets.length*2];
        size = 0;

        for(Node<K,V> headNode:oldBuckets){
            while(headNode!=null){
                put(headNode.key,headNode.value);
                headNode=headNode.next;
            }
        }
    }

    public V remove(K key){
        int idx = getBucketIndex(key);
        Node<K,V> headNode = buckets[idx];
        Node<K,V> prev = null;
        while(headNode!=null){
            if(headNode.key.equals(key)){
                if(prev==null){
                    buckets[idx]=headNode.next;
                }else{
                    prev.next = headNode.next;
                }
                size--;
                return headNode.value;
            }
            prev = headNode;
            headNode = headNode.next;
        }

        return null;
    }

    public V get(K key){
        int idx = getBucketIndex(key);
        Node<K,V> headBucket = buckets[idx];

        while(headBucket!=null){
            if(headBucket.key.equals(key)){
                return headBucket.value;
            }
            headBucket = headBucket.next;
        }

        return null;
    }


    private int getBucketIndex(K key){
        return key.hashCode()%buckets.length;
    }

    public int size(){
        return size;
    }

    public static void main(String[] args) {
        HashMapPractice<String,String> mp = new HashMapPractice<>();
        mp.put("test1", "sahil1");
        mp.put("test2", "sahil2");
        mp.put("test3", "sahil3");
        mp.put("test4", "sahil4");
        mp.put("test5", "sahil5");
        mp.put("test6", "sahil6");
        System.out.println(mp.get("test1"));
        System.out.println(mp.get("test2"));
        System.out.println(mp.get("test3"));
        System.out.println(mp.get("test4"));
        System.out.println(mp.get("test5"));
        System.out.println(mp.get("test6"));
    }
    
}
