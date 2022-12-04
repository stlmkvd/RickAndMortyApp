package com.stlmkvd.rickandmorty.adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.net.URL

class UrlTypeAdapter : TypeAdapter<URL>() {
    override fun write(out: JsonWriter?, value: URL?) {
        out?.apply {
            beginObject()
            val strValue = value?.toString()
            name("url").value(strValue)
            endObject()
        }
    }

    override fun read(`in`: JsonReader?): URL? {
        var url: URL? = null
        `in`?.apply {
            beginObject()
            val value = nextString()
            if (value != null && value.isNotBlank()) url = URL(value)
            endObject()
        }
        return url
    }
}