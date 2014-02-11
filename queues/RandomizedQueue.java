import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int capacity = 1;
    private int size = 0;
    private int head;
    private int tail;
    
    public RandomizedQueue() { // construct an empty randomized queue
        q = (Item[]) new Object[capacity];
    }
    
    private void resize(int newcapacity) {
        Item[] newq = (Item[]) new Object[newcapacity];
        tail = 0;
        for (Item item : this)
            newq[tail++] = item;
        q = newq;
        capacity = newcapacity;
        head = 0;
    }
    
    public boolean isEmpty() { // is the queue empty?
        return size == 0;
    }
    
    public int size() { // return the number of items on the queue
        return size;
    }
    
    public void enqueue(Item item) { // add the item
        if (item == null)
            throw new NullPointerException();
        if (size + 1 == capacity)
            resize(2*capacity);
        size++;
        q[tail++] = item;
        tail %= capacity;
    }
    
    public Item dequeue() { // delete and return a random item
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        if (size <= capacity/4)
            resize(capacity/2);
        int randomIndex = (StdRandom.uniform(size) + head) % capacity;
        Item item = q[randomIndex];
        q[randomIndex] = q[head];
        q[head++] = null;
        head %= capacity;
        size--;
        return item;
    }
    
    private void swap(int i, int j) {
        Item temp = q[i];
        q[i] = q[j];
        q[j] = temp;
    }
    
    public Item sample() { // return (but do not delete) a random item
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int randomIndex = (StdRandom.uniform(size) + head) % capacity;
        return q[randomIndex];
    }
    
    private class RandomOrderIterator implements Iterator<Item> {
        private int current = 0;
        private int currentSize;
        private int currentCapacity;
        private int randomIndex;
        private Item[] tempq;
        
        public RandomOrderIterator() {
            currentCapacity = size;
            tempq = (Item[]) new Object[size];
            for (currentSize = 0; currentSize < size; currentSize++)
                tempq[currentSize] = q[(head + currentSize) % capacity];
        }
        
        public boolean hasNext() {
            return currentSize > 0;
        }
        
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            randomIndex = StdRandom.uniform(currentSize) + current;
            randomIndex %= currentCapacity;
            Item temp = tempq[randomIndex];
            tempq[randomIndex] = tempq[current];
            tempq[current] = temp;
            Item item = tempq[current++];
            current %= currentCapacity;
            currentSize--;
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    public Iterator<Item> iterator() {
        return new RandomOrderIterator();
    }
    
    public static void main(String[] args) { // unit testing
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        q.enqueue(6);
        
        for (int x : q) {
            System.out.print(x+": ");
            for (int y : q)
                System.out.print(y);
            System.out.println();
        }
    }
}