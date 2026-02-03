import java.util.PriorityQueue;

class B2
{
    public static void main(String[] args)
    {
        // Making a test input queue
        PriorityQueue<Integer> Input = new PriorityQueue<>();

        // Filling the queue with all command line arguments
        for(String arg : args)
        {
            Input.offer(Integer.valueOf(arg));
        }

        // We have failed to put something in the queue... :(
        if(Input.isEmpty())
        {
            return;
        }

        // printing the items in the order we want (recursively)
        printItems(Input);
    }

    public static void printItems(PriorityQueue<Integer> input)
    {
        // We need to handle some edge cases:
        // there is one element left in the input queue, in which case we just print that element
        // the input queue has an even size, meaning it can't be split evenly with one element in the middle.
        // in this case we split it unevenly, letting one of the offshoot queues be one bigger than the other.

        // There is only one element left. Simply print it
        if(input.size() == 1)
        {
            System.out.println(input.poll());
            return;
        }

        // Making two queues. We essentially want to move elements from one to the other until their lengths match.
        PriorityQueue<Integer> input1 = new PriorityQueue<>(input);
        PriorityQueue<Integer> input2 = new PriorityQueue<>();
        Integer element = 0; // Keeping track of the current moving element

        // Moving elements from input1 to input2, breaking if their lengths are the same
        while(input1.size() != input2.size())
        {
            element = input1.poll();
            if(input1.size() == input2.size())
            {
                break;
            }
            input2.offer(element);
        }

        // If we have an even size on the input layer, we pull an extra element, which will become the parent of all the other elements.
        // This means one of the queues will have one less element in it.
        if((input.size() % 2) == 0.0)
        {
            element = input1.poll();
        }

        // printing the element
        System.out.println(element);

        // If there are elements left to look at, print them recursively.
        if(!input1.isEmpty())
        {
            printItems(input1);
        }
        if(!input2.isEmpty())
        {
            printItems(input2);
        }
    }
}