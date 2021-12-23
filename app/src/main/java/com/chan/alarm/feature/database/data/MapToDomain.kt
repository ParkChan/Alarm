package com.chan.alarm.feature.database.data

interface MapToDomain<T> {
    fun mapToDomain(): T
}