class Insertion extends SortingAlgorithm
{
    // Overriding GetName from base class
    @Override
    String GetName()
    {
        return "insertion";
    }

    // Overriding TrueSort from the base class.
    @Override
    int[] TrueSort (int[] A) 
    {
        
        for (int i = 1;Compare(i,A.length - 1) <= 0; i++) {
            
            int j = i;

            while (Compare(j,0) > 0 && Compare(A[j-1] ,A[j]) > 0)
            {
                Swap(A,j,j - 1);

                j = j - 1;
            }
        }
        return A;
    }
}