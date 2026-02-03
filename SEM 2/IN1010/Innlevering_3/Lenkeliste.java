abstract class Lenkeliste <E> implements Liste<E>
{

    protected class Node
    {
        protected E element;
        protected Node prev;
        protected Node next;

        Node(E newElement, Node p, Node n)
        {
            element = newElement;
            prev = p;
            next = n;
        }
    }

    protected Node head;
    protected Node tail;
    protected int count = 0;

    Lenkeliste()
    {
        head = new Node(null,null,null);
        tail = new Node(null,head,null);
        head.next = tail;
    }

    @Override
    public int størrelse()
    {
        return count;
    }

    @Override
    public void leggTil(E x)
    {
        // Inserting a node between the last and second to last node.
        Node newNode = new Node(x,tail.prev,tail);
        tail.prev.next = newNode; 
        tail.prev = newNode;

        count += 1;
    }

    @Override
    public E hent()
    {
        if (head.next == tail)
        {
            // Listen er tom:
            throw new UgyldigListeindeks(0);
        }
        
        return head.next.element;
    }

     @Override
    public E fjern()
    {
        
        if (head.next == tail)
        {
            // Listen er tom:
            throw new UgyldigListeindeks(0);
        }

        E elem = head.next.element;
        head.next.next.prev = head;
        head.next = head.next.next;

        count -= 1;
        return elem;
    }

    public String toString()
    {
        String s = "";

        if(count == 0)
        {
            s = "Ingen elementer i listen!!";
            return s;
        }

        Node iter = head.next;
        while(iter.next != tail)
        {
            s += iter.element;
            iter = iter.next;
        }

        return s;
    }

}