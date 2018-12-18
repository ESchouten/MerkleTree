package com.erikschouten.merkletree

data class TestTradelane(val id: String, val transporter: TestTransporter)
data class TestTransporter(val name: String, val employeeCount: Int)