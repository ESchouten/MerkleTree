package com.erikschouten.merkletree.classes

import org.bouncycastle.util.encoders.Base64

data class Hash(val value: String) : MerkleNode {

  constructor(bytes: ByteArray): this(Base64.toBase64String(bytes))

  override fun sha3() = org.bouncycastle.util.encoders.Base64.decode(value)!!
}
