package nl.codebulb.aese

import nl.codebulb.aese.config.UIConfig
import nl.codebulb.aese.cryptography.AESEncrypter
import nl.codebulb.aese.gui.QRView
import nl.codebulb.aese.util.QRGenerator
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextArea, TextField}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Text
import scalafx.Includes._
import scalafx.scene.input.{Clipboard, ClipboardContent}

/**
  * @author Robert Lemmens 
  *         14-4-18
  *
  *
  *  App is launched from here. Still a bit messy with all the scalaFX stuff in here.
  */
object Main extends JFXApp {

  // Config
  val config = for {
    conf <- UIConfig.load
  } yield conf
  val appTitle: String = config.fold(_ => "Error loading config...", r => r.title)

  // Util inits
  val AESTools = new AESEncrypter
  val QRGenerator = new QRGenerator

  // ScalaFX stuff
  val payloadBox = new TextField()
  val passwordBox = new TextField()
  val outputBox = new TextArea {
    text = ""
    editable = false
  }

  // Main stage
  stage = new PrimaryStage {
    title = appTitle
    scene = new Scene {
      content = new HBox {
        padding = Insets(20)
        children = Seq(
          new VBox {
            spacing = 10
            padding = Insets(20)
            children = Seq(
              new Text {
                text = appTitle
                style = "-fx-font-size: 48px"
              },
              new Text {
                text = "Text to encrypt"
                style = "-fx-font-size: 16px"
                fill = Color.Gray
              },
              payloadBox,
              new Text {
                text = "Password"
                style = "-fx-font-size: 16px"
                fill = Color.Gray
              },
              passwordBox,
              new HBox {
                spacing = 10
                children = Seq(
                  new Button {
                    text = "Encrypt"
                    style = {
                      "-fx-background-color: #d63031; " +
                        "-fx-font-size: 16px; " +
                        "-fx-text-fill: white;" +
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                        "-fx-background-radius: 0;" +
                        "-fx-padding: 15 45 15 45;"
                    }
                    onAction = handle {
                      println("Encrypting: " + payloadBox.text.value + " - " + passwordBox.text.value)
                      val encrypted = AESTools.encrypt(payloadBox.text.value, passwordBox.text.value)
                      outputBox.text = encrypted.unsafeRunSync()
                      println("done")
                    }
                  },
                  new Button {
                    text = "Decrypt"
                    style = {
                      "-fx-background-color: #2d3436; " +
                        "-fx-font-size: 16px; " +
                        "-fx-text-fill: white;" +
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                        "-fx-background-radius: 0;" +
                        "-fx-padding: 15 45 15 45;"
                    }
                    onAction = handle {
                      println("Decrypting: " + payloadBox.text.value + " - " + passwordBox.text.value)
                      val decrypted = AESTools.decrypt(payloadBox.text.value, passwordBox.text.value)
                      outputBox.text = decrypted.unsafeRunSync()
                      println("done")
                    }
                  }
                )
              },
              new Text {
                text = "Output"
                style = "-fx-font-size: 16px"
                fill = Color.Gray
              },
              outputBox,
              new HBox {
                spacing = 10
                children = Seq(
                  new Button {
                    text = "QR"
                    style = {
                      "-fx-background-color: #d63031; " +
                        "-fx-font-size: 16px; " +
                        "-fx-text-fill: white;" +
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                        "-fx-background-radius: 50;" +
                        "-fx-padding: 15 15 15 15;"
                    }
                    onAction = handle {
                      println("Generating QR Code")
                      val file = QRGenerator.generateQRCode(outputBox.text.value)
                      println(file.getAbsolutePath)
                      QRView.showQRCode(file)
                    }
                  },
                  new Button {
                    text = "Copy"
                    style = {
                      "-fx-background-color: #d63031; " +
                        "-fx-font-size: 16px; " +
                        "-fx-text-fill: white;" +
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                        "-fx-background-radius: 50;" +
                        "-fx-padding: 15 15 15 15;"
                    }
                    onAction = handle {
                      println("Copying to clipboard")
                      val clipBoardContent = new ClipboardContent()
                      clipBoardContent.putString(outputBox.text.value)
                      Clipboard.systemClipboard.setContent(clipBoardContent)
                    }
                  }
                )
              },
            )
          }
        )
      }
    }
  }




}
