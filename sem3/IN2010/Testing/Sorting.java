class Sorting
{
    public static void main(String[] args) 
    {
        
        Integer[] TestArray = new Integer[] {80,91,7,33,50,70,13,321,12};

        TestArray = (Integer[])(QuickSort.Sort(TestArray,0,TestArray.length - 1));

        for (Integer i : TestArray)
        {
            System.out.println(i);
        }

    }
}

class BucketSort
{
    public static Comparable[] Sort(Comparable[] A)
    {
        return A;
    }

    
}

class QuickSort
{

    /* 
    
    Can have O(n^2) complexity, but is often more like O(nlog(n)).

    */

    public static Comparable[] Sort(Comparable[] A, int low, int high)
    {
        if(low >= high)
        {
            return A;
        }

        // Partitioning the array, and getting the pivot index
        int p = PartitionArray(A, low, high);
        // We want to sort everything to the left and to the right of the pivot index.
        // This is why p isn't in either quicksort. It is already sorted.
        Sort(A, low, p - 1);
        Sort(A, p + 1, high);

        return A;
    } 

    public static int PartitionArray(Comparable[] A, int low, int high)
    {
        int p = GetPivotIndex(low, high);

        // Swapping the highest element with the middle element
        Comparable temp = A[high];
        A[high] = A[p];
        A[p] = temp;

        Comparable pivot = A[high]; // Stashing away pivot element.
        int left = low;
        int right = high-1; // We don't want to interfere with the pivot element

        while (left < right) 
        {
            // Scanning from left to right
            while (left <= right && A[left].compareTo(pivot) <= 0)
            {
                left += 1;
            }
            // Scanning from right to left
            while (right >= left && A[right].compareTo(pivot) >= 0)
            {
                right -= 1;
            }

            // Swapping the two values, meaning the large ends up on the right,
            // and the small ends up on the left
            if(left < right)
            {
                temp = A[right];
                A[right] = A[left];
                A[left] = temp;
            }
        }

        // Finally, swapping left (which is now on the right of right, meaning A[left] is larger than pivot, with pivot)
        temp = A[high];
        A[high] = A[left];
        A[left] = temp;

        // We now have a partition of an array that has all values in left of pivot be lower than pivot, and all values right of pivot higher than pivot.
        // We then return left, which is the index we land on at the end. (when sorting further, we want to sort the values to the left and to the right of this value)
        return left;
    }

    public static int GetPivotIndex(int low, int high)
    {
        int pivot = (int)Math.floor(low + (high-low)/2);

        if(pivot > low && pivot < high)
        {
            return pivot;
        }

        return low;
    }
}