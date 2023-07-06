import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class Push(
    val id: String,
    val type: String,
    val actor: Actor,
    val repo: Repo,
    val payload: Payload,
    val public: Boolean,
    @SerialName("created_at") @Json(name = "created_at") val createdAt: String
)

@Serializable
@JsonClass(generateAdapter = true)
data class Actor(
    val id: Int,
    val login: String,
    @SerialName("gravatar_id") @Json(name = "gravatar_id") val gravatarId: String,
    val url: String,
    @SerialName("avatar_url") @Json(name = "avatar_url") val avatarUrl: String
)

@Serializable
@JsonClass(generateAdapter = true)
data class Repo(
    val id: Int,
    val name: String,
    val url: String
)

@Serializable
@JsonClass(generateAdapter = true)
data class Payload(
    @SerialName("push_id") @Json(name = "push_id") val pushId: Int? = null,
    val size: Int? = null,
    @SerialName("distinct_size") @Json(name = "distinct_size") val distinctSize: Int? = null,
    val ref: String? = null,
    val head: String? = null,
    val before: String? = null,
    @SerialName("ref_type") @Json(name = "ref_type") val refType: String? = null,
    @SerialName("master_branch") @Json(name = "master_branch") val masterBranch: String? = null,
    val description: String? = null,
    @SerialName("pusher_type") @Json(name = "pusher_type") val pusherType: String? = null
)

fun main() {
    measureMoshi<List<Push>>(
        titlePrefix = "Large list serializing",
        fileName = "large-list.json"
    )
//    measureKotlinX<List<Push>>(
//        titlePrefix = "Large list serializing",
//        fileName = "large-list.json"
//    )
}
