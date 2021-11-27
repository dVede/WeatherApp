import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import tornadofx.*

class WeatherForm: View("WeatherAPP") {
    override val root: Parent = FXMLLoader.load(javaClass.getResource("/fxml/weather.fxml"))
}