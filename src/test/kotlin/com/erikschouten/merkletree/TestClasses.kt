package com.erikschouten.merkletree

data class TestTradelane(val id: String, val transporter: ITransporter)
data class TestTransporter(val name: String, val employeeCount: Int): ITransporter
interface ITransporter