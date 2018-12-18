package com.erikschouten.merkletree

interface IMerkleNode {
    fun sha3(): ByteArray
}
