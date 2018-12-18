package com.erikschouten.merkletree

import org.bouncycastle.jcajce.provider.digest.SHA3

class MerkleTree private constructor(val left: MerkleNode, val right: MerkleNode) : MerkleNode {

    override fun sha3() = SHA3.Digest224().digest(left.sha3() + right.sha3())!!

    companion object {
        fun build(data: List<MerkleNode>): MerkleNode {
            var merkleNodes = data
            //Combine nodes in parent nodes until the root is generated
            while (merkleNodes.size > 1) {
                //Put two nodes into one parent node
                merkleNodes = merkleNodes.chunked(2).map {
                    if (it.getOrNull(1) != null) {
                        MerkleTree(it[0], it[1])
                    } else {
                        //If one is left, return object instead of putting in another node
                        it[0]
                    }
                }
            }
            //Return root
            return merkleNodes.first()
        }
    }

    override fun toString(): String {
        return "MerkleTree(left=$left, right=$right)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MerkleTree

        if (left != other.left) return false
        if (right != other.right) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + right.hashCode()
        return result
    }
}
