package com.erikschouten.merkletree.leaf

import com.erikschouten.merkletree.IMerkleNode
import org.bouncycastle.util.encoders.Base64

data class Hash(
    val value: String
) : IMerkleNode {

    constructor(bytes: ByteArray) : this(Base64.toBase64String(bytes))
    constructor(merkleNode: IMerkleNode) : this(merkleNode.sha3())

    override fun sha3() = Base64.decode(value)!!
}
