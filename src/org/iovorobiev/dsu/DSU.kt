package org.iovorobiev.dsu

class DSU {

    fun makeNode(value: Any): Node = Node(value)

    fun union(x: Node, y: Node) {
        val xRoot = root(x)
        val yRoot = root(y)
        when {
            xRoot.rank < yRoot.rank -> xRoot.parent = yRoot
            xRoot.rank > yRoot.rank -> yRoot.parent = xRoot
            xRoot.rank == yRoot.rank -> {yRoot.parent = xRoot; xRoot.rank++}
        }
    }

    fun equivalent(node1: Node, node2: Node) : Boolean = root(node1) == root(node2)

    fun root(node: Node): Node = if (node.parent == null) node else root(node)
}

class Node (val value: Any){
    var parent: Node? = null
    var rank = 0
}

