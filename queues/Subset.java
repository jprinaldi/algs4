public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String[] strings = StdIn.readAllStrings();
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        
        for (String s : strings)
            rq.enqueue(s);
        
        for (int i = 0; i < k; i++)
            System.out.println(rq.dequeue());
    }
}