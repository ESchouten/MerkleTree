package com.erikschouten.merkletree

import org.bouncycastle.jcajce.provider.digest.SHA3
import java.io.Serializable

data class MerkleTree<out A : MerkleNode, out B : MerkleNode>(val left: A, val right: B) : MerkleNode,
  Serializable {

  override fun sha3() = SHA3.Digest224().digest(left.sha3() + right.sha3())!!

  companion object {
    fun build(data: List<MerkleNode>): MerkleNode {
      var merkleNodes = data
      while (merkleNodes.size > 1) {
        merkleNodes = merkleNodes.chunked(2).map {
          if (it.getOrNull(1) != null) {
            MerkleTree(it[0], it[1])
          } else {
            it[0]
          }
        }
      }
      return merkleNodes[0]
    }
  }
}
