package com.erikschouten.merkletree.leaf

import com.erikschouten.merkletree.MerkleNode
import org.bouncycastle.util.encoders.Base64

data class Hash(val value: String) : MerkleNode {

  constructor(bytes: ByteArray) : this(Base64.toBase64String(bytes))

  override fun sha3() = Base64.decode(value)!!
}
