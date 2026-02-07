class Forelesning_1
{
    public static void main(String[] args)
    {
        // Variable declaration
        int[] a = new int[10];

        // For loop
        for(int i = 1; i < 11; i += 1)
        {
            a[i-1] = i;
        }

        // If statement 
        int Num = 10;

        if (Num > 5)
        {
            // Du kan koble sammen strings med +, og da må du ikke engang convertere til String!!!
            System.out.println("Tallet er " + Num);
        }
    }
}