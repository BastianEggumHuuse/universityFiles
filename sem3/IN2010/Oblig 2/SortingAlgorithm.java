class SortingAlgorithm
{
    // All the sorting algorithms want to track number of swaps, number of comparisons,
    // and the number of milliseconds that the algorithm lasts
    // Therefore we keep track of this in a parent class that they all inherit from
    public long NumSwaps = 0;
    public long NumComps = 0;
    public long NumMicroSeconds = 0;
    // Also note that these numbers must be Longs! 
    // If kept as integers they overflow :)

    // We also override this method to get the name of the algorithm
    String GetName()
    {
        return "algorithm";
    }

    // All sorting algorithms can be called with this method, which also keeps track of time
    int[] Sort (int[] A)
    {
        long StartTime = System.nanoTime();
        int[] SortedArray = TrueSort(A);
        NumMicroSeconds = (System.nanoTime() - StartTime)/1000;

        return SortedArray;
    }

    // This method is overridden in the classes that inherit from this one.
    // This method is where the actual algorithm goes
    int[] TrueSort(int[] A)
    {
        return A;
    }

    // Another benefit of all the algorithms inheriting from this class is that we can define the comparison and swapping methods here!

    // The compare method returns a positive value if i1 > i2, and a negative if i1 < i2
    // If they are equal, the method returns 0!
    public int Compare(int i1, int i2)
    {
        NumComps += 1;

        return(i1 - i2);
    }
    
    public boolean Equal(int i1, int i2)
    {
        NumComps += 1;

        return(i1 == i2);
    }

    // Method that swaps two elements in an array, indexed by index_1 and index_2
    // This is the classic way of swapping elements, where the first is stored in a
    // temoporary variable.
    public void Swap(int[] A,int index_1, int index_2)
    {
        int temp = A[index_1];
        A[index_1] = A[index_2];
        A[index_2] = temp;

        NumSwaps += 1;
    }
}