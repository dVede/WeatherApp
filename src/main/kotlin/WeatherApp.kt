import javafx.stage.Stage
import tornadofx.App


class WeatherApp: App(WeatherForm::class) {
    override fun start(stage: Stage) {
        stage.show()
        stage.isResizable = false
        stage.sizeToScene()
        super.start(stage)
    }
}