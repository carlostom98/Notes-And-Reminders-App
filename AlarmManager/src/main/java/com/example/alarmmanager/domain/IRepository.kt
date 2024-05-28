package com.example.alarmmanager.domain

interface IRepository {
    fun schedule()
    fun cancel()
}