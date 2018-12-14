package com.erikschouten.merkletree

import org.bouncycastle.jcajce.provider.digest.SHA3
import java.io.Serializable

class MerkleTree private constructor(val left: MerkleNode, val right: MerkleNode) : MerkleNode {

  override fun sha3() = SHA3.Digest224().digest(left.sha3() + right.sha3())!!

  override fun toString(): String {
    return "MerkleTree(left=$left, right=$right)"
  }

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
      return merkleNodes.first()
    }
  }
}
