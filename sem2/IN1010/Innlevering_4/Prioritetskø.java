class Prioritetskø<E extends Comparable<E>> extends Lenkeliste<E>
{
    Prioritetskø()
    {
        super();
    }
    
    
    @Override
    public void leggTil(E x)
    {
        // Finding the node's location
        Node iter = head;
        while(iter.next != tail && x.compareTo(iter.next.element) > 0)
        {
            iter = iter.next;
        }

        // Inserting a node between the last and second to last node.
        Node newNode = new Node(x,iter,iter.next);
        iter.next.prev = newNode; 
        iter.next = newNode;

        count += 1;
    }
}