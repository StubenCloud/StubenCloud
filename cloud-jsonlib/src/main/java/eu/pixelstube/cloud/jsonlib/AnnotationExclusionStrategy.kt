package eu.pixelstube.cloud.jsonlib

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

/**

This file was created by Max H. (Haizoooon)
Date: 25.05.2021
Copyright© 2021 Max H.

 **/
class AnnotationExclusionStrategy(private vararg val annotationClasses: Class<out Annotation>) : ExclusionStrategy {
    override fun shouldSkipField(fieldAttributes: FieldAttributes): Boolean {
        return annotationClasses.any { fieldAttributes.getAnnotation(it) != null }
    }

    override fun shouldSkipClass(aClass: Class<*>): Boolean {
        return false
    }
}

