package nl.codebulb.aese.util

import java.io.File
import java.nio.file.{Files, Path, Paths, StandardCopyOption}

/**
  * @author Robert Lemmens 
  *         15-4-18
  *
  *   A small helper class for copying files in a more intuitive way.
  */

trait FileUtils {

  implicit class FileImplicit(file: File) {

    /**
      * By bringing in this implicit we can move a file like so: fileObject.move("/path")
      *
      * @param target
      * @return
      */
    def move(target: String): Path = {
      Files.move(file.toPath, Paths.get(target), StandardCopyOption.REPLACE_EXISTING)
    }

  }
}

