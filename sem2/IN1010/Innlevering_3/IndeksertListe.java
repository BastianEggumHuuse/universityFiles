class IndeksertListe <E> extends Lenkeliste<E> 
{
    IndeksertListe()
    {
        super();
    }

    Node findNodeAtIndex(int pos)
    {
        if ((0 <= pos && pos < count) == false)
        {
            throw new UgyldigListeindeks(pos);
        }

        Node iter = head.next;
        int i = 0;

        while (iter.next != tail && i < pos)
        {
            iter = iter.next;
            i += 1;
        }

        return iter;
    }

    public void leggTil(int pos ,E x)
    {
        if (pos == count)
        {
            leggTil(x);
            return;
        }

        Node posNode = findNodeAtIndex(pos);

        // Inserting a node Directly before the node currently at index pos.
        Node newNode = new Node(x,posNode.prev,posNode);
        posNode.prev.next = newNode; 
        posNode.prev= newNode;

        count += 1;
    }

    public void sett(int pos ,E x)
    {
        Node posNode = findNodeAtIndex(pos);

        // Inserting a node Directly after the node at index pos.
        Node newNode = new Node(x,posNode.prev,posNode.next);
        posNode.next.prev = newNode; 
        posNode.prev.next = newNode;
    }

    public E hent(int pos)
    {
        Node posNode = findNodeAtIndex(pos);

        return posNode.element;
    }

    public E fjern(int pos)
    {
        Node posNode = findNodeAtIndex(pos);

        E elem = posNode.element;
        posNode.next.prev = posNode.prev;
        posNode.prev.next = posNode.next;

        count -= 1;
        return elem;
    }
}