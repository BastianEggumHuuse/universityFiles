class Forelesning
{

    public static void main(String[] args)
    {
        Box<String> b = new Box("Hello");
        Box<Integer> b2 = new Box(10);

        MultiBox<Float,Integer> mb = new MultiBox(2.0,1);

        NumBox<Float> nb = new NumBox(15.7);

        b.Printer();
        b2.Printer();
        mb.Printer();
        nb.Printer();

        int[] list = new int[10];

        // Foreach loop!!!!!!
        for(int i : list)
        {
            System.out.println(i);
        }

        int[] arr = new int[4];
        arr[0] = 1;
        arr[1] = 2;
        arr[2] = 3;
        arr[3] = 4;

        try 
        {
            IterBox<Integer> iBox = new IterBox(arr);

            for(int i : iBox)
            {
                System.out.println(i);
            }
        }
        catch (Exception e)
        {
            System.out.println("Ahh piss");
        }
    
    }

}

// Generics

class Box<E>
{
    protected E element;

    public Box(E newElement)
    {
        element = newElement;
    }

    public void Printer()
    {
        System.out.println(element);
    }
}

class MultiBox<E1,E2> 
{

    protected E1 element_1;
    protected E2 element_2;

    public MultiBox(E1 newElement_1, E2 newElement_2)
    {
        element_1 = newElement_1;
        element_2 = newElement_2;
    }

    public void Printer()
    {
        System.out.println(element_1 + " " + element_2);
    }
}

// Kun classer som arver fra Number kan brukes her.
class NumBox<N extends Number> extends Box
{
    public NumBox(N newElement)
    {
        super(newElement);
    }
}

// Kun classer som kan sammenligges kan brukes her.
class ComBox<N extends Comparable<N>> extends Box
{
    public ComBox(N newElement)
    {
        super(newElement);
    }
}

class IterBox<E>
{
    E[] elements;

    public IterBox(E[] newElements)
    {
        elements = newElements;
    }

    public class BoxIterator //implements Iterator<E>
    {
        int index = 0;

        public boolean hasNext()
        {
            return elements.length > index;
        }

        public E next()
        {
            E elem = elements[index];
            index += 1;
            return elem;
        }
    }

    public BoxIterator Iterator()
    {
        return new BoxIterator();
    }

}