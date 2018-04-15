package nl.codebulb.aese.config

import pureconfig.error.ConfigReaderFailures
/**
  * @author Robert Lemmens 
  *         14-4-18
  */

case class UIConfig(title: String, width: Int, height: Int)

object UIConfig {

  import pureconfig._

  def load: Either[ConfigReaderFailures, UIConfig] = {
    loadConfig[UIConfig]("gui")
  }

}
