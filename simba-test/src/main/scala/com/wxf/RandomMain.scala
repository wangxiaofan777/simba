package com.wxf

import scala.util.Random

/**
 *
 * @author WangMaoSong
 * @date 2022/8/4 10:21
 */
object RandomMain {


  def main(args: Array[String]): Unit = {
    val random = new Random

    val array = new Array[Byte](4)
    random.nextBytes(array)
    println(array.length)
    array.foreach(println)
  }
}
