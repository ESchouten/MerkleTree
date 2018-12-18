package com.erikschouten.merkletree

import com.erikschouten.merkletree.leaf.Data
import com.erikschouten.merkletree.leaf.Hash
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class MerkleTreeTests {

    @Test
    fun enableDefaultTypingTest() {
        val tradelaneData = TestTradelane("Hauwert", TestTransporter("Erik", 1))
        val json = jacksonObjectMapper().enableDefaultTyping().writeValueAsString(tradelaneData)
        println(json)
    }

    @Test
    fun jsonConversionTest() {
        val billOfLading = Data("data:application/pdf;base64,BillOfLading")
        val commercialInvoice = Data("data:application/pdf;base64,CommercialInvoice")
        val packingList = Data("data:application/pdf;base64,PackingList")
        val tradelaneData = Data(TestTradelane("Hauwert", TestTransporter("Erik", 1)))

        val merkleTree = MerkleTree.build(
            listOf(
                billOfLading,
                commercialInvoice,
                packingList,
                tradelaneData
            )
        )

        val objectmapper = jacksonObjectMapper().enableDefaultTyping()

        val json = objectmapper.writeValueAsString(merkleTree)
        println(json)
        val obj = objectmapper.readValue<MerkleTree>(json)

        assertEquals(merkleTree, obj)

    }

    @Test
    fun dataTypeTest() {
        val tradelaneData = Data(TestTradelane("Hauwert", TestTransporter("Erik", 1)))
        assert(tradelaneData.get() is TestTradelane)
    }

    @Test
    fun evenTreeTest() {
        val billOfLading = Data("data:application/pdf;base64,BillOfLading")
        val commercialInvoice = Data("data:application/pdf;base64,CommercialInvoice")
        val packingList = Data("data:application/pdf;base64,PackingList")
        val tradelaneData = Data(TestTradelane("Hauwert", TestTransporter("Erik", 1)))

        MerkleTree.build(
            listOf(
                billOfLading,
                commercialInvoice,
                packingList,
                tradelaneData
            )
        )
    }

    @Test
    fun unevenTreeTest() {
        val billOfLading = Data("data:application/pdf;base64,BillOfLading")
        val commercialInvoice = Data("data:application/pdf;base64,CommercialInvoice")
        val packingList = Data("data:application/pdf;base64,PackingList")
        val tradelaneData = Data(TestTradelane("Hauwert", TestTransporter("Erik", 1)))
        val waybill = Data("data:application/pdf;base64,Waybill")

        MerkleTree.build(
            listOf(
                billOfLading,
                commercialInvoice,
                packingList,
                tradelaneData,
                waybill
            )
        )
    }

    @Test
    fun singleNodeTest() {
        val billOfLading = Data("data:application/pdf;base64,BillOfLading")
        MerkleTree.build(listOf(billOfLading))
    }

    @Test
    fun partialTreeTest() {
        val billOfLading = Data("data:application/pdf;base64,BillOfLading")
        val commercialInvoice = Data("data:application/pdf;base64,CommercialInvoice")
        val packingList = Data("data:application/pdf;base64,PackingList")
        val tradelaneData = Data(TestTradelane("Hauwert", TestTransporter("Erik", 1)))

        val merkleTree = MerkleTree.build(
            listOf(
                billOfLading,
                commercialInvoice,
                packingList,
                tradelaneData
            )
        )

        val merkleTreeWithHash = MerkleTree.build(
            listOf(
                Hash(billOfLading),
                commercialInvoice,
                packingList,
                Hash(tradelaneData)
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
        val tradelaneData = Data(TestTradelane("Hauwert", TestTransporter("Erik", 1)))

        val merkletree = MerkleTree.build(
            listOf(
                Data(billOfLading),
                Data(commercialInvoice),
                Data(packingList),
                Data(tradelaneData)
            )
        )

        val merkletree2 = MerkleTree.build(
            listOf(
                Data(billOfLading),
                Data(commercialInvoice),
                Data(packingList),
                Data(tradelaneData)
            )
        )

        assertNotEquals(Hash(merkletree), Hash(merkletree2))
    }

    @Test
    fun onlyHashesTest() {
        val billOfLading = Data("data:application/pdf;base64,BillOfLading")
        val commercialInvoice = Data("data:application/pdf;base64,CommercialInvoice")
        val packingList = Data("data:application/pdf;base64,PackingList")
        val tradelaneData = Data(TestTradelane("Hauwert", TestTransporter("Erik", 1)))

        val merkleTree = MerkleTree.build(
            listOf(
                billOfLading,
                commercialInvoice,
                packingList,
                tradelaneData
            )
        )

        val merkleTreeWithHash = MerkleTree.build(
            listOf(
                Hash(billOfLading),
                Hash(commercialInvoice),
                Hash(packingList),
                Hash(tradelaneData)
            )
        )

        assertEquals(
            Hash(merkleTree),
            Hash(merkleTreeWithHash)
        )
    }
}
