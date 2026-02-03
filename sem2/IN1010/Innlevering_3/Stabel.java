class Stabel<E> extends Lenkeliste<E>
{
    Stabel()
    {
        super();
    }

    @Override
    public void leggTil(E x)
    {
        // Inserting a node between the last and second to last node.
        Node newNode = new Node(x,head,head.next);
        head.next.prev = newNode; 
        head.next = newNode;

        count += 1;
    }

}