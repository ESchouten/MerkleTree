package com.erikschouten.merkletree.leaf

import com.erikschouten.merkletree.Merkle
import org.bouncycastle.util.encoders.Base64

data class Hash constructor(val value: String) : Merkle {

    constructor(bytes: ByteArray) : this(Base64.toBase64String(bytes))
    constructor(merkle: Merkle) : this(merkle.sha3())

    override fun sha3() = Base64.decode(value)!!
}
