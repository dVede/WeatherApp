import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.text.Text
import java.net.URL
import java.util.*


class Controller : Initializable {
    private lateinit var cityName: String
    private lateinit var weatherManager: WeatherManager

    @FXML
    private lateinit var feels_like: Text

    @FXML
    private lateinit var find_button: Button

    @FXML
    private lateinit var humidity: Text

    @FXML
    private lateinit var max_temp: Text

    @FXML
    private lateinit var min_temp: Text

    @FXML
    private lateinit var pressure: Text

    @FXML
    private lateinit var temp_add: Text

    @FXML
    private lateinit var temp_text: Text

    @FXML
    private lateinit var town_id: Text

    @FXML
    private lateinit var town_textField: TextField

    @FXML
    private lateinit var weather_icon: ImageView

    @FXML
    private lateinit var weather_status: Text

    @FXML
    private lateinit var wind_deg: Text

    @FXML
    private lateinit var wind_speed: Text

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        this.cityName = town_id.text
        weatherManager = WeatherManager(cityName)
        weatherUpdate()
        find_button.setOnAction {
            setPressed()
        }
    }

    private fun weatherUpdate() {
        weatherManager.getWeather()
        town_id.text = weatherManager.city
        max_temp.text = weatherManager.maxTemp.toString() + CELSIUS_SIGN
        min_temp.text = weatherManager.minTemp.toString() + CELSIUS_SIGN
        feels_like.text = weatherManager.feelsLike.toString() + CELSIUS_SIGN
        humidity.text = weatherManager.humidity.toString() + HUMIDITY_SIGN
        pressure.text = weatherManager.pressure.toString() + PRESSURE_SIGN
        temp_text.text = weatherManager.temp.toString() + CELSIUS_SIGN
        temp_add.text = weatherManager.temp.toString() + CELSIUS_SIGN
        weather_status.text = weatherManager.weather.replaceFirstChar { it.uppercaseChar() }
        weather_icon.image = Image(getIcon(weatherManager.icon))
        wind_deg.text = getWindDirection(weatherManager.deg)
        wind_speed.text = weatherManager.windSpeed.toString() + SPEED_SIGN
    }

    private fun setPressed() {
        if (town_textField.text.isBlank()) {
            return
        } else {
            val name = town_textField.text.trim().lowercase().replaceFirstChar { it.uppercaseChar() }
            this.cityName = name
            town_textField.text = name
            weatherManager = WeatherManager(cityName)
            try {
                weatherUpdate()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}