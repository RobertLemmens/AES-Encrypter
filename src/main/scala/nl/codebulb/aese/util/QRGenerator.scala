package nl.codebulb.aese.util
import java.io.File
import net.glxn.qrgen._

/**
  * @author Robert Lemmens 
  *         15-4-18
  *
  *  Generate QR Codes using the qrgen library
  */
class QRGenerator {

  /**
    * Convert a string into a QR Code. QR Codes are generated in /tmp/ folder by default
    *
    * @param stringToEncode the base64 result from encryption
    * @return the QR code image as a java.io.File
    */
  def generateQRCode(stringToEncode: String): File = {
    QRCode.from(stringToEncode).file()
  }

}
