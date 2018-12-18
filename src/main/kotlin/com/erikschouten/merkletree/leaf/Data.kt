package com.erikschouten.merkletree.leaf

import com.erikschouten.merkletree.MerkleNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.bouncycastle.jcajce.provider.digest.SHA3
import java.util.*

class Data private constructor(
    val value: String,
    val className: String,
    val nonce: String = UUID.randomUUID().toString()
) : MerkleNode {

    constructor(obj: Any) : this(
        value = jacksonObjectMapper().writeValueAsString(obj),
        className = obj::class.qualifiedName!!
    )

    fun get() = jacksonObjectMapper().readValue(value, Class.forName(className))!!

    override fun sha3() = SHA3.Digest224().digest((value + nonce).toByteArray())!!

    override fun toString(): String {
        return "Data(value='$value', className=$className, nonce=$nonce)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Data

        if (value != other.value) return false
        if (className != other.className) return false
        if (nonce != other.nonce) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + className.hashCode()
        result = 31 * result + nonce.hashCode()
        return result
    }


}
