import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class UserProfile(
    val personal: Personal,
    @SerialName("billing_address") @Json(name = "billing_address") val billingAddress: BillingAddress,
    val delivery: Delivery,
    val preferences: Preferences,
    val active: Boolean
)

@Serializable
@JsonClass(generateAdapter = true)
data class Personal(
    @SerialName("first_name") @Json(name = "first_name") val firstName: String,
    @SerialName("last_name") @Json(name = "last_name") val lastName: String,
    val email: String,
    @SerialName("phone_number") @Json(name = "phone_number") val phoneNumber: String,
    @SerialName("email_verified") @Json(name = "email_verified") val emailVerified: Boolean
)

@Serializable
@JsonClass(generateAdapter = true)
data class BillingAddress(
    @SerialName("post_code") @Json(name = "post_code") val postCode: String,
    val city: String,
    @SerialName("street_address") @Json(name = "street_address") val streetAddress: String,
    @SerialName("country_code") @Json(name = "country_code") val countryCode: String
)

@Serializable
@JsonClass(generateAdapter = true)
data class Delivery(
    val name: String,
    @SerialName("post_code") @Json(name = "post_code") val postCode: String,
    val city: String,
    @SerialName("street_address") @Json(name = "street_address") val streetAddress: String,
    @SerialName("country_code") @Json(name = "country_code") val countryCode: String,
    val remarks: String
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
