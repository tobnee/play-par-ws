import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "parws"
    val appVersion      = "0.1"
	
    val appDependencies = Nil
 
    val main = play.Project(
      appName, appVersion, appDependencies
    ) 

}
