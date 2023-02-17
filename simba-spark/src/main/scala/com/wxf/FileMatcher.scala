package com.wxf

import java.io.File


/**
 *
 * @author WangMaoSong
 * @date 2022/6/29 9:29
 */
object FileMatcher {

  private def fileHere = new File(".").listFiles

  def filesEnding(query: String): Array[File] =
    fileMatching(_.endsWith(query))

  def filesContaining(query: String): Array[File] =
    fileMatching(_.contains(query))

  def filesRegex(query: String): Array[File] =
    fileMatching(_.matches(query))

  private def fileMatching(matcher: String => Boolean) =
    for (file <- fileHere; if matcher(file.getName))
      yield file


}
