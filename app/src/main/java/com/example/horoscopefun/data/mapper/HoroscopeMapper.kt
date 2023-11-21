package com.example.horoscopefun.data.mapper

import com.example.horoscopefun.data.remote.dto.HoroscopeDetailsDto
import com.example.horoscopefun.data.remote.dto.list.HoroscopeDto
import com.example.horoscopefun.domian.model.MHoroscope
import com.example.horoscopefun.domian.model.MHoroscopeDetail

/**
 * @author : Mingaleev D
 * @data : 20.11.2023
 */

fun HoroscopeDto.toDomain() = MHoroscope(
    icon = icon,
    id = id,
    name = name
)

fun List<HoroscopeDto>.toDomainList() = map { it.toDomain() }

fun HoroscopeDetailsDto.toDomainDetails() = MHoroscopeDetail(
    date = date,
    horoscope = horoscope,
    icon = icon,
    id = id,
    sign = sign
)
