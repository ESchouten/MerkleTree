package com.erikschouten.merkletree.classes

interface MerkleNode {
  fun sha3(): ByteArray
}
