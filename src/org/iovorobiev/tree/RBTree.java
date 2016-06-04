package org.iovorobiev.tree;

public class RBTree {

    public RBNode root;

    public void insert(int key) {
        if (root == null) {
            root = new RBNode(key, null);
            return;
        }
        RBNode current = root;
        while (true) {
            if (key < current.key) {
                if (current.left == null) {
                    current.left = new RBNode(key, current);
                    break;
                }
                current = current.left;
            } else if (key > current.key) {
                if (current.right == null) {
                    current.right = new RBNode(key, current);
                    break;
                }
                current = current.right;
            } else {
                return;
            }
        }
        balance(key, current);
    }

    private void balance(int key, RBNode current) {
        if (!current.isColorRed) {
            return;
        }
        if (current.parent == null) {
            current.isColorRed = false;
            return;
        }
        RBNode parent = current.parent;
        RBNode insertedNode = key < current.key? current.left : current.right;
        RBNode currentSibling = getSibling(current);
        if (currentSibling != null && currentSibling.isColorRed) {
            parent.isColorRed = true;
            currentSibling.isColorRed = false;
            current.isColorRed = false;
            if (parent.parent == null) {
                parent.isColorRed = false;
                return;
            }
            balance(key, parent.parent);
        } else if (!isStraightLine(insertedNode, current, parent)){
            zigzagRotate(key, current, parent, insertedNode);
            straightLineRotate(current.key, insertedNode, parent, current);
        } else {
            straightLineRotate(key, current, parent, insertedNode);
        }
    }

    private void zigzagRotate(int key, RBNode current, RBNode parent, RBNode insertedNode) {
        if (key > current.key) {
            rotateLeft(current);
        } else {
            rotateRight(current);
        }
    }

    private void straightLineRotate(int key, RBNode current, RBNode parent, RBNode insertedNode) {
        if (key > current.key) {
            rotateLeft(parent);
        } else {
            rotateRight(parent);
        }
        current.isColorRed = false;
        insertedNode.isColorRed = true;
        parent.isColorRed = true;
    }

    private boolean isStraightLine(RBNode child, RBNode current, RBNode parent) {
        return child == current.left && current == parent.left
                || child == current.right && current == parent.right;
    }

    public void delete(int key) {
        if (root == null) {
            return;
        }
        RBNode current = root;
        while (current.key != key) {
            if (key > current.key) {
                if (current.right == null) {
                    return;
                }
                current = current.right;
            } else if (key < current.key) {
                if (current.left == null) {
                    return;
                }
                current = current.left;
            }
        }
        balanceDelete(current);
    }

    private void balanceDelete(RBNode current) {
        RBNode parent = current.parent;
        RBNode replacement = findPredecessor(current);
        if (current.isColorRed) {
            if (replacement == null) {
                if (parent == null) {
                    root = current.right;
                    root.parent = null;
                    return;
                }
                if (current == parent.right) {
                    parent.right = current.right;
                    if (current.right != null) {
                        current.right.parent = parent;
                    }
                } else {
                    parent.left = current.right;
                    if (current.left != null) {
                        current.left.parent = parent;
                    }
                }
                return;
            }
            current.key = replacement.key;
            balanceDelete(replacement);
            return;
        }
        if (current.left == null || current.right == null) {
            RBNode child = current.left == null? current.right : current.left;
            if (parent == null) {
                root = child;
            } else {
                if (current == parent.left) {
                    parent.left = child;
                } else {
                    parent.right = child;
                }
            }
            if (child == null) {
                return;
            }
            child.parent = parent;
            if (child.isColorRed) {
                child.isColorRed = false;
                return;
            }
            deleteCase1(child);
            return;
        }
        if (replacement != null) {
            current.key = replacement.key;
            balanceDelete(replacement);
        }

    }

    private void deleteCase1(RBNode child) {
        if (child.parent != null) {
            deleteCase2(child);
        }
    }

