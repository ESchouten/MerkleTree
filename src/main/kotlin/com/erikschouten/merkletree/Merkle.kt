package com.erikschouten.merkletree

import java.io.Serializable

interface Merkle : Serializable, Comparable<Merkle> {
    fun sha3(): ByteArray

    override fun compareTo(other: Merkle): Int {
        return compare(this.sha3(), other.sha3())
    }

    private fun compare(one: ByteArray, two: ByteArray): Int {
        val thisList = one.asList()
        val otherList = two.asList()
        for (i in 0 until Math.min(thisList.size, otherList.size)) {
            val elem1 = thisList[i]
            val elem2 = otherList[i]

            compareValues(elem1, elem2).let {
                if (it != 0) return it
            }
        }
        return compareValues(thisList.size, otherList.size)
    }
}
