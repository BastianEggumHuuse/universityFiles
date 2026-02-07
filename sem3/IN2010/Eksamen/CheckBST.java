class Node
{

    int element;
    Node left;
    Node right;

    public Node(int newElement)
    {
        element = newElement;
        left = null;
        right = null;
    }

    public void LinkLeft(Node newLeft)
    {
        if(left == null)
        {
            left = newLeft;
            newLeft.right = left;
            return;
        }

        newLeft.left = left;
        newLeft.right = this;
        left.right = newLeft;
        left = newLeft;
    }

    public void LinkRight(Node newRight)
    {
        if(right == null)
        {
            right = newRight;
            newRight.left = right;
            return;
        }

        newRight.right = right;
        newRight.left = this;
        right.left = newRight;
        right = newRight;
    }
}

class CheckBST
{

    public static void main(String[] args) {

        // Building tree
        Node v_0 = new Node(5);
        Node v_l = new Node(2);
        Node v_r = new Node(7);
        Node v_l_l = new Node(1);
        Node v_l_r = new Node(9);
        Node v_r_l = new Node(6);

        v_0.left = v_l; v_0.right = v_r;
        v_l.left = v_l_l; v_l.right = v_l_r;
        v_r.left = v_r_l;

        Node v = new Node(v_0.element);
        Node head = new Node(0);
        Node tail = new Node(0);

        head.right = v;
        v.left = head;
        v.right = tail;
        tail.left = v;

        AddChildrenToLinkedList(v_0,v);

        boolean IsBST = true;

        Node iter = head.right;
        while(iter.right != tail)
        {
            int e_l = iter.element;
            int e_r = iter.right.element;

            if(e_l > e_r)
            {
                IsBST = false;
                break;
            }

            iter = iter.right;
        }

        System.out.println(IsBST);
    }

    public static void AddChildrenToLinkedList(Node v, Node n)
    {
        if (v.left != null)
        {
            Node n_l = new Node(v.left.element);
            n.LinkLeft(n_l);
            AddChildrenToLinkedList(v.left,n_l);
        }
        
        if (v.right != null)
        {
            Node n_r = new Node(v.right.element);
            n.LinkRight(n_r);
            AddChildrenToLinkedList(v.right,n_r);
        }
    }
}