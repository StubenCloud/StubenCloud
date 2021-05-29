package eu.pixelstube.cloud.jsonlib

import com.google.gson.*
import java.lang.reflect.Type

/**

This file was created by Max H. (Haizoooon)
Date: 25.05.2021
Copyright© 2021 Max H.

 **/
class JsonLibSerializer : JsonSerializer<JsonLib>, JsonDeserializer<JsonLib> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): JsonLib {
        return JsonLib.fromJsonElement(json)
    }

    override fun serialize(src: JsonLib, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return src.jsonElement
    }


}
