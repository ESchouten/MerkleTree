package com.erikschouten.merkletree

import com.erikschouten.merkletree.classes.Hash
import com.erikschouten.merkletree.classes.Hashable
import java.security.MessageDigest

class MerkleTree : Hashable {

    val hash: Hash?
    val subTrees: Pair<MerkleTree, MerkleTree>?
    val leafs: Pair<Leaf, Leaf?>?


    constructor(hash: Hash) {
        this.hash = hash
        this.subTrees = null
        this.leafs = null
    }

    constructor(left: MerkleTree, right: MerkleTree) {
        this.hash = null
        this.subTrees = Pair(left, right)
        this.leafs = null
    }

    constructor(left: Leaf, right: Leaf? = null) {
        this.hash = null
        this.subTrees = null
        this.leafs = Pair(left, right)
    }

    override fun sha3(): ByteArray {
        val md = MessageDigest.getInstance("SHA-3")
        return when {
            subTrees != null -> md.digest(subTrees.first.sha3() + subTrees.first.sha3())
            leafs != null -> md.digest(
                if (leafs.second != null) {
                    leafs.first.sha3() + leafs.second!!.sha3()
                } else {
                    leafs.first.sha3()
                }
            )
            //Lastly, when there's no underlying data, use provided hash
            hash != null -> hash.sha3()
            else -> throw Exception("Error generating sha3 value")
        }
    }

    override fun toString(): String {
        return if (hash != null) {
            hash.value
        } else if (subTrees != null) {
            "${subTrees.first} ${subTrees.second}"
        } else if (leafs != null) {
            "${leafs.first} ${leafs.second}"
        } else ""
    }

    companion object {
        fun build(data: List<String>): MerkleTree {
            var merkleNodes =
                data.chunked(2).map { MerkleTree(Leaf(it[0]), it.getOrNull(1)?.let { Leaf(it) }) }
            while (merkleNodes.size > 1) {
                merkleNodes = merkleNodes.chunked(2).map { if (it.size > 1) MerkleTree(it[0], it[1]) else it[0] }
            }
            return merkleNodes[0]
        }
    }
}