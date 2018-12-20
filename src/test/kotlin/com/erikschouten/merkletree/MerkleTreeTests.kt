package com.erikschouten.merkletree

import com.erikschouten.merkletree.leaf.Hash
import com.erikschouten.merkletree.leaf.SecureData
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
        val obj = jacksonObjectMapper().enableDefaultTyping().readValue<TestTradelane>(json)

        assertEquals(
            tradelaneData,
            obj,
            "Data (de)serialisation gone wrong"
        )
    }

    @Test
    fun jsonConversionTest() {
        val billOfLading = SecureData("data:application/pdf;base64,BillOfLading")
        val commercialInvoice = SecureData("data:application/pdf;base64,CommercialInvoice")
        val packingList = Hash(SecureData("data:application/pdf;base64,PackingList"))
        val tradelaneData = SecureData(TestTradelane("Hauwert", TestTransporter("Erik", 1)))

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
        val obj = objectmapper.readValue<MerkleTree>(json)

        assertEquals(
            merkleTree,
            obj,
            "Data (de)serialisation gone wrong"
        )
    }

    @Test
    fun dataTypeTest() {
        val tradelaneData = SecureData(TestTradelane("Hauwert", TestTransporter("Erik", 1)))

        assert(tradelaneData.get() is TestTradelane)
    }

    @Test
    fun evenTreeTest() {
        val billOfLading = SecureData("data:application/pdf;base64,BillOfLading")
        val commercialInvoice = SecureData("data:application/pdf;base64,CommercialInvoice")
        val packingList = SecureData("data:application/pdf;base64,PackingList")
        val tradelaneData = SecureData(TestTradelane("Hauwert", TestTransporter("Erik", 1)))

        val dataList = listOf(
            billOfLading,
            commercialInvoice,
            packingList,
            tradelaneData
        )

        val merkleTree = MerkleTree.build(dataList)

        assertEquals(
            dataList.size,
            merkleTree.findData().size,
            "Incorrect/incomplete data found (even tree)"
        )

        assert(dataList.map { it.get() }.containsAll(merkleTree.findData()))
    }

    @Test
    fun unevenTreeTest() {
        val billOfLading = SecureData("data:application/pdf;base64,BillOfLading")
        val commercialInvoice = SecureData("data:application/pdf;base64,CommercialInvoice")
        val packingList = SecureData("data:application/pdf;base64,PackingList")
        val tradelaneData = SecureData(TestTradelane("Hauwert", TestTransporter("Erik", 1)))
        val waybill = SecureData("data:application/pdf;base64,Waybill")

        val dataList = listOf(
            billOfLading,
            commercialInvoice,
            packingList,
            tradelaneData,
            waybill
        )

        val merkleTree = MerkleTree.build(dataList)

        assertEquals(
            dataList.size,
            merkleTree.findData().size,
            "Incorrect/incomplete data found (uneven tree)"
        )

        assert(dataList.map { it.get() }.containsAll(merkleTree.findData()))
    }

    @Test
    fun singleNodeTest() {
        val billOfLading = SecureData("data:application/pdf;base64,BillOfLading")
        MerkleTree.build(listOf(billOfLading))
    }

    @Test
    fun partialTreeTest() {
        val billOfLading = SecureData("data:application/pdf;base64,BillOfLading")
        val commercialInvoice = SecureData("data:application/pdf;base64,CommercialInvoice")
        val packingList = SecureData("data:application/pdf;base64,PackingList")
        val tradelaneData = SecureData(TestTradelane("Hauwert", TestTransporter("Erik", 1)))

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

        assertEquals(
            2,
            merkleTreeWithHash.findData().size,
            "Incorrect/incomplete data found (partial tree)"
        )

        assert(listOf(commercialInvoice, packingList).map { it.get() }.containsAll(merkleTreeWithHash.findData()))
    }

    @Test
    fun sameDataDifferentNonceTest() {
        val billOfLading = "data:application/pdf;base64,BillOfLading"
        val commercialInvoice = "data:application/pdf;base64,CommercialInvoice"
        val packingList = "data:application/pdf;base64,PackingList"
        val tradelaneData = SecureData(TestTradelane("Hauwert", TestTransporter("Erik", 1)))

        val merkletree = MerkleTree.build(
            listOf(
                SecureData(billOfLading),
                SecureData(commercialInvoice),
                SecureData(packingList),
                SecureData(tradelaneData)
            )
        )

        val merkletree2 = MerkleTree.build(
            listOf(
                SecureData(billOfLading),
                SecureData(commercialInvoice),
                SecureData(packingList),
                SecureData(tradelaneData)
            )
        )

        assertNotEquals(
            Hash(merkletree),
            Hash(merkletree2),
            "Hash not different despite of different nonce"
        )
    }

    @Test
    fun onlyHashesTest() {
        val billOfLading = SecureData("data:application/pdf;base64,BillOfLading")
        val commercialInvoice = SecureData("data:application/pdf;base64,CommercialInvoice")
        val packingList = SecureData("data:application/pdf;base64,PackingList")
        val tradelaneData = SecureData(TestTradelane("Hauwert", TestTransporter("Erik", 1)))

        val dataList = listOf(
            billOfLading,
            commercialInvoice,
            packingList,
            tradelaneData
        )

        val merkleTree = MerkleTree.build(dataList)

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

        assertEquals(0, merkleTreeWithHash.findData().size)

        assertEquals(dataList.size, merkleTree.findData().size)
    }
}