    private void deleteCase2(RBNode child) {
        RBNode sibling = getSibling(child);
        if (sibling != null && sibling.isColorRed) {
            child.parent.isColorRed = true;
            sibling.isColorRed = false;
            if (child == child.parent.left) {
                rotateLeft(child.parent);
            } else {
                rotateRight(child.parent);
            }
        }
        deleteCase3(child);
    }

    private void deleteCase3(RBNode child) {
        RBNode sibling = getSibling(child);
        if (!child.parent.isColorRed
                && (sibling != null && !sibling.isColorRed )
                && (sibling.left == null || !sibling.left.isColorRed)
                && (sibling.right == null || !sibling.right.isColorRed)) {
            sibling.isColorRed = true;
            deleteCase1(child.parent);
        } else {
            deleteCase4(child);
        }
    }

    private void deleteCase4(RBNode child) {
        RBNode sibling = getSibling(child);
        if (child.parent.isColorRed
                && (sibling != null && !sibling.isColorRed)
                && (sibling.left == null || !sibling.left.isColorRed)
                && (sibling.right == null || !sibling.right.isColorRed)) {
            sibling.isColorRed = true;
            child.parent.isColorRed = false;
        } else {
            deleteCase5(child);
        }
    }

    private void deleteCase5(RBNode child) {
        RBNode sibling = getSibling(child);
        if (sibling != null && !sibling.isColorRed) {
            if (child == child.parent.left
                    && (sibling.right == null || !sibling.right.isColorRed)
                    && (sibling.left != null && sibling.left.isColorRed)) {
                sibling.isColorRed = true;
                sibling.left.isColorRed = false;
                rotateRight(sibling);
            } else if (child == child.parent.right
                        && (sibling.left == null || !sibling.left.isColorRed)
                        && (sibling.right != null && sibling.right.isColorRed)) {
                sibling.isColorRed = true;
                sibling.right.isColorRed = false;
                rotateLeft(sibling);
            }
        }
        deleteCase6(child);
    }

    private void deleteCase6(RBNode child) {
        RBNode sibling = getSibling(child);
        if (sibling == null) {
            return;
        }
        sibling.isColorRed = child.parent.isColorRed;
        child.parent.isColorRed = false;
        if (child == child.parent.left) {
            sibling.right.isColorRed = false;
            rotateLeft(child.parent);
        } else {
            sibling.left.isColorRed = false;
            rotateRight(child.parent);
        }
    }

    private RBNode findPredecessor(RBNode current) {
        RBNode predecessor = current.left;
        while (predecessor != null && predecessor.right != null) {
            predecessor = predecessor.right;
        }
        return predecessor;
    }

    private RBNode getSibling(RBNode node) {
        if (node.parent == null) {
            return null;
        }
        if (node.parent.left == node) {
            return node.parent.right;
        } else {
            return node.parent.left;
        }
    }

    private void rotateRight(RBNode current) {
        if (current.parent == null) {
            root = current.left;
        } else {
            if (current == current.parent.left) {
                current.parent.left = current.left;
            } else {
                current.parent.right = current.left;
            }
        }
        RBNode proxyRight = current.left.right;
        current.left.parent = current.parent;
        current.parent = current.left;
        if (proxyRight != null) {
            proxyRight.parent = current;
        }
        current.left.right = current;
        current.left = proxyRight;

    }

    private void rotateLeft(RBNode current) {
        if (current.parent == null) {
            root = current.right;
        } else {
            if (current == current.parent.left) {
                current.parent.left = current.right;
            } else {
                current.parent.right = current.right;
            }
        }
        RBNode proxyLeft = current.right.left;
        current.right.parent = current.parent;
        current.parent = current.right;
        if (proxyLeft != null) {
            proxyLeft.parent = current;
        }
        current.right.left = current;
        current.right = proxyLeft;
    }

    public static class RBNode {
        public int key;
        public RBNode left;
        public RBNode right;
        public RBNode parent;
        public boolean isColorRed;

        public RBNode(int key, RBNode parent) {
            this.key = key;
            this.parent = parent;
            isColorRed = true;
        }

        public String getColorString() {
            return isColorRed? "Red" : "Black";
        }

        public int getColorInt() {
            return isColorRed? 0 : 1;
        }
    }
}
