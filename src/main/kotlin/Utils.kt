import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.net.URL
import java.net.UnknownHostException
import kotlin.math.floor

const val URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=0f21bd02afdc24c032045afd167ff588" +
        "&units=metric&lang=ru"
const val CELSIUS_UNIT = "°С"
const val HUMIDITY_UNIT = "%%"
const val PRESSURE_UNIT = "мм рт. ст."
const val SPEED_UNIT = "м/c"
const val DEFAULT_CITY = "Москва"
const val INTERNET_ERROR_MSG = "No internet connection"
const val CITY_ERROR_MSG = "City with this name was not found"
const val EMPTY_ERROR_MSG = "Text field is empty"
const val ICON_PATH = "/icons/%s.png"
private val codesList = listOf("01d","01n","02d","02n","03d","03n","04d","04n","09d","09n",
    "10d","10n","11d","11n","13d","13n","50d","50n")

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
    } catch (e: UnknownHostException) {
        throw UnknownHostException(INTERNET_ERROR_MSG)
    } catch (e: FileNotFoundException) {
        throw FileNotFoundException(CITY_ERROR_MSG)
    }
    return JSONObject(result)
}

fun getIcon(code: String): String = if (codesList.contains(code)) String.format(ICON_PATH, code)
else String.format(ICON_PATH, "unknown")

