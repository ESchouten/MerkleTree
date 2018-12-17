package com.erikschouten.merkletree.leaf

import com.erikschouten.merkletree.MerkleNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.bouncycastle.jcajce.provider.digest.SHA3
import java.util.*

data class Data(val value: String) : MerkleNode {

  private val nonce: UUID = UUID.randomUUID()

  constructor(obj: Any) : this(jacksonObjectMapper().writeValueAsString(obj))

  override fun sha3() = SHA3.Digest224().digest((value + nonce.toString()).toByteArray())!!
}
