package eu.pixelstube.cloud.jsonlib

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject

/**

This file was created by Max H. (Haizoooon)
Date: 25.05.2021
Copyright© 2021 Max H.

 **/
fun JsonObject.getOrNull(property: String): JsonElement? {
    val jsonElement = get(property)
    return if (jsonElement is JsonNull) null else jsonElement
}
