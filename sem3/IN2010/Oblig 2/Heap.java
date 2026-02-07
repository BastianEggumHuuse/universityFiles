class Heap extends SortingAlgorithm
{
    // Overriding GetName from base class
    @Override
    String GetName()
    {
        return "heap";
    }

    // Overriding TrueSort from the base class.
    @Override
    int[] TrueSort (int[] A) 
    {   
        int n = A.length;
        BuildMaxHeap(A, n);

        for(int i = n - 1; Compare(i, 0) >= 0; i -= 1)
        {
            Swap(A,0,i);
            BubbleDown(A, 0, i);
        }

        return A;
    }

    // Method that turns a given array into a max-heap, ensuring that the first element
    // is the greatest element in the array at all times.
    int[] BuildMaxHeap(int[] A,int n)
    {
        for(int i = (int)Math.floor(n/2); Compare(i, 0) >= 0; i -= 1)
        {
            BubbleDown(A, i, n);
        }

        return A;
    }

    // Support method that takes a partially constructed heap A, with n elements, as well as the index of the heap we are currently working on,
    // and returns the heap, but "more constructed".
    int[] BubbleDown(int[] A, int i, int n)
    {
        // Storing the greatest value index, along with the indices of it's left and right children
        int greatest = i;
        int left =  2*i + 1;
        int right = 2*i + 2;

        // Here we check two things. Firstly we check if the left node exists (if the left node is lesser than n).
        // Then we check if the element in A at the greatest index, is lesser than the one at index left.
        // if this is the case, then left should be the greatest instead.
        if (Compare(left, n) < 0 && Compare(A[greatest], A[left]) < 0)
        {
            greatest = left;
        }
        // Same procedure here, but for the right child
        if (Compare(right, n) < 0 && Compare(A[greatest], A[right]) < 0)
        {
            greatest = right;
        }

        // If i is no longer the greatest (so if any of the above checks pass),
        // we swap these two.
        if(Equal(i, greatest) == false)
        {
            Swap(A,i,greatest);
            BubbleDown(A, greatest, n);
        }

        return A;
    }
}