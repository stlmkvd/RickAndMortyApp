package com.stlmkvd.rickandmorty.adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.net.URL


object NullableUrlTypeAdapter : TypeAdapter<URL?>() {
    override fun write(out: JsonWriter, value: URL?) {
        out.value(value?.toString())
    }

    override fun read(input: JsonReader): URL? {
        if (input.peek() == JsonToken.NULL) {
            input.nextNull()
            return null
        }
        val stringUrl = input.nextString()
        if (stringUrl.isBlank()) return null
        return URL(stringUrl)
    }
}

object NullableStringTypeAdapter : TypeAdapter<String?>() {
    override fun write(out: JsonWriter, value: String?) {
        out.value(value)
    }

    override fun read(input: JsonReader): String? {
        if (input.peek() == JsonToken.NULL){
            input.nextNull()
            return null
        }
        val string = input.nextString()
        if (string.isBlank()) return null
        return string
    }
}