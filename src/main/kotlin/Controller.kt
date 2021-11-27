import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.text.Text
import java.io.FileNotFoundException
import java.net.URL
import java.net.UnknownHostException
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
        this.cityName = DEFAULT_CITY
        weather_icon.image = Image(javaClass.getResource(String.format(ICON_PATH, "unknown"))?.toExternalForm())
        weatherManager = WeatherManager(cityName)
        weatherUpdate()
        find_button.setOnAction {
            if (town_textField.text.isBlank())
                Alert(AlertType.ERROR,
                    EMPTY_ERROR_MSG,
                    ButtonType.OK).show()

            else buttonPressed(town_textField.text)
        }
    }

    private fun weatherUpdate() {
        try {
            weatherManager.getWeather()
        } catch (e: FileNotFoundException) {
            Alert(AlertType.ERROR,
                CITY_ERROR_MSG,
                ButtonType.OK).show()
            return
        } catch (e: UnknownHostException) {
            Alert(AlertType.WARNING,
                INTERNET_ERROR_MSG,
                ButtonType.OK).show()
            return
        }
        town_id.text = weatherManager.city
        max_temp.text = String.format("%s $CELSIUS_UNIT", weatherManager.maxTemp.toString())
        min_temp.text = String.format("%s $CELSIUS_UNIT", weatherManager.minTemp.toString())
        feels_like.text = String.format("%s $CELSIUS_UNIT", weatherManager.feelsLike.toString())
        humidity.text = String.format("%s $HUMIDITY_UNIT", weatherManager.humidity.toString())
        pressure.text = String.format("%s $PRESSURE_UNIT", weatherManager.pressure.toString())
        temp_text.text = String.format("%s $CELSIUS_UNIT", weatherManager.temp.toString())
        temp_add.text = String.format("%s $CELSIUS_UNIT", weatherManager.temp.toString())
        weather_status.text = weatherManager.weather.replaceFirstChar { it.uppercaseChar() }
        weather_icon.image = Image(getIcon(weatherManager.icon))
        wind_deg.text = getWindDirection(weatherManager.deg)
        wind_speed.text = String.format("%s $SPEED_UNIT", weatherManager.windSpeed.toString())
    }

    private fun buttonPressed(str: String) {
        val name = str.trim().lowercase().replaceFirstChar { it.uppercaseChar() }
        this.cityName = name
        weatherManager = WeatherManager(cityName)
        weatherUpdate()
    }
}