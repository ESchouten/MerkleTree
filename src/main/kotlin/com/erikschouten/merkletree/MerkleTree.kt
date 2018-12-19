package com.erikschouten.merkletree

import java.util.*

data class MerkleTree(val merkle: Merkle) : Merkle {

    override fun sha3() = merkle.sha3()

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