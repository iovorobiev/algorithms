package org.iovorobiev.tree

import java.util.*

class BTree(val loadFactor: Float = 0.25f, val sizeOfBlock: Int = 10) {
    var root: BTreeNode? = BTreeNode()
    val minSize: Int;

    init {
        minSize = (loadFactor * sizeOfBlock).toInt()
    }

    fun delete(key: Int) {
        root?.let {
            findAndDelete(it, null, key)
        }
    }

    fun findAndDelete(node: BTreeNode, parent: BTreeNode?, key: Int) {

        val child = node.getChildByKey(key)
        child?.let {
            findAndDelete(child, node, key)
        }
        if (node.keys.contains(key)) {
             if (node.children == null) {
                 node.removeKey(key)
             } else {
                 var next = node.children!!.get(node.keys.indexOf(key))
                 while (next.children != null) {
                     next = next.children?.last()!!
                 }
                 val newKey = next.keys.last()
                 findAndDelete(node, parent, newKey)
                 node.keys[node.keys.indexOf(key)] = newKey
                 return
             }
        }
        deleteFrom(node, parent)
    }

    fun deleteFrom(node: BTreeNode, parent: BTreeNode?) {
        parent?.let {
            balance(node, it)
        }
    }

    private fun balance(node: BTreeNode, parent: BTreeNode) {
        if (node.keys.size > minSize) {
            return
        }
        val leftSibling = parent.findSibling(node, true)
        if (leftSibling != null && leftSibling.keys.size > minSize + 1) {
            rotateRight(node, parent, leftSibling)
            return
        }
        val rightSibling = parent.findSibling(node, false)
        if (rightSibling != null && rightSibling.keys.size > minSize + 1) {
            rotateLeft(node, parent, rightSibling)
            return
        }
        val right: BTreeNode
        val left: BTreeNode
        when {
            leftSibling == null && rightSibling != null -> {
                right = rightSibling
                left = node
            }
            leftSibling != null -> {
                right = node
                left = leftSibling
            }
            else -> throw IllegalStateException("Both siblings are null")
        }
        val key = parent.keys.get(0)
        left.insertKey(key)
        left.keys.addAll(right.keys)
        right.children?.let {
            left.addChildren(it)
        }
        parent.removeKey(key)
        parent.removeChild(right)
    }

    private fun rotateLeft(node: BTreeNode, parent: BTreeNode, rightSibling: BTreeNode) {
        val index = parent.getKeyIndexByRightChild(rightSibling)
        if (index == -1) {
            throw IllegalArgumentException("No right sibling in the tree on rotate left")
        }
        node.insertKey(parent.keys[index])
        parent.keys.removeAt(index)
        parent.insertKey(rightSibling.keys[0])
        rightSibling.keys.removeAt(0)
    }

    private fun rotateRight(node: BTreeNode, parent: BTreeNode, leftSibling: BTreeNode) {
        val index = parent.getKeyIndexByRightChild(node)
        if (index == -1) {
            throw IllegalArgumentException("No right sibling in the tree on rotate right")
        }
        node.insertKey(parent.keys[index])
        parent.keys.removeAt(index)
        parent.insertKey(leftSibling.keys.last())
        leftSibling.keys.removeAt(leftSibling.keys.lastIndex)
    }

    fun insert(key: Int) {
        (root ?: BTreeNode()).apply {
            findAndInsert(this, null, key)
        }
    }

    private fun findAndInsert(current: BTreeNode, parent: BTreeNode?, key: Int): Int? {
        val next = current.getChildByKey(key)
        var newKey: Int? = key
        next?.let { node ->
            newKey = findAndInsert(node, current, key)
        }
        return insertTo(current, parent, newKey)
    }

    private fun insertTo(node: BTreeNode, parent: BTreeNode?, key: Int?): Int? {
        key ?: return null
        node.insertKey(key)
        return if (node.keys.trueSize() > sizeOfBlock) {
            split(node, parent)
        } else {
            null
        }
    }

    private fun split(node: BTreeNode, parent: BTreeNode?): Int? {
        val median: Int = Math.floor(node.keys.size / 2.0).toInt()
        val left = BTreeNode(node.keys.subList(0, median),
                node.children?.subList(0, median + 1))
        val right = BTreeNode(node.keys.subList(median + 1, node.keys.size),
                node.children?.let { it.subList(median + 1, it.size) })
        if (parent == null) {
            val newRoot = BTreeNode()
            newRoot.insertKey(node.keys[median])
            newRoot.replaceNodeWith(node, left, right)
            root = newRoot
            return null
        }
        parent.replaceNodeWith(node, left, right)
        return node.keys[median]
    }
}

class BTreeNode {
    val keys: MutableList<Int> = LinkedList()
    var children: MutableList<BTreeNode>? = null

    constructor() {
    }

    constructor (withKeys: MutableList<Int>, withChildren: MutableList<BTreeNode>?) {
        keys.addAll(withKeys)
        children = withChildren
    }

    fun getChildByKey(newKey: Int): BTreeNode? {
        for (i in 0..keys.lastIndex) {
            when {
                newKey == keys[i] -> return null
                newKey < keys[i] -> return children?.get(i)
            }
        }
        return children?.last()
    }

    fun getKeyIndexByRightChild(child: BTreeNode): Int {
        for (i in 0..keys.lastIndex) {
            if (child.keys.last() < keys[i]) {
                return i - 1
            }
        }
        return keys.lastIndex
    }

    fun removeKey(key: Int) {
        keys.remove(key)
    }

    fun removeChild(child: BTreeNode) {
        children?.remove(child)
    }

    fun insertKey(newKey: Int) {
        val index = -Collections.binarySearch(keys, newKey) - 1
        keys.add(index, newKey)
    }

    fun replaceNodeWith(node: BTreeNode, left: BTreeNode, right: BTreeNode) {
        children = children ?: LinkedList()
        children?.indexOf(node)?.apply {
            val index = if (this == -1) 0 else this
            children?.add(index, right)
            children?.add(index, left)
            children?.remove(node)
        }
    }

    fun addChildren(children: MutableList<BTreeNode>) {
        this.children = this.children ?: LinkedList()
        this.children?.addAll(children)
    }

    fun findSibling(node: BTreeNode, left: Boolean): BTreeNode? {
        val index = children?.indexOf(node)
        return if (index == null || index == -1) null
        else children.safeGet(index + if (left) -1 else 1)
    }
}

fun <T> MutableList<T>?.trueSize(): Int {
    return if (this == null) 0 else size
}

fun <T> MutableList<T>?.safeGet(index: Int): T? {
    this ?: return null
    return if (index < 0 || index >= size) null else get(index)
}