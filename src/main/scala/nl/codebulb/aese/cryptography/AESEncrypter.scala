package nl.codebulb.aese.cryptography
import cats.effect.IO
import tsec.cipher.symmetric._
import tsec.cipher.symmetric.jca._
import tsec.common._
/**
  * @author Robert Lemmens 
  *         15-4-18
  */
class AESEncrypter {

  implicit val gcmstrategy = AES128GCM.defaultIvStrategy[IO]

  def encrypt(payload: String, password: String): IO[String] = {
    println("Command recieved")
    val aad = AAD(password.utf8Bytes)
    AES128GCM.genEncryptor[IO].flatMap(
      implicit instance =>
        for {
          key <- AES128GCM.generateKey[IO]
          encrypted <- AES128GCM.encryptWithAAD[IO](PlainText(payload.utf8Bytes), key, aad)
        } yield encrypted.content.toUtf8String
    )
  }

}
