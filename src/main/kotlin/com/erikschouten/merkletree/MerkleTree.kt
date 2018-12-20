package com.erikschouten.merkletree

import com.erikschouten.merkletree.leaf.SecureData
import java.util.*

data class MerkleTree(val merkle: Merkle) : Merkle {

    override fun sha3() = merkle.sha3()

    fun contains(merkle: Merkle) = contains(merkle.sha3())
    fun contains(sha3: ByteArray): Boolean {
        val stack = Stack<MerkleNode>()

        if (merkle is MerkleNode) {
            if (merkle.sha3().contentEquals(sha3)) return true
            stack.push(merkle)
        } else if (merkle.sha3().contentEquals(sha3)) {
            return true
        }

        while (stack.isNotEmpty()) {
            val current = stack.pop()

            for (merkle in listOf(current.left, current.right)) {
                if (merkle is MerkleNode) {
                    if (merkle.sha3().contentEquals(sha3)) return true
                    stack.push(merkle)
                } else if (merkle.sha3().contentEquals(sha3)) {
                    return true
                }
            }
        }
        return false
    }

    fun findData(): List<Any> = findSecureData().map { it.get() }
    fun findSecureData(): List<SecureData> {
        val data = mutableListOf<SecureData>()
        val stack = Stack<MerkleNode>()

        if (merkle is MerkleNode) {
            stack.push(merkle)
        } else if (merkle is SecureData) {
            data.add(merkle)
        }

        while (stack.isNotEmpty()) {
            val current = stack.pop()

            for (merkle in listOf(current.left, current.right)) {
                if (merkle is MerkleNode) {
                    stack.push(merkle)
                } else if (merkle is SecureData) {
                    data.add(merkle)
                }
            }
        }

        return data
    }

    companion object {
        fun build(data: List<Merkle>): MerkleTree {
            //Sorting Merkles to ensure same hash when an identical tree is created
            var merkleNodes = data.sorted()
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