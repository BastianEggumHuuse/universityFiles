public class Quick extends  SortingAlgorithm
{

    // Overriding GetName from base class
    @Override
    String GetName()
    {
        return "quick";
    }

    // Overriding TrueSort from the base class.
    // Since this class also needs to take in other parameters, we have an overload that takes in nothing
    // The parameters for TrueSort are the same regardless of the array, as we simply want both ends of the array (in indexes)
    @Override
    public int[] TrueSort(int[] A)
    {
        return TrueSort(A,0,A.length-1);
    }

    // This then, is the actual sorting algorithm
    public int[] TrueSort(int[] A, int low, int high)
    {
        /**
        Sorting algorithm that works by creating a pivot element, and then moving all elements higher than the pivot to the right of it,
        and all elements lower than the pivot to the left of it. This then gives us two new arrays (the elements to the left, and the elements to the right),
        which we call the algorithm on recursively.
        */

        if(Compare(low, high) >= 0)
        {
            return A;
        }

        // Partitioning the array, and returning the pivot index
        int p = PartitionArray(A, low, high);

        // Running the method recursively on the elements lesser, and then greater, than the pivot
        // Note that the pivot element itself is excluded, since it's already in it's correct place!
        TrueSort(A, low, p - 1);
        TrueSort(A, p + 1, high);

        return A;
    }

    public int PartitionArray(int[] A, int low, int high)
    {
        int p = GetPivotIndex(A, low, high);

        // Swapping pivot element and highest element
        Swap(A,p,high);

        // Initializing left and right, pointers to the left and right ends of the array.
        int pivot = A[high]; // Collecting the pivot element
        int left = low;
        int right = high - 1; // We use -1 here to avoid the now stashed away pivot element

        // The goal of this method is to scan right from the left, and left from the right, until both sides have found an element that is on the wrong side
        // (Being on the wrong side means being greater than pivot if on the left, and being lesser than pivot if on the right)
        // Once both sides have found an element, they swap the element's places.
        // This ensures that the elements on the left are lesser than pivot, and the elements on the right are further than pivot.
        while (Compare(left, right) <= 0)
        {

            // Scanning from the left to the right (Adding 1 to left until it finds an element on the wrong side)
            while(Compare(left, right) <= 0 && Compare(A[left],pivot) <= 0)
            {
                left += 1;
            }
            // Scanning from the right to the left (Subtracting 1 from right until it finds an element on the wrong side)
            while(Compare(right, left) >= 0 && Compare(A[right],pivot ) >= 0)
            {
                right -= 1;
            }

            // As long as left is still on the left, and right is still on the right, 
            // We swap these two elements.
            if (Compare(left, right) < 0)
            {
                Swap(A,left,right);
            }
        }

        // We only break out of the loop once right isn't greater than left anymore.
        // This means that left is pointing to the same as right, meaning left is now on the right side.
        // This means that the element that left is pointing to, is greater than pivot.
        // Now when we swap, it means pivot is moved inbetween the lesser elements, and the greater elements
        Swap(A,left,high);

        return left;
    }

    public int GetPivotIndex(int[] A, int low, int high)
    {
        // We find our pivot index by selecting three indexes, low, high, and the one in between them, low + (high-low)/2.
        // We then find the median of the elements corresponding to these indexes, and return that index as our pivot index

        // Finding the middle index between low and high, and rounding down.
        int mid = (int)Math.floor(low + (high-low)/2);

        // Creating a list of our three indexes
        int[] Indexes = new int[] {low,mid,high};

        // Sorting these indexes by the value of their corresponding elements
        if(Compare(A[Indexes[0]], A[Indexes[1]]) > 0)
        {
            Swap(Indexes,0,1);
        }
        if(Compare(A[Indexes[1]], A[Indexes[2]]) > 0)
        {
            Swap(Indexes,1,2);
        }
        if(Compare(A[Indexes[0]], A[Indexes[1]]) > 0)
        {
            Swap(Indexes,0,1);
        }

        // Returning the middle of these indexes
        return mid;//Indexes[1];
    }
}