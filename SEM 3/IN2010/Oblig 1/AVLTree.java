
import javax.lang.model.util.ElementScanner14;


class AVLTree extends BinarySearchTree
{
    // Found out you can inherit from inner classes today :)
    // Actually inheriting from Node doesn't help that much, since we have to override
    // many of the methods anyway.

    // Since the instructions do a lot more inserting and removing than it does searching,
    // an AVL-tree might not be the best solution here. By watching the runtimes of the A1 and B1,
    // we see that the regular binary search tree actually performs the tasks quicker when we have a lot of commands
    // (that or the code is just bad haha).
    class AVLNode extends Node
    {
        //The outer class handles empty trees, so the minimum height is 0
        public int height = 0;

        // Basic constructor
        public AVLNode(int Element)
        {
            // Initializing the base class
            super(Element);
        }

        // method that returns the height of a tree (or subtree)
        @Override
        public int getHeight()
        {
            if(left == null && right == null) //If we enter this statement, this is a leaf-node, and we return 0.
            {
                return(0);
            }
            if(left == null) // if we get here, we have 
            {
                return(1 + ((AVLNode)right).getHeight());
            }
            if(right == null)
            {
                return(1 + ((AVLNode)left).getHeight());
            }
            
            // if we get down here, we have both a left and right child, meaning we must choose the greatest of their heights
            return(1 + Integer.max(((AVLNode)left).getHeight(),((AVLNode)right).getHeight()));
        }

        // Method for performing a left rotation, a procedure used to balance the tree.
        public AVLNode rotateLeft()
        {
            // Getting the node to our right, which we will replace the current node with
            AVLNode replacement = (AVLNode)right;

            // Setting our right node to the replacement's left (we know that our replacement's left is higher than our current node)
            right = replacement.left;
            if(right != null)
            {
                right.parent = this;
            }
            // Placing the current node 
            replacement.left = this;
            parent = replacement;

            // Updating the heights of the nodes
            height = getHeight();
            replacement.height = replacement.getHeight();

            // Returning the new node, which has replaced the current one
            return(replacement);
        }

        // Method functionally identical to RotateLeft but mirrored.
        public AVLNode rotateRight()
        {
            AVLNode replacement = (AVLNode)left;

            left = replacement.right;
            if(left != null)
            {
                left.parent = this;
            }

            replacement.right = this;
            parent = replacement;

            height = getHeight();
            replacement.height = replacement.getHeight();

            return(replacement);
        }

        // Method for finding out how unbalanced a tree is
        public int balanceFactor()
        {
            // We've chosen to let positive values signal a weight to the right, and negative to the left.

            if(left == null && right == null)
            {
                return(0);
            }
            if(left == null)
            {
                //System.out.println(((AVLNode)right).height);
                return((AVLNode)right).height + 1;
            }
            if(right == null)
            {
                return -1 -((AVLNode)left).height;
            }

            return ((AVLNode)right).height - ((AVLNode)left).height;
        }

        public Node balance()
        {
            int factor = balanceFactor();
            AVLNode CurrentNode = this;

            // Node is weighted to the left
            if(factor < -1)
            {
                // Node's left is weighted to the right
                if(((AVLNode)left).balanceFactor() > 0)
                {
                    CurrentNode.left = ((AVLNode)CurrentNode.left).rotateLeft();
                }

                // balancing the node through rotation
                CurrentNode = CurrentNode.rotateRight();

                return(CurrentNode);
            }
            // Node is weighted to the right
            if(factor > 1)
            {

                // Node's right is weighted to the right
                if(((AVLNode)right).balanceFactor() < 0)
                {
                    CurrentNode.right = ((AVLNode)CurrentNode.right).rotateRight();
                }

                // balancing the node through rotation
                CurrentNode = CurrentNode.rotateLeft();
                
                return(CurrentNode);
            }

            // If we get here, the node is already sufficiently balanced.
            return(this);
        }

        // Method for inserting an element into the tree.
        public Node AVLinsert(int e)
        {
            // this method is identical to the one in BinarySearchTree, but includes balancing.

            if(e < element)
            {
                if(left == null)
                {
                    // Initializing new node
                    left = new AVLNode(e);
                    left.parent = this;

                    ((AVLNode)left).height = ((AVLNode)left).getHeight();
                    left = ((AVLNode)left).balance();

                    height = getHeight();
                    return(balance());
                }
                left = ((AVLNode)left).AVLinsert(e);
            }
            else if (e > element)
            {
                if(right == null)
                {
                    right = new AVLNode(e);
                    right.parent = this;

                    ((AVLNode)right).height = ((AVLNode)right).getHeight();
                    right = ((AVLNode)right).balance();
                    
                    height = getHeight();
                    return(balance());
                }
                right = ((AVLNode)right).AVLinsert(e);
            }

            height = getHeight();
            return(balance());
        }

        // method for removing an element from the tree
        @Override
        public Node remove(int e)
        {
            // Functionally identical to the method from BinarySearchTree, but with balancing

            // For the balancing to trigger at the correct times, all if statements must now be linked together with else ifs.

            if(e < element)
            {
                if(left != null)
                {
                    left = left.remove(e);
                }
            }
            else if (e > element)
            {
                if(right != null)
                {
                    right = right.remove(e);
                }
            }

            else if(left == null && right == null) // This node is a leaf
            {
                return(null);
            }
            else if (left == null) // This node has only a right child
            {
                // Setting height and balancing
                ((AVLNode)right).height = ((AVLNode)right).getHeight();
                right = ((AVLNode)right).balance();
                return(right);
            }
            else if (right == null) // This node has only a left child
            {
                // Setting height and balancing
                ((AVLNode)left).height = ((AVLNode)left).getHeight();
                left = ((AVLNode)left).balance();
                return(left);
            }
            else // This node has two children, which we must account for
            {
                int replacement = right.findMin().element; 
                right = right.remove(replacement); 
                element = replacement;
            }

            // Setting the height and balancing
            // If you reach this point, you are either a node that has just had it's element replaced, or a node that doesn't change at all, but has to be balanced on the way back up.
            // The nodes that don't change are the ones that enter the first two if statements!!! They end up back here on the way up.
            height = getHeight();
            return(balance());
        }

        // Debug method for testing if the invariant stays true
        // Since we use max here, we only look at weights to the right.
        public int highestWeight()
        {
            int num = balanceFactor();   
            if (left != null)
            {
                num = Integer.max(num,((AVLNode)left).highestWeight());
            }
            if(right != null)
            {
                num = Integer.max(num,((AVLNode)right).highestWeight());
            }
            return(num);
        }

        public int lowestWeight()
        {
            int num = balanceFactor();   
            if (left != null)
            {
                num = Integer.min(num,((AVLNode)left).lowestWeight());
            }
            if(right != null)
            {
                num = Integer.min(num,((AVLNode)right).lowestWeight());
            }
            return(num);
        }
    }   

    @Override
    public void insert(int e)
    {

        if(root == null)
        {
            root = new AVLNode(e);
        }

        root = ((AVLNode)root).AVLinsert(e);
    }

    @Override
    public void remove(int e)
    {

        if(root == null)
        {
            return;
        }

        root = root.remove(e);
    } 

    public int[] highestWeight()
    {
        if (root == null)
        {
            return new int[] {0,0};
        }
        
        return(new int[] {((AVLNode)root).highestWeight(),((AVLNode)root).lowestWeight()});
    }
}