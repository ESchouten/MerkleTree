package com.erikschouten.merkletree.leaf

import com.erikschouten.merkletree.MerkleNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.bouncycastle.jcajce.provider.digest.SHA3
import java.util.*

class Data private constructor(
    val value: String,
    val className: String,
    val nonce: UUID = UUID.randomUUID()
) : MerkleNode {

    constructor(obj: Any) : this(
        value = jacksonObjectMapper().writeValueAsString(obj),
        className = obj::class.qualifiedName!!
    )

    fun get() = jacksonObjectMapper().readValue(value, Class.forName(className))!!

    override fun sha3() = SHA3.Digest224().digest((value + nonce.toString()).toByteArray())!!

    override fun toString(): String {
        return "Data(value='$value', className=$className, nonce=$nonce)"
    }
}
