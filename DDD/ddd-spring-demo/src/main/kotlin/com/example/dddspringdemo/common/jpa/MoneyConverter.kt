package com.example.dddspringdemo.common.jpa

import com.example.dddspringdemo.common.model.Money
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class MoneyConverter : AttributeConverter<Money, Int> {
    override fun convertToDatabaseColumn(money: Money): Int {
        return money.value
    }

    override fun convertToEntityAttribute(value: Int): Money {
        return Money(value)
    }
}