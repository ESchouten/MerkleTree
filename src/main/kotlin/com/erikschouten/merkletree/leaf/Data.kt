package com.erikschouten.merkletree.leaf

import com.erikschouten.merkletree.MerkleNode
import org.bouncycastle.jcajce.provider.digest.SHA3
import java.util.*

class Data(value: String) : MerkleNode {

  val value: Pair<String, UUID> = Pair(value, UUID.randomUUID())

  override fun sha3() = SHA3.Digest224().digest((value.first + value.second.toString()).toByteArray())!!

  override fun toString(): String {
    return "Data(value=$value)"
  }
}
