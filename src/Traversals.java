import java.util.*;

public class Traversals {

  /**
   * Returns the sum of the values of all leaf nodes in the given tree of integers.
   * A leaf node is defined as a node with no children.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the sum of leaf node values, or 0 if the tree is null
   */
  public static int sumLeafNodes(TreeNode<Integer> node) {
    if (node == null) return 0;
    if (node.right == null && node.left == null) return node.value;

    return sumLeafNodes(node.left) + sumLeafNodes(node.right);
  }

  /**
   * Counts the number of internal nodes (non-leaf nodes) in the given tree of integers.
   * An internal node has at least one child.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the count of internal nodes, or 0 if the tree is null
   */
  public static int countInternalNodes(TreeNode<Integer> node) {
    if (node == null || node.left == null && node.right == null) return 0;
    // if (node.left == null && node.right == null) return 0;

    return 1 + countInternalNodes(node.left) + countInternalNodes(node.right);
  }

  /**
   * Creates a string by concatenating the string representation of each node's value
   * in a post-order traversal of the tree. For example, if the post-order visitation
   * encounters values "a", "b", and "c" in that order, the result is "abc".
   * If node is null, returns an empty string.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a post-order traversal string, or an empty string if the tree is null
   */
  public static <T> String buildPostOrderString(TreeNode<T> node) {
    StringBuilder text = new StringBuilder(); // Strings are immutable, so must use a StringBuilder;
    buildPostOrderString(node, text);
    return text.toString();
  }

  public static <T> StringBuilder buildPostOrderString(TreeNode<T> node, StringBuilder text) {
    if (node == null) return text;

    buildPostOrderString(node.left, text);
    buildPostOrderString(node.right, text);
    text.append(node.value);

    return text;

  }

  /**
   * Collects the values of all nodes in the tree level by level, from top to bottom.
   * If node is null, returns an empty list.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a list of node values in a top-to-bottom order, or an empty list if the tree is null
   */
  public static <T> List<T> collectLevelOrderValues(TreeNode<T> node) {
    Queue<TreeNode<T>> queue = new LinkedList<>();
    List<T> values = new LinkedList<>();

    queue.add(node);

    while (!queue.isEmpty()) {
      TreeNode<T> current = queue.poll();

      if (current == null) continue;

      values.add(current.value);
      queue.add(current.left);
      queue.add(current.right);
    }

    return values;
  }

  /**
   * Counts the distinct values in the given tree.
   * If node is null, returns 0.
   *
   * @param node the node of the tree
   * @return the number of unique values in the tree, or 0 if the tree is null
   */
  public static int countDistinctValues(TreeNode<Integer> node) {
    Set<Integer> set = new HashSet<>();

    countDistinctValues(node, set);

    return set.size();
  }

  public static void countDistinctValues(TreeNode<Integer> node, Set<Integer> set) {
    if (node == null) return;
    
    set.add(node.value);
    countDistinctValues(node.left, set);
    countDistinctValues(node.right, set);

    return;
  }

  /**
   * Determines whether there is at least one root-to-leaf path in the tree
   * where each successive node's value is strictly greater than the previous node's value.
   * If node is null, returns false.
   *
   * @param node the node of the tree
   * @return true if there exists a strictly increasing root-to-leaf path, false otherwise
   */
  public static boolean hasStrictlyIncreasingPath(TreeNode<Integer> node) {
    if (node != null && node.left == null && node.right == null) return true;
    boolean increasing = false;

    return hasStrictlyIncreasingPath(node, increasing);
  }

  public static boolean hasStrictlyIncreasingPath(TreeNode<Integer> node, boolean increasing) {
    if (node == null) return increasing;
    if (node.left == null && node.right == null) return increasing;

    if (node.value < node.left.value || node.value < node.right.value) return true;
    if (node.value >= node.left.value || node.value >= node.right.value) return false;
    
    return (hasStrictlyIncreasingPath(node.left, increasing) || hasStrictlyIncreasingPath(node.right, increasing));
  }

  // OPTIONAL CHALLENGE
  /**
   * Checks if two trees have the same shape. Two trees have the same shape
   * if they have exactly the same arrangement of nodes, irrespective of the node values.
   * If both trees are null, returns true. If one is null and the other is not, returns false.
   *
   * @param nodeA the node of the first tree
   * @param nodeB the node of the second tree
   * @param <T>   the type of values stored in the trees
   * @return true if the trees have the same shape, false otherwise
   */
  public static <T> boolean haveSameShape(TreeNode<T> nodeA, TreeNode<T> nodeB) {
    if (nodeA == null && nodeB == null) return true;
    if (nodeA == null ^ nodeB == null) return false;

    boolean sameLeft = haveSameShape(nodeA.left, nodeB.left);
    boolean sameRight = haveSameShape(nodeA.right, nodeB.right);

    return (sameLeft && sameRight);
  }


  // OPTIONAL CHALLENGE
  // Very challenging!
  // Hints:
  // List.copyOf may be helpful
  // Consider adding a helper method
  // Consider keeping the current path in a separate variable
  // Consider removing the current node from the current path after the node's subtrees have been traversed.
  /**
   * Finds all paths from the root to every leaf in the given tree.
   * Each path is represented as a list of node values from root to leaf.
   * The paths should be added pre-order.
   * If node is null, returns an empty list.
   * 
   * Example:
   *
   *         1
   *        / \
   *       2   3
   *      / \    \
   *     4   5    6
   * 
   * Expected output:
   *   [[1, 2, 4], [1, 2, 5], [1, 3, 6]]
   * 
   * @param node the root node of the tree
   * @return a list of lists, where each inner list represents a root-to-leaf path in pre-order
   */
  public static <T> List<List<T>> findAllRootToLeafPaths(TreeNode<T> node) {
    List<List<T>> listOfLists = new ArrayList<>();
    if (node == null) return listOfLists;
    List<T> list = new ArrayList<>();
    list.add(node.value);

    findAllRootToLeafPaths(node.left, listOfLists, list);
    findAllRootToLeafPaths(node.right, listOfLists, list);

    return listOfLists;
  }

  public static <T> List<T> findAllRootToLeafPaths(TreeNode<T> node, List<List<T>> listOfLists, List<T> list) {
    // store a copy of the list in ArrayList currentList
    List<T> currentList = new ArrayList<>();
    currentList = List.copyOf(list);
    
    // if null, return current list
    if (node == null) return currentList;

    // add current value to list
    currentList.add(node.value);

    // if at a leaf, add current list to list of lists, then remove current value from list
    if (node.right == null && node.left == null) {
      listOfLists.add(currentList);
      currentList.remove(node.value);
      return currentList;
    }

    // traverse left with listOfLists and list
    findAllRootToLeafPaths(node.left, listOfLists, currentList);
    // traverse right with listOfLists and list
    findAllRootToLeafPaths(node.right, listOfLists, currentList);

    // pass current list
    return currentList;
  }
}
