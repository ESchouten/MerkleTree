package com.erikschouten.merkletree

import com.erikschouten.merkletree.leaf.SecureData
import java.util.*

data class MerkleTree(val merkle: Merkle) : Merkle {

    override fun sha3() = merkle.sha3()

    fun findData(): List<Any> {
        val data = mutableListOf<Any>()
        val stack = Stack<MerkleNode>()

        if (merkle is MerkleNode) {
            stack.push(merkle)
        } else if (merkle is SecureData) {
            data.add(merkle.get())
        }

        while (stack.isNotEmpty()) {
            val current = stack.pop()

            val merkles = listOf(current.left, current.right)

            for (merkle in merkles) {
                if (merkle is MerkleNode) {
                    stack.push(merkle)
                } else if (merkle is SecureData) {
                    data.add(merkle.get())
                }
            }
        }

        return data
    }

    companion object {
        fun build(data: List<Merkle>): MerkleTree {
            var merkleNodes = data
            //Combine nodes in parent nodes until the root is generated
            while (merkleNodes.size > 1) {
                //Put two nodes into one parent node
                merkleNodes = merkleNodes.chunked(2).map {
                    if (it.getOrNull(1) != null) {
                        MerkleNode(it[0], it[1])
                    } else {
                        //If one is left, return object instead of putting in another node
                        it[0]
                    }
                }
            }
            //Return root
            return MerkleTree(merkleNodes.first())
        }
    }
}