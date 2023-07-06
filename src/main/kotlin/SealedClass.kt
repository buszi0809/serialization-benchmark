import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class UserAvatar {

    @Serializable
    @SerialName("IMAGE")
    @JsonClass(generateAdapter = true)
    data class Image(val url: String) : UserAvatar()

    @Serializable
    @SerialName("RESOURCE")
    @JsonClass(generateAdapter = true)
    data class Resource(val name: String) : UserAvatar()

    @Serializable
    @SerialName("NONE")
    @JsonClass(generateAdapter = true)
    class None : UserAvatar() {
        override fun equals(other: Any?): Boolean = other is None
        override fun hashCode(): Int = System.identityHashCode(this)
    }
}

@OptIn(ExperimentalStdlibApi::class)
fun measureMoshiSealedClass() {
    val moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(UserAvatar::class.java, "type")
                .withSubtype(UserAvatar.Image::class.java, "IMAGE")
                .withSubtype(UserAvatar.Resource::class.java, "RESOURCE")
                .withSubtype(UserAvatar.None::class.java, "NONE")
        )
        .build()

    measure(
        title = "Sealed class serializing - Moshi",
        fileName = "sealed-class.json",
        serializerFactory = { moshi.adapter<List<UserAvatar>>()},
        deserialize = { serializer, input -> serializer.fromJson(input) },
        serialize = { serializer, model -> serializer.toJson(model) }
    )
}

fun main() {
    measureKotlinX<List<UserAvatar>>(
        titlePrefix = "Sealed class serializing",
        fileName = "sealed-class.json"
    )
//    measureMoshiSealedClass()
}
