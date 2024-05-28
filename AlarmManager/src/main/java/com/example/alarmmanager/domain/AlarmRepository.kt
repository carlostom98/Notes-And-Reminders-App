package com.example.alarmmanager.domain

import com.example.alarmmanager.data.interfaces.IAlarmScheduler

class AlarmRepository(private val alarmScheduler: IAlarmScheduler) : IRepository {

    override fun schedule() {
        alarmScheduler.schedule()
    }

    override fun cancel() {
        alarmScheduler.cancel()
    }
}