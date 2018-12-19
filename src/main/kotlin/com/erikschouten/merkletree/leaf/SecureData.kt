package com.erikschouten.merkletree.leaf

import com.erikschouten.merkletree.IMerkleNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.bouncycastle.jcajce.provider.digest.SHA3
import java.util.*

class SecureData private constructor(
    val value: String,
    val nonce: String = UUID.randomUUID().toString()
) : IMerkleNode {

    constructor(obj: Any) : this(
        value = jacksonObjectMapper().enableDefaultTyping().writeValueAsString(Data(obj))
    )

    fun get() = jacksonObjectMapper().enableDefaultTyping().readValue(value, Data::class.java)!!.value

    override fun sha3() = SHA3.Digest224().digest((value + nonce).toByteArray())!!

    override fun toString(): String {
        return "SecureData(value='$value', nonce=$nonce)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SecureData

        if (value != other.value) return false
        if (nonce != other.nonce) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + nonce.hashCode()
        return result
    }

    //Used to wrap object for JSon class definition
    class Data(val value: Any)
}
