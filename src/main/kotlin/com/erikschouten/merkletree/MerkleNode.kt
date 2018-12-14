package com.erikschouten.merkletree

interface MerkleNode {
  fun sha3(): ByteArray
}
