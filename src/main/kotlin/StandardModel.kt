import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class UserProfile(
    val active: Boolean,
    val billingAddress: BillingAddress,
    val delivery: Delivery,
    val personal: Personal,
    val preferences: Preferences
)

@Serializable
@JsonClass(generateAdapter = true)
data class BillingAddress(
    val city: String,
    val countryCode: String,
    val postCode: String,
    val streetAddress: String
)

@Serializable
@JsonClass(generateAdapter = true)
data class Delivery(
    val city: String,
    val countryCode: String,
    val name: String,
    val postCode: String,
    val remarks: String,
    val streetAddress: String
)

@Serializable
@JsonClass(generateAdapter = true)
data class Personal(
    val email: String,
    val emailVerified: Boolean,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)

@Serializable
@JsonClass(generateAdapter = true)
data class Preferences(
    val notifications: Boolean
)

fun main() {
//    measureMoshi<UserProfile>(
//        titlePrefix = "Standard model serializing",
//        fileName = "standard-model.json"
//    )
    measureKotlinX<UserProfile>(
        titlePrefix = "Standard model serializing",
        fileName = "standard-model.json"
    )
}
