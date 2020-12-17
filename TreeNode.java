/**
 * Ein Knoten in einem binaeren Baum.
 *
 * Der gespeicherte Wert ist unveraenderlich,
 * die Referenzen auf die Nachfolger koennen aber
 * geaendert werden.
 *
 * Die Klasse bietet Methoden, um Werte aus einem Baum
 * zu suchen, einzufuegen und zu loeschen. Diese gibt
 * es jeweils noch in optimierten Varianten, um
 * rotate-to-root Baeume zu verwalten.
 */
public class TreeNode {
  /**
   * Linker Nachfolger
   */
  private TreeNode left;
  /**
   * Rechter Nachfolger
   */
  private TreeNode right;
  /**
   * Wert, der in diesem Knoten gespeichert ist
   */
  private final int value;

  /**
   * Erzeugt einen neuen Knoten ohne Nachfolger
   * @param val Wert des neuen Knotens
   */
  public TreeNode(int val) {
    this.value = val;
    this.left = null;
    this.right = null;
  }

  /**
   * Erzeugt einen neuen Knoten mit den gegebenen Nachfolgern
   * @param val Wert des neuen Knotens
   * @param left linker Nachfolger des Knotens
   * @param right rechter Nachfolger des Knotens
   */
  public TreeNode(int val, TreeNode left, TreeNode right) {
    this.value = val;
    this.left = left;
    this.right = right;
  }
  
  /**
  * @return Wert des aktuellen Knotens
  *
  */	
  public int getValue() {
    return this.value;
  }

  /**
   * @return Der gespeicherte Wert, umgewandelt in einen String
   */
  public String getValueString() {
    return Integer.toString(this.value);
  }

  /**
   * @return true, falls der Knoten einen linken Nachfolger hat, sonst false 
   */	
  public boolean hasLeft() {
    return this.left != null;
  }
  /**
   * @return true, falls der Knoten einen rechten Nachfolger hat, sonst false 
   */	
  public boolean hasRight() {
    return this.right != null;
  }


  /**
   * @return linker Nachfolger des aktuellen Knotens
   */		
  public TreeNode getLeft() {
    return this.left;
  }
  /**
   * @return rechter Nachfolger des aktuellen Knotens
   */	
  public TreeNode getRight() {
    return this.right;
  }

  /**
   * Sucht in diesem Teilbaum nach x, ohne den Baum zu veraendern.
   * @param x der gesuchte Wert
   * @return true, falls x enthalten ist, sonst false
   */
  public boolean simpleSearch(int x) {
    //TODO
	int value = getValue();
	
	if(x < value){
		return hasLeft() && getLeft().simpleSearch(x);
	}
	else if(x > value){
		return hasRight() && getRight().simpleSearch(x);
	}
	else{
		return true;
	}
  }

  /**
   * Fuegt x in diesen Teilbaum ein.
   * @param x der einzufuegende Wert
   */
  public void insert(int x) {
   //TODO
   if(x < value){
	   if(hasLeft()){
		   getLeft().insert(x);
	   }
	   else{
		   left = new TreeNode(x);
	   }
   } else if(x > value){
	   if(hasRight()){
		   getRight().insert(x);
	   }
	   else{
		   right = new TreeNode(x);
	   }
   }
  }

  /**
   * Sucht in diesem Teilbaum nach x und rotiert den Endpunkt der Suche in die
   * Wurzel.
   * @param x der gesuchte Wert
   * @return die neue Wurzel des Teilbaums
   */
  public TreeNode rotationSearch(int x) {
   //TODO
    final TreeNode node = rotationSearch(null, x);

    return simpleSearch(node.getValue()) ? rotationSearch(node.getValue()) : node;
  }

  private TreeNode rotationSearch(TreeNode parent, int x) {
    if (x < value && hasLeft()) {
      final TreeNode left = getLeft();

      if (left.getValue() == x) {
        rotateLeft(left, parent);
        return left;
      } else {
        return left.rotationSearch(this, x);
      }
    } else if (x > value && hasRight()) {
      final TreeNode right = getRight();

      if (right.getValue() == x) {
        rotateRight(right, parent);
        return right;
      } else {
        return right.rotationSearch(this, x);
      }
    } else {
      // Wert nicht gefunden (es sei denn root.value == x).
      // Breche Suche mit letztem Wert ab.
      return this;
    }
  }

  private void rotateLeft(TreeNode node, TreeNode parent) {
    left = node.getRight();
    node.right = this;

    if (parent != null)
      parent.childSwap(this, node);
  }

  private void rotateRight(TreeNode node, TreeNode parent) {
    right = node.getLeft();
    node.left = this;

    if (parent != null)
      parent.childSwap(this, node);
  }

  private void childSwap(TreeNode father, TreeNode child) {
    if (getLeft() == father) {
      left = child;
    } else if (getRight() == father) {
      right = child;
    }
  }

  /**
   * @return Geordnete Liste aller Zahlen, die in diesem Teilbaum gespeichert sind.
   */
  public String toString() {
    //TODO
	StringBuilder builder = new StringBuilder();

    if (hasLeft()) {
      builder.append(getLeft());
      builder.append(", ");
    }

    builder.append(getValueString());

    if (hasRight()) {
      builder.append(", ");
      builder.append(getRight());
    }

    return builder.toString();
  }
 
  /**
   * Erzeugt eine dot Repraesentation in str
   * @param str Stringbuilder Objekt zur Konstruktion der Ausgabe
   * @param nullNodes Hilfsvariable, um Nullknoten zu indizieren. Anfangswert sollte 0 sein. 
   * @return Den nullNodes Wert fuer den behandelten Baum
   */
  public int toDot(StringBuilder str, int nullNodes) {
    if(this.hasLeft()) {
      str.append(this.getValueString() + " -> " + this.left.getValueString() + ";"
        + System.lineSeparator());
      nullNodes = this.left.toDot(str, nullNodes);
    } else {
      str.append("null" + nullNodes + "[shape=point]" + System.lineSeparator()
        + this.getValueString() + " -> null" + nullNodes + ";" + System.lineSeparator());
      nullNodes += 1;
    }
    if(this.hasRight()) {
      str.append(this.getValueString() + " -> " + this.right.getValueString() + ";"
        + System.lineSeparator());
      nullNodes = this.right.toDot(str, nullNodes);
    } else {
      str.append("null" + nullNodes + "[shape=point]" + System.lineSeparator()
        + this.getValueString() + " -> null" + nullNodes + ";" + System.lineSeparator());
      nullNodes += 1;
    }
    return nullNodes;
  }

}
