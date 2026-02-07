class Merge extends SortingAlgorithm 
{
    // Overriding GetName from base class
    @Override
    String GetName()
    {
        return "merge";
    }

    @Override
    int[] TrueSort (int[] A){

        // Length of array
        int n = A.length;

        // Only one element remains, so we simply return the input
        if (Compare(n,1) <= 0) 
        {
            return A;
        }

        int i = (int)Math.floor(n/2);
 
        int[] A1 = TrueSort(CopyArray(A, 0, i));
        int[] A2 = TrueSort(CopyArray(A, i, n));

        return Merge(A1, A2, A);
    }

    int[] Merge (int[] A1, int[] A2, int[] A)
    {
        int i = 0;
        int j = 0;

        while (Compare(i,A1.length) < 0 && Compare(j,A2.length) < 0)
        {
            if (Compare(A1[i], A2[j]) <= 0){

                A[i+j] = A1[i];
                i += 1;

            } else {

                A[i+j] = A2[j];
                j += 1;
            }
            // This algorithm doesn't use "swaps" the same way the others do
            // We define this as our swap.
            NumSwaps += 1;
        }

        while (Compare(i,A1.length) < 0) {
            A[i + j] = A1[i];
            i = i + 1;
        }
        while (Compare(j,A2.length) < 0) {
            A[i + j] = A2[j];
            j = j + 1;
        }

        return A;
    }

    int[] CopyArray(int[] A, int i0, int i1)
    {
        int Diff = i1 - i0;
        int[] NewArray = new int[Diff];

        for(int i = i0; Compare(i, i1) < 0; i += 1)
        {
            NewArray[i - i0] = A[i];
        }

        return NewArray;
    }
}