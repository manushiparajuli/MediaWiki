/*
 * Name: Manushi Parajuli (mp0812)
 * Name: Sanjana Binoj
 * */


package assignment.celebrities;

public class OrderedDictionary implements OrderedDictionaryADT {

    Node root;

    OrderedDictionary() {
        root = new Node();
    }

    /**
     * Returns the Record object with key k, or it returns null if such a record
     * is not in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/celebrities/DictionaryException.java
     */
    @Override
    public CelebrityRecord find(DataKey k) throws DictionaryException {
        Node current = root;
        int comparison;
        if (root.isEmpty()) {
            throw new DictionaryException("There is no record matches the given key");
        }

        while (true) {
            comparison = current.getData().getDataKey().compareTo(k);
            if (comparison == 0) { // key found
                return current.getData();
            }
            if (comparison == 1) {
                if (current.getLeftChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getLeftChild();
            } else if (comparison == -1) {
                if (current.getRightChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getRightChild();
            }
        }

    }

    /**
     * Inserts r into the ordered dictionary. It throws a DictionaryException if
     * a record with the same key as r is already in the dictionary.
     *
     * @param r
     * @throws birds.DictionaryException
     */
    @Override
    public void insert(CelebrityRecord r) throws DictionaryException {
        if (root.isEmpty()) {
            root = new Node(r);
            return;
        }

        Node current = root;
        int comparison;

        while (true) {
            comparison = current.getData().getDataKey().compareTo(r.getDataKey());
            if (comparison == 0) {
                throw new DictionaryException("A record with the same key already exists");
            }
            if (comparison == 1) {
                if (current.getLeftChild() == null) {
                    Node newNode = new Node(r);
                    current.setLeftChild(newNode);
                    newNode.setParent(current);
                    return;
                }
                current = current.getLeftChild();
            } else if (comparison == -1) {
                if (current.getRightChild() == null) {
                    Node newNode = new Node(r);
                    current.setRightChild(newNode);
                    newNode.setParent(current);
                    return;
                }
                current = current.getRightChild();
            }
        }
    }

    /**
     * Removes the record with Key k from the dictionary. It throws a
     * DictionaryException if the record is not in the dictionary.
     *
     * @param k
     * @throws birds.DictionaryException
     */
    /**
     * Removes the record with Key k from the dictionary. It throws a DictionaryException if the record is not in the dictionary.
     *
     * @param k
     * @throws DictionaryException
     */
    @Override
    public void remove(DataKey k) throws DictionaryException {

        Node removeNode = root;
        if (root.isEmpty()) {
            throw new DictionaryException("There is no record matches the given key");
        }
        while (removeNode != null) {
            int comparison = removeNode.getData().getDataKey().compareTo(k);
            if (comparison == 0) {
                break;
            } else if (comparison > 0) {
                removeNode = removeNode.getLeftChild();
            } else {
                removeNode = removeNode.getRightChild();
            }
        }

        if (removeNode == null) {
            throw new DictionaryException("The record to be removed is not in the dictionary.");
        }

        if (removeNode.getLeftChild() == null && removeNode.getRightChild() == null) {
            if (removeNode == root) {
                root = removeNode;
                if(root.hasLeftChild()){
                    if(!root.hasLeftChild())
                        root = new Node();

                }
                root = new Node();
            } else {
                Node parentNode = removeNode.getParent();
                if (removeNode == parentNode.getLeftChild()) {
                    parentNode.setLeftChild(null);
                } else {
                    parentNode.setRightChild(null);
                }
            }
            return;
        }

       if (removeNode.getLeftChild() == null || removeNode.getRightChild() == null) {
            Node childNode;
            if (removeNode.getLeftChild() != null) {
                childNode = removeNode.getLeftChild();
            } else {
                childNode = removeNode.getRightChild();
            }
            if (removeNode == root) {
                root = childNode;
            } else {
                Node parentNode = removeNode.getParent();
                if (removeNode == parentNode.getLeftChild()) {
                    parentNode.setLeftChild(childNode);
                } else {
                    parentNode.setRightChild(childNode);
                }
                childNode.setParent(parentNode);
            }
            return;
        }
        Node successor = removeNode.getRightChild();
        while (successor.getLeftChild() != null) {
            successor = successor.getLeftChild();
        }

        removeNode.setData(successor.getData());
        Node parent = successor.getParent();
        if (successor.getRightChild() == null) {
            if (successor == parent.getLeftChild()) {
                parent.setLeftChild(null);
            } else {
                parent.setRightChild(null);
            }
        } else {
            if (successor == parent.getLeftChild()) {
                parent.setLeftChild(successor.getRightChild());
            } else {
                parent.setRightChild(successor.getRightChild());
            }
            successor.getRightChild().setParent(parent);
        }
    }


    /**
     * Returns the successor of k (the record from the ordered dictionary with
     * smallest key larger than k); it returns null if the given key has no
     * successor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws birds.DictionaryException
     */
    @Override
    public CelebrityRecord successor(DataKey k) throws DictionaryException {
        Node current = root;
        Node successor = null;
        int comparison;
        if (root.isEmpty()) {
            throw new DictionaryException("Dictionary is empty");
        }

        while (current != null) {
            comparison = current.getData().getDataKey().compareTo(k);
            if (comparison > 0) {
                successor = current;
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }

        if (successor == null) {
            throw new DictionaryException("No successor found for the given key");
        }

        return successor.getData();
    }



    /**
     * Returns the predecessor of k (the record from the ordered dictionary with
     * largest key smaller than k; it returns null if the given key has no
     * predecessor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws birds.DictionaryException
     */
    /**
     * Returns the predecessor of k (the record from the ordered dictionary with
     * largest key smaller than k); it returns null if the given key has no
     * predecessor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws birds.DictionaryException
     */
    /**
     * Returns the predecessor of k (the record from the ordered dictionary with
     * largest key smaller than k); it returns null if the given key has no
     * predecessor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws DictionaryException
     */
    @Override
    public CelebrityRecord predecessor(DataKey k) throws DictionaryException {
        Node current = root;
        Node predecessor = null;
        int comparison;

        while (current != null) {
            comparison = current.getData().getDataKey().compareTo(k);

            if (comparison >= 0) {
                current = current.getLeftChild();
            } else {
                predecessor = current;
                current = current.getRightChild();
            }
        }

        if (predecessor == null) {
            throw new DictionaryException("There is no predecessor for the given key");
        }

        return predecessor.getData();
    }




    /**
     * Returns the record with smallest key in the ordered dictionary. Returns
     * null if the dictionary is empty.
     *
     * @return
     */
    @Override
    public CelebrityRecord smallest() throws DictionaryException {
        Node current = root;
        while (current.getLeftChild() != null) {
            current = current.getLeftChild();
        }
        if (current.isEmpty()) {
            throw new DictionaryException("The dictionary is empty.");
        }
        return current.getData();
    }

    @Override
    public CelebrityRecord largest() throws DictionaryException{
        Node current = root;
        while (current.getRightChild() != null) {
            current = current.getRightChild();
        }
        if (current.isEmpty()) {
            throw new DictionaryException("The dictionary is empty.");
        }
        return current.getData();
    }
    @Override
    public boolean isEmpty (){
        return root.isEmpty();
    }
}
