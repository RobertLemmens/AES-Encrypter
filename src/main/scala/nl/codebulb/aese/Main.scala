package nl.codebulb.aese

import nl.codebulb.aese.config.UIConfig
import nl.codebulb.aese.cryptography.AESEncrypter
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextArea, TextField}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.{Color, LinearGradient, Stops}
import scalafx.scene.text.Text
import scalafx.Includes._
/**
  * @author Robert Lemmens 
  *         14-4-18
  */
object Main extends JFXApp {

  val config = for {
    conf <- UIConfig.load
  } yield conf

  val AESTools = new AESEncrypter

  val appTitle: String = config.fold(l => "error", r => r.title)

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
              new TextField {
                text = "Payload"
              },
              new Text {
                text = "Password"
                style = "-fx-font-size: 16px"
                fill = Color.Gray
              },
              new TextField {
                text = "Password"
              },
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
                      println("Encrypting")
                      val encrypted = AESTools.encrypt("some text pls", "key")
                      val test = encrypted.unsafeRunSync()
                      println(test)
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
                  }
                )
              },
              new Text {
                text = "Output"
                style = "-fx-font-size: 16px"
                fill = Color.Gray
              },
              new TextArea {
                text = ""
                editable = false
              },
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
