package com.erikschouten.merkletree

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
interface MerkleNode {
    fun sha3(): ByteArray
}
