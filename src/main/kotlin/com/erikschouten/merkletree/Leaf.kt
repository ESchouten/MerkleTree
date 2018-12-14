package com.erikschouten.merkletree

import com.erikschouten.merkletree.classes.Hash
import com.erikschouten.merkletree.classes.MerkleNode
import org.bouncycastle.jcajce.provider.digest.SHA3
import java.util.*

class Leaf : MerkleNode {

  val hash: Hash?
  val data: Pair<String, UUID>?

  constructor(hash: Hash) {
    this.hash = hash
    this.data = null
  }

  constructor(data: String) {
    this.hash = null
    this.data = Pair(data, UUID.randomUUID())
  }

  override fun sha3(): ByteArray {
    return when {
      data != null -> {
        SHA3.Digest224().digest("${data.first}${data.second}".toByteArray())
      }
      hash != null -> hash.sha3()
      else -> throw Exception("Error generating sha3 value")
    }
  }
}
