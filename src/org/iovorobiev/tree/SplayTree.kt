package org.iovorobiev.tree

class SplayTree {
    var root: SplayTreeNode? = null

    fun insert(key: Int) {
        var current = root ?: SplayTreeNode(key)
        while(key != current.key) {
            if (key > current.key) {
                if (current.right == null) {
                    current.right = SplayTreeNode(key, current)
                    current = current.right!!
                    break
                }
                current = current.right!!
            } else {
                if (current.left == null) {
                    current.left = SplayTreeNode(key, current)
                    current = current.left!!
                    break
                }
                current = current.left!!
            }
        }
        splay(current)
        root = current;
    }

    fun merge(one: SplayTreeNode?, two: SplayTreeNode?) : SplayTreeNode? {
        if (one == null) {
            return two
        }
        if (two == null) {
            return one
        }
        val left: SplayTreeNode?
        val right: SplayTreeNode?
        if (one.key > two.key) {
            left = two
            right = one
        } else if (one.key < two.key){
            left = one
            right = two
        } else {
            right = merge(two.left, two.right)
            left = one
        }
        var current = left
        while (current?.right != null) {
            current = current?.right!!
        }
        splay(current!!)
        current.right = right
        return current
    }

    fun delete(key: Int) {
        var current: SplayTreeNode = root ?: return
        while (key != current.key) {
            if (key > current.key) {
                current = current.right ?: return
            } else {
                current = current.left ?: return
            }
        }
        splay(current)
        root = merge(current.left, current.right)
    }

    fun splay(node: SplayTreeNode) {
        while(node.parent != null) {
            if (node.parent?.parent == null ) {
                if (node == node.parent?.left) {
                    rotateRight(node.parent!!)
                } else {
                    rotateLeft(node.parent!!)
                }
            } else if (node == node.parent?.left
                    && node.parent == node.parent?.parent?.left) {
                rotateRight(node.parent!!.parent!!)
                rotateRight(node.parent!!)
            } else if (node == node.parent?.right
                    && node.parent == node.parent?.parent?.right) {
                rotateLeft(node.parent!!.parent!!)
                rotateLeft(node.parent!!)
            } else if (node == node.parent?.left
                    && node.parent == node.parent?.parent?.right) {
                rotateRight(node.parent!!)
                rotateLeft(node.parent!!)
            } else if (node == node.parent?.right
                    && node.parent == node.parent?.parent?.left) {
                rotateLeft(node.parent!!)
                rotateRight(node.parent!!)
            }
        }
    }

    private fun rotateLeft(node: SplayTreeNode) {
        if (node == node.parent?.left) {
            node.parent?.left = node.right
        } else if (node == node.parent?.right){
            node.parent?.right = node.right
        }
        val proxyLeft = node.right?.left
        node.right?.parent = node.parent
        node.parent = node.right
        proxyLeft?.parent = node
        node.right?.left = node
        node.right = proxyLeft
    }

    private fun rotateRight(node: SplayTreeNode) {
        if (node == node.parent?.left) {
            node.parent?.left = node.left
        } else if (node == node.parent?.right){
            node.parent?.right = node.left
        }
        val proxyRight = node.left?.right
        node.left?.parent = node.parent
        node.parent = node.left
        proxyRight?.parent = node
        node.left?.right = node
        node.left = proxyRight
    }

    class SplayTreeNode constructor(
                        val key: Int,
                        var parent: SplayTreeNode? = null,
                        var left: SplayTreeNode? = null,
                        var right: SplayTreeNode? = null
                        ) {

    }
}