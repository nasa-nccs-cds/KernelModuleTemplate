name := "kermodbase"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

organization := "nasa.nccs"

lazy val root = (project in file("."))

libraryDependencies ++= Dependencies.scala

libraryDependencies ++= Dependencies.CDAPI

packageOptions in (Compile, packageBin) += Package.ManifestAttributes( java.util.jar.Attributes.Name.SPECIFICATION_TITLE -> "CDS2KernelModule" )
