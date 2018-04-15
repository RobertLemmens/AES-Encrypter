package nl.codebulb.aese.gui

import java.io.File

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.ImageView
import javax.imageio.ImageIO
import nl.codebulb.aese.Main.stage
import nl.codebulb.aese.util.FileUtils
import scalafx.scene.control.{Alert, Button, TextInputDialog}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.image.Image


/**
  * @author Robert Lemmens 
  *         15-4-18
  *
  * Pop up dialog for creating QR Codes
  *
  */
object QRView extends FileUtils{

  /**
    * Launch the popup to save the QR Code.
    *
    * @param file
    */
  def showQRCode(file: File): Unit = {
    val image = new Image(file.toURI.toString)
    val imageView = new ImageView(image)
    val alert = new TextInputDialog(defaultValue = file.getAbsolutePath) {
      initOwner(stage)
      title = "QR Code"
      headerText = "Your QR Code"
      contentText = "Save in:"
    }
    alert.setGraphic(imageView)
    val result = alert.showAndWait()
    result match {
      case Some(name) => println("Your QR code : " + name)
      case None       => println("Dialog was canceled.")
    }
    file move result.get + File.separator + "EncrQRCode.png"
  }

}
