import com.squareup.moshi.JsonClass
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
    val created_at: String
)

@Serializable
@JsonClass(generateAdapter = true)
data class Actor(
    val id: Int,
    val login: String,
    val gravatar_id: String,
    val url: String,
    val avatar_url: String
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
    val push_id: Int? = null,
    val size: Int? = null,
    val distinct_size: Int? = null,
    val ref: String? = null,
    val head: String? = null,
    val before: String? = null,
    val ref_type: String? = null,
    val master_branch: String? = null,
    val description: String? = null,
    val pusher_type: String? = null
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
