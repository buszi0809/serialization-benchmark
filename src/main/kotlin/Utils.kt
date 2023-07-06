import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.io.File
import java.math.RoundingMode
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun <Serializer, Model> measure(
    title: String,
    fileName: String,
    serializerFactory: () -> Serializer,
    deserialize: (Serializer, String) -> Model,
    serialize: (Serializer, Model) -> String
) {
    val text = File(fileName).readText()

    val rawTimes = (0..5).map {
        val serializer: Serializer
        val serializerCreationTime = measureTime {
            serializer = serializerFactory()
        }.toDouble(DurationUnit.MILLISECONDS).round()

        val model: Model
        val deserializationTime = measureTime {
            model = deserialize(serializer, text)
        }.toDouble(DurationUnit.MILLISECONDS).round()

        val serializationTime = measureTime {
            serialize(serializer, model)
        }.toDouble(DurationUnit.MILLISECONDS).round()

        Triple(serializerCreationTime, deserializationTime, serializationTime)
    }

    val (serializerCreationTimes, deserializationTimes, serializationTimes) = rawTimes.unzip()
    val combinedDeserializationTimes = rawTimes.map { (first, second, _) -> first + second }
    val combinedSerializationTimes = rawTimes.map { (first, _, third) -> first + third }

    println(title)
    println("Serializer/adapter creation times:  $serializerCreationTimes")
    println("Deserialization times:              $deserializationTimes")
    println("Serialization times:                $serializationTimes")
    println("Combined deserialization times:     $combinedDeserializationTimes")
    println("Combined serialization times:       $combinedSerializationTimes")
}

@OptIn(ExperimentalStdlibApi::class)
inline fun <reified Model> measureMoshi(
    titlePrefix: String,
    fileName: String
) {
    val moshi = Moshi.Builder().build()

    measure(
        title = "$titlePrefix - Moshi",
        fileName = fileName,
        serializerFactory = { moshi.adapter<Model>() },
        deserialize = { serializer, input -> serializer.fromJson(input) },
        serialize = { serializer, model -> serializer.toJson(model) }
    )
}

inline fun <reified Model> measureKotlinX(
    titlePrefix: String,
    fileName: String
) {
    val json = Json {
        ignoreUnknownKeys = true
    }

    measure(
        title = "$titlePrefix - KotlinX",
        fileName = fileName,
        serializerFactory = { serializer<Model>() },
        deserialize = { serializer, input -> json.decodeFromString(serializer, input) },
        serialize = { serializer, model -> json.encodeToString(serializer, model) }
    )
}

fun Double.round() = toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()

fun <T1, T2, T3> List<Triple<T1, T2, T3>>.unzip(): Triple<List<T1>, List<T2>, List<T3>> {
    val first = map { it.first }
    val second = map { it.second }
    val third = map { it.third }
    return Triple(first, second, third)
}
