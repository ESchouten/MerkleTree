package com.erikschouten.merkletree.leaf

import com.erikschouten.merkletree.Merkle
import org.bouncycastle.util.encoders.Base64

class Hash private constructor(val value: String): Merkle {

    private constructor(bytes: ByteArray) : this(Base64.toBase64String(bytes))
    constructor(merkle: Merkle) : this(merkle.sha3())

    override fun sha3() = Base64.decode(value)!!

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Hash

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "Hash(value='$value')"
    }


}
