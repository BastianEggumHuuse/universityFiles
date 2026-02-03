class Test
{
    public static void main(String[] args) {
        
        AVLTree tree = new AVLTree();

        tree.insert(-3);
        tree.insert(-2);
        tree.insert(-1);
        tree.insert(0);
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);

        System.out.println("Row 1" + tree.root.element);
        System.out.println("Full 1" + tree.toString());
        System.out.println("Row 2" + tree.root.left.element + " " + tree.root.right.element);
        System.out.println("Row 3" + tree.root.left.left.element + " " + tree.root.left.right.element + " " + tree.root.right.left.element + " " + tree.root.right.right.element);

    }
}