package com.erikschouten.merkletree

import com.erikschouten.merkletree.classes.Hash
import com.erikschouten.merkletree.classes.Hashable
import java.security.MessageDigest
import java.util.*

class Leaf : Hashable {

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
        val md = MessageDigest.getInstance("SHA-3")
        md.digest("${data.first}${data.second}".toByteArray())
      }
      hash != null -> hash.sha3()
      else -> throw Exception("Error generating sha3 value")
    }
  }
}
