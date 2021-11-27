import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.math.floor

const val URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=0f21bd02afdc24c032045afd167ff588" +
        "&units=metric&lang=ru"
const val CELSIUS_SIGN = " °С"
const val HUMIDITY_SIGN = " %"
const val PRESSURE_SIGN = " мм рт. ст."
const val SPEED_SIGN = " м/c"

fun getWindDirection(degrees: Double): String {
    val section = floor(degrees / 45)
    val arr = listOf("Северный","Северо-Западный","Западный","Юго-Западный",
        "Южный","Юго-Восточный", "Восточный", "Северо-Восточный")
    return arr[(section % 8).toInt()]
}

fun getJSONFromUrl(urlAddress: String) : JSONObject {
    val result: String
    try {
        val urlConnection = URL(urlAddress).openConnection()
        val stream = InputStreamReader(urlConnection.getInputStream())
        result = stream.buffered().use(BufferedReader::readText)
    } catch (e: Exception) {
        throw IllegalArgumentException("City not found")
    }
    return JSONObject(result)
}

fun getIcon(code: String): String =
    when (code) {
        "01d" -> "/icons/01d.png"
        "01n" -> "/icons/01n.png"
        "02d" -> "/icons/02d.png"
        "02n" -> "/icons/02n.png"
        "03d" -> "/icons/03d.png"
        "03n" -> "/icons/03n.png"
        "04n" -> "/icons/04n.png"
        "04d" -> "/icons/04d.png"
        "09n" -> "/icons/09n.png"
        "09d" -> "/icons/09d.png"
        "10n" -> "/icons/10n.png"
        "10d" -> "/icons/10d.png"
        "11n" -> "/icons/11n.png"
        "11d" -> "/icons/11d.png"
        "13n" -> "/icons/13n.png"
        "13d" -> "/icons/13d.png"
        "50n" -> "/icons/50n.png"
        "50d" -> "/icons/50d.png"
        else -> "/icons/unknown.png"
    }