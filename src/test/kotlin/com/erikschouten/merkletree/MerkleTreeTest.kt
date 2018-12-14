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

        MerkleTree.build(
            listOf(
                Leaf(billOfLading),
                Leaf(commercialInvoice),
                Leaf(packingList),
                Leaf(letterOfCredit)
            )
        )
    }

    @Test
    fun merkleTreeUnevenTest() {
        val billOfLading = "data:application/pdf;base64,BillOfLading"
        val commercialInvoice = "data:application/pdf;base64,CommercialInvoice"
        val packingList = "data:application/pdf;base64,PackingList"
        val letterOfCredit = "data:application/pdf;base64,LetterOfCredit"
        val waybill = "data:application/pdf;base64,Waybill"

        MerkleTree.build(
            listOf(
                Leaf(billOfLading),
                Leaf(commercialInvoice),
                Leaf(packingList),
                Leaf(letterOfCredit),
                Leaf(waybill)
            )
        )
    }

    @Test
    fun singleMerkleNodeTest() {
        val billOfLading = "data:application/pdf;base64,BillOfLading"
        MerkleTree.build(listOf(Leaf(billOfLading)))
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

        assertEquals(Hash(merkleTree.sha3()), Hash(merkleTreeWithHash.sha3()))
    }
}
