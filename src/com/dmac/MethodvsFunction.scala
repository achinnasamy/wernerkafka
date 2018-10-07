package com.dmac

object MethodvsFunction {

  def main(args : Array[String]) = {

    def method(x:Int) = x+3
    val function = (x:Int) => x+3

    println(method(3))
    println(function(3))
    println(function)

  }

}
