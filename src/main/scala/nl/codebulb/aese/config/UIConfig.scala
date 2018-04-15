package nl.codebulb.aese.config

import pureconfig.error.ConfigReaderFailures
/**
  * @author Robert Lemmens 
  *         14-4-18
  */

case class UIConfig(title: String)

object UIConfig {

  import pureconfig._

  /**
    * Load application.conf as a case class
    *
    * @return
    */
  def load: Either[ConfigReaderFailures, UIConfig] = {
    loadConfig[UIConfig]("gui")
  }

}
