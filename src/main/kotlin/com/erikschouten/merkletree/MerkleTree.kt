package com.erikschouten.merkletree

import com.erikschouten.merkletree.classes.MerkleNode
import org.bouncycastle.jcajce.provider.digest.SHA3
import java.io.Serializable

data class MerkleTree<out A : MerkleNode, out B : MerkleNode>(val first: A, val second: B) : MerkleNode,
  Serializable {

  override fun sha3() = SHA3.Digest224().digest(first.sha3() + second.sha3())!!

  companion object {
    fun build(data: List<Leaf>): MerkleNode {
      var merkleNodes: List<MerkleNode> = data
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
