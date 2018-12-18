package com.erikschouten.merkletree

data class MerkleTree(val merkleNode: IMerkleNode) : IMerkleNode {

    override fun sha3() = merkleNode.sha3()

    companion object {
        fun build(data: List<IMerkleNode>): MerkleTree {
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