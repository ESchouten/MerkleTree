package com.erikschouten.merkletree

import com.erikschouten.merkletree.leaf.Data
import com.erikschouten.merkletree.leaf.Hash
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class MerkleTreeTest {

    @Test
    fun merkleTreeTest() {
        val billOfLading = "data:application/pdf;base64,BillOfLading"
        val commercialInvoice = "data:application/pdf;base64,CommercialInvoice"
        val packingList = "data:application/pdf;base64,PackingList"
        val letterOfCredit = "data:application/pdf;base64,LetterOfCredit"

        MerkleTree.build(
            listOf(
                Data(billOfLading),
                Data(commercialInvoice),
                Data(packingList),
                Data(letterOfCredit)
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
                Data(billOfLading),
                Data(commercialInvoice),
                Data(packingList),
                Data(letterOfCredit),
                Data(waybill)
            )
        )
    }

    @Test
    fun singleMerkleNodeTest() {
        val billOfLading = "data:application/pdf;base64,BillOfLading"
        MerkleTree.build(listOf(Data(billOfLading)))
    }

    @Test
    fun partialTreeTest() {
        val billOfLading = Data("data:application/pdf;base64,BillOfLading")
        val commercialInvoice = Data("data:application/pdf;base64,CommercialInvoice")
        val packingList = Data("data:application/pdf;base64,PackingList")
        val letterOfCredit = Data("data:application/pdf;base64,LetterOfCredit")

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
                Hash(letterOfCredit)
            )
        )

        assertEquals(
            Hash(merkleTree),
            Hash(merkleTreeWithHash)
        )
    }

    @Test
    fun sameDataDifferentNonceTest() {
        val billOfLading = "data:application/pdf;base64,BillOfLading"
        val commercialInvoice = "data:application/pdf;base64,CommercialInvoice"
        val packingList = "data:application/pdf;base64,PackingList"
        val letterOfCredit = "data:application/pdf;base64,LetterOfCredit"

        val merkletree = MerkleTree.build(
            listOf(
                Data(billOfLading),
                Data(commercialInvoice),
                Data(packingList),
                Data(letterOfCredit)
            )
        )

        val merkletree2 = MerkleTree.build(
            listOf(
                Data(billOfLading),
                Data(commercialInvoice),
                Data(packingList),
                Data(letterOfCredit)
            )
        )

        assertNotEquals(Hash(merkletree), Hash(merkletree2))
    }
}
