import org.json.JSONObject


class WeatherManager(val city: String) {
    var weather: String = ""
    var temp: Int = 0
    var feelsLike: Int = 0
    var maxTemp: Int = 0
    var minTemp: Int = 0
    var humidity: Int = 0
    var pressure: Int = 0
    var deg: Double = 0.0
    var windSpeed: Int = 0
    var icon: String = ""

    fun getWeather(units: String) {
        val jsonObject: JSONObject = getJSONFromUrl(String.format(URL, city, units))
        var jsonPart: JSONObject = jsonObject.getJSONObject("main")
        this.temp = jsonPart.getInt("temp")
        this.feelsLike = jsonPart.getInt("feels_like")
        this.maxTemp = jsonPart.getInt("temp_max")
        this.minTemp = jsonPart.getInt("temp_min")
        this.pressure = jsonPart.getInt("pressure")
        this.humidity = jsonPart.getInt("humidity")
        jsonPart = jsonObject.getJSONObject("wind")
        this.windSpeed = jsonPart.getInt("speed")
        this.deg = jsonPart.getDouble("deg")
        jsonPart = jsonObject.getJSONArray("weather").getJSONObject(0)
        this.weather = jsonPart.getString("description")
        this.icon = jsonPart.getString("icon")
    }
}