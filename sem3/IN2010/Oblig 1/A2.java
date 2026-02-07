import java.util.ArrayList;

class A2
{
    public static void main(String[] args)
    {

        // Making a test input array
        ArrayList<Integer> Input = new ArrayList<>();

        // Filling the array with all command line arguments
        for(String arg : args)
        {
            Input.add(Integer.valueOf(arg));
        }

        // We have failed to put something in the array... :(
        if(Input.isEmpty())
        {
            return;
        }

        // Instantiating an output array
        ArrayList<Integer> Output = new ArrayList<>();

        // Filling the output array
        fillList(Input, Output);

        // Printing relevant info
        System.out.println("\nList before sorting : " + Input);
        System.out.println("List after sorting : " + Output);
    }

    public static void fillList(ArrayList<Integer> input, ArrayList<Integer> output)
    {
        // Getting the number in the middle of the current input list, as well as it's index.
        Integer[] indexAndMedian = findMedian(input);
        Integer middleIndex = indexAndMedian[0];
        Integer median = indexAndMedian[1];

        // Adding the middle number to the output list
        output.add(median);

        // We dive into the right children of each node, then the left
        if(middleIndex < input.size() - 1)
        {
            // Collecting all numbers in the list greater than the median
            ArrayList<Integer> greater = new ArrayList<>();
            for(int i = middleIndex + 1; i < input.size(); i += 1)
            {
                greater.add(input.get(i));
            }
            // Calling this method recursively for the greater list
            fillList(greater,output);
        }
        if(middleIndex > 0)
        {
            // Collecting all numbers in the list lesser than the median
            ArrayList<Integer> lesser = new ArrayList<>();
            for(int i = 0; i < middleIndex; i += 1)
            {
                lesser.add(input.get(i));
            }

            // Calling this method recursively for the lesser list
            fillList(lesser,output);
        }

        // Since we send a pointer to an object through the recursive calls, we don't have to return anything
        // The object we declare before calling this method the first time has all the data we need :)
    }

    // Support method for finding the median number within a list.
    public static Integer[] findMedian(ArrayList<Integer> input)
    {
        // Finding the length of the current input list
        int length = input.size();
        // If there's only one element in the list, we simply return it.
        if(length <= 1)
        {
            return(new Integer[] {0,input.getFirst()});
        }

        // Finding the index of the middle element (we round up always, meaning that right elements get chosen first, leading to nodes at the bottom of the tree generally leaning left)
        int middleIndex = (int)Math.ceil(length/2); 
        Integer median = input.get(middleIndex);

        // returning the median along with it's index
        return(new Integer[] {middleIndex,median});
    }
}