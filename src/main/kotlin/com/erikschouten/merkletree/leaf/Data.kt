package com.erikschouten.merkletree.leaf

import com.erikschouten.merkletree.MerkleNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.bouncycastle.jcajce.provider.digest.SHA3
import java.util.*

class Data(obj: Any) : MerkleNode {

    private val value: String = jacksonObjectMapper().writeValueAsString(obj)
    private val className = obj::class.qualifiedName
    private val nonce: UUID = UUID.randomUUID()

    fun get() = jacksonObjectMapper().readValue(value, Class.forName(className))!!

    override fun sha3() = SHA3.Digest224().digest((value + nonce.toString()).toByteArray())!!

    override fun toString(): String {
        return "Data(value='$value', className=$className, nonce=$nonce)"
    }
}
