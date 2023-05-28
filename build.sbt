name := """Fila0AppScala"""
organization := "lpp.fila0app"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.10"

PlayKeys.devSettings += "play.server.http.port" -> "3000"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.24"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "lpp.fila0app.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "lpp.fila0app.binders._"
