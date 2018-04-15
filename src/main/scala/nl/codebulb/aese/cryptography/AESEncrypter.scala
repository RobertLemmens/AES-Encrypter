package nl.codebulb.aese.cryptography
import cats.effect.IO
import tsec.cipher.symmetric._
import tsec.cipher.symmetric.jca._
import tsec.common._
import tsec.hashing.jca._

/**
  * @author Robert Lemmens 
  *         15-4-18
  *
  * The class containing the encryption definitions. Implemented with tsec.
  */
class AESEncrypter {

  implicit val ctrStrategy: IvGen[IO, AES128CTR] = AES128CTR.defaultIvStrategy[IO]

  /**
    * Encrypt a string with a password.
    *
    * @param payload The string to encrypt
    * @param password The key
    * @return A base64 representation of the encrypted result.
    */
  def encrypt(payload: String, password: String): IO[String] = {
    AES128CTR.genEncryptor[IO].flatMap(
      implicit instance =>
        for {
          key <- passToSpec(password.utf8Bytes)
          encrypted <- AES128CTR.encrypt[IO](PlainText(payload.utf8Bytes), key)
          arr = encrypted.toConcatenated
        } yield arr.toB64String
    )
  }

  /**
    * Decrypt a base64 representation of AES128 encrypted content with the key.
    *
    * @param base64Encypted
    * @param password
    * @return
    */
  def decrypt(base64Encypted: String, password: String): IO[String] = {
    AES128CTR.genEncryptor[IO].flatMap(
      implicit instance =>
        for {
          key <- passToSpec(password.utf8Bytes)
          from <- IO.fromEither(AES128CTR.ciphertextFromConcat(base64Encypted.base64Bytes))
          decryped <- AES128CTR.decrypt[IO](from, key)
        } yield decryped.toUtf8String
    )
  }

  /**
    * Turns a byte array into a key object (jca.SecretKey[_]) required by tsec.
    *
    * @param passwordBytes
    * @return
    */
  private def passToSpec(passwordBytes: Array[Byte]): IO[jca.SecretKey[AES128CTR]] = {
    AES128CTR.buildKey[IO](java.util.Arrays.copyOf(passwordBytes.hash[SHA1],16))
  }

}
