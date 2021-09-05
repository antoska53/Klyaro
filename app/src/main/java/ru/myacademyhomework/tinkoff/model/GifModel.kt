package ru.myacademyhomework.tinkoff.model

// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json     = Json(JsonConfiguration.Stable)
// val gIFModel = json.parse(GIFModel.serializer(), jsonString)



import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class GifModel (
	val id: Long,
	val description: String,
	val votes: Long,
	val author: String,
	val date: String,
	val gifURL: String,
	val gifSize: Long,
	val previewURL: String,
	val videoURL: String,
	val videoPath: String,
	val videoSize: Long,
	val type: String,
	val width: String,
	val height: String,
	val commentsCount: Long,
	val fileSize: Long,
	val canVote: Boolean
)
