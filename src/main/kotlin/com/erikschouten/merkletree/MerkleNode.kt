package com.erikschouten.merkletree

import org.bouncycastle.jcajce.provider.digest.SHA3

data class MerkleNode(val left: IMerkleNode, val right: IMerkleNode) : IMerkleNode {

    override fun sha3() = SHA3.Digest224().digest(left.sha3() + right.sha3())!!
}
