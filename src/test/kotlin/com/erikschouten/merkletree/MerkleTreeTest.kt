package com.erikschouten.merkletree

import com.erikschouten.merkletree.classes.Hash
import org.junit.Test
import kotlin.test.assertEquals

class MerkleTreeTest {

    @Test
    fun merkleTreeTest() {
        val billOfLading = "data:application/pdf;base64,BillOfLading"
        val commercialInvoice = "data:application/pdf;base64,CommercialInvoice"
        val packingList = "data:application/pdf;base64,PackingList"
        val letterOfCredit = "data:application/pdf;base64,LetterOfCredit"

        val merkleTree = MerkleTree.build(
            listOf(
                Leaf(billOfLading),
                Leaf(commercialInvoice),
                Leaf(packingList),
                Leaf(letterOfCredit)
            )
        )

        println(Hash(merkleTree.sha3()))
    }

    @Test
    fun merkleTreeUnevenTest() {
        val billOfLading = "data:application/pdf;base64,BillOfLading"
        val commercialInvoice = "data:application/pdf;base64,CommercialInvoice"
        val packingList = "data:application/pdf;base64,PackingList"
        val letterOfCredit = "data:application/pdf;base64,LetterOfCredit"
        val waybill = "data:application/pdf;base64,Waybill"

        val merkleTree = MerkleTree.build(
            listOf(
                Leaf(billOfLading),
                Leaf(commercialInvoice),
                Leaf(packingList),
                Leaf(letterOfCredit),
                Leaf(waybill)
            )
        )

        println(Hash(merkleTree.sha3()))
    }

    @Test
    fun singleMerkleNodeTest() {
        val billOfLading = "data:application/pdf;base64,BillOfLading"

        val merkleTree = MerkleTree.build(listOf(Leaf(billOfLading)))

        println(Hash(merkleTree.sha3()))
    }

    @Test
    fun partialTreeTest() {
        val billOfLading = Leaf("data:application/pdf;base64,BillOfLading")
        val commercialInvoice = Leaf("data:application/pdf;base64,CommercialInvoice")
        val packingList = Leaf("data:application/pdf;base64,PackingList")
        val letterOfCredit = Leaf("data:application/pdf;base64,LetterOfCredit")


        val merkleTree = MerkleTree.build(
            listOf(
                billOfLading,
                commercialInvoice,
                packingList,
                letterOfCredit
            )
        )

        val merkleTreeWithHash = MerkleTree.build(
            listOf(
                billOfLading,
                commercialInvoice,
                packingList,
                Leaf(Hash(letterOfCredit.sha3()))
            )
        )

        val hash1 = Hash(merkleTree.sha3())
        val hash2 = Hash(merkleTreeWithHash.sha3())

        assertEquals(hash1, hash2)
    }
}
