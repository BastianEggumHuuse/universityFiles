
class BinarySearchTree
{
    // in both this class and in AVLTree later, we found out that making
    // the algorithm methods methods of the inner class Node actually made
    // the algorithms harder to write. In the future, this will be avoided.

    class Node
    {
        int element; // The element we want to store

        // Other nodes this node connects to
        public Node parent = null;
        public Node left   = null;
        public Node right  = null;

        // Basic constructor
        public Node(int Element)
        {
            element = Element;
        }

        // Method for checking if the tree contains an element
        public boolean contains(int e)
        {    
            // This method performs a binary search, which works by dividing the set in two every step to get closer to the value we want.
            // Here we don't have to check any numbers, since  binary search trees are sorted the way a binary search works by design.
            // Instead, we must make insert and remove uphold this rule.

            if(e < element)
            {
                if(left == null)   
                {
                    return false;
                }
                return left.contains(e);
            }
            else if (e > element)
            {
                if(right == null)
                {
                    return false;
                }
                return right.contains(e);
            }

            return true;
        }

        // Method for inserting an element into the tree.
        public void insert(int e)
        {
            // Again we do a binary search, this time until we find the position we want to place the new item.

            // Since we only check for e being greater or lesser (not (greater or equal) or (lesser or equal), any number that already exists within the set
            // will simply go through without doing anything new.

            if(e < element)
            {
                if(left == null)
                {
                    left = new Node(e);
                    left.parent = this;
                    return;
                }
                left.insert(e);
            }
            else if (e > element)
            {
                if(right == null)
                {
                    right = new Node(e);
                    right.parent = this;
                    return;
                }
                right.insert(e);
            }
        }

        // Method that finds the minimum element within the tree
        public Node findMin()
        {
            if(left == null)
            {
                return this;
            }

            // The minimum is always to the left!!
            return(left.findMin());
        }

        // method for removing an element from the tree
        public Node remove(int e)
        {

            // this first bit is for traversing the tree.
            if(e < element)
            {
                // If the element exists, we dive deeper. (if the element doesn't exist within the tree, the recursion stops here)
                if(left != null)
                {
                    left = left.remove(e);
                }
                // if we enter this part, we don't want to change this node, so we return it unchanged.
                return(this);
            }
            if (e > element)
            {
                if(right != null)
                {
                    right = right.remove(e);
                }
                return(this);
            }

            // If we get to this line, we've encountered the element we are looking for
            if (left == null) // If left is null, we know that right is a node, or null. either way it works
            {
                return(right);
            }
            if (right == null) // if we get through this statement, we know that the left node isn't null, and we just replace the current node with that one.
            {
                return(left);
            }
            // This is the actual interesting replacement, where we have two children.
            int replacement = right.findMin().element; // firstly, we find the child of right with the lowest element (from the definition of this type of tree we know that this is greater than left)
            right = right.remove(replacement); // We remove this minimum element from it's original place
            element = replacement; // Then substitute the current element with the minimum (doing it this way instead of switching the actual object saves us from having to clean up some loose parent ends)
            return(this); // Returning the current node, which has just had it's element changed.
        }

        public int size(int c)
        {
            c += 1;
            if(left != null)
            {
                c = left.size(c);
            }
            if(right != null)
            {
                c = right.size(c);
            }

            return(c);
        }

        public int getHeight()
        {
            if(left == null && right == null) //If we enter this statement, this is a leaf-node, and we return 0.
            {
                return(0);
            }
            if(left == null)
            {
                return((right).getHeight() + 1);
            }
            if(right == null)
            {
                return((left).getHeight() + 1);
            }
            
            // if we get down here, we have both a left and right child, meaning we must choose the greatest of their heights
            return(Integer.max(left.getHeight(),right.getHeight()) + 1);
        }

        // Printing method for debugging.
        public String toString()
        {

            String newString = Integer.toString(element);
            if(left != null)
            {
                newString += " " + left.toString() + " ";
            }
            if(right != null)
            {
                newString += " " + right.toString() + " ";
            }

            return(newString);
        }
    }

    // all methods run recursively, but we call them from this object
    // Therefore we need a root to call them from.
    public Node root;

    // None of the methods below are particularily interesting, as they simply start the recursive chains.
    public boolean contains(int e)
    {
        if(root == null)
        {
            return false;
        }

        return root.contains(e);
    }

    public void insert(int e)
    {

        if(root == null)
        {
            root = new Node(e);
        }

        root.insert(e);
    }

    public void remove(int e)
    {

        if(root == null)
        {
            return;
        }

        root = root.remove(e);
    }

    public int size()
    {
        if(root == null)
        {
            return 0;
        }
        return(root.size(0));
    }

    public int height()
    {
        if(root == null)
        {
            return -1;
        }

        return(root.getHeight());
    }

    @Override
    public String toString()
    {
        if(root == null)
        {
            return("null");        
        }

        return(root.toString());
    }
}