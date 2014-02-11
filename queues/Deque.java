import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first, last;
    
    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }
    
    public Deque() { // construct an empty deque
        size = 0;
        first = null;
        last = null;
    }
    
    public boolean isEmpty() { // is the deque empty?
        return size == 0;
    }
    
    public int size() { // return the number of items on the deque
        return size;
    }
        
    public void addFirst(Item item) { // insert the item at the front
        if (item == null)
            throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldfirst;
        if (isEmpty())
            last = first;
        else
            oldfirst.prev = first;
        size++;
    }
    
    public void addLast(Item item) { // insert the item at the end
        if (item == null)
            throw new NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        last.next = null;
        if (isEmpty())
            first = last;
        else
            oldlast.next = last;
        size++;
    }

    public Item removeFirst() { // delete and return the item at the front
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty())
            last = null;
        else
            first.prev = null;
        return item;
    }

    public Item removeLast() { // delete and return the item at the end
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        size--;
        if (isEmpty())
            first = null;
        else
            last.next = null;
        return item;
    }
        
    public Iterator<Item> iterator() {
        return new FirstToLastIterator();
    }
    
    private class FirstToLastIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            if (current == null)
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
    
    public static void main(String[] args) { // unit testing
        Deque<Integer> d = new Deque<Integer>();
        d.addLast(1);
        d.addLast(2);
        d.addFirst(3);
        Iterator<Integer> it = d.iterator();
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.hasNext());
    }
}