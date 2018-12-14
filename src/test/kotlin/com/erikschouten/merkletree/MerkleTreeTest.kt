package com.erikschouten.merkletree

import org.junit.Test

class MerkleTreeTest {

    @Test
    fun merkleTreeTest() {
        val billOfLading = "data:application/pdf;base64,BillOfLading"
        val commercialInvoice = "data:application/pdf;base64,CommercialInvoice"
        val packingList = "data:application/pdf;base64,PackingList"
        val letterOfCredit = "data:application/pdf;base64,LetterOfCredit"

        val merkleTree = MerkleTree.build(listOf(billOfLading, commercialInvoice, packingList, letterOfCredit))

        println(merkleTree)
    }

    @Test
    fun merkleTreeUnevenTest() {
        val billOfLading = "data:application/pdf;base64,BillOfLading"
        val commercialInvoice = "data:application/pdf;base64,CommercialInvoice"
        val packingList = "data:application/pdf;base64,PackingList"
        val letterOfCredit = "data:application/pdf;base64,LetterOfCredit"
        val waybill = "data:application/pdf;base64,Waybill"

        val merkleTree = MerkleTree.build(listOf(billOfLading, commercialInvoice, packingList, letterOfCredit, waybill))

        println(merkleTree)
    }
}
