package ru.jjba.jr2.domain.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Example(
        @PrimaryKey
        val id: String,
        val exampleJp: String,
        val exampleFurigana: String,
        val exampleTranslate: String,
        val interpretation: String
) {
    override fun toString() = "$id $exampleJp $exampleFurigana $exampleTranslate"
}