package com.example.lesson_1

import parentClass

class childClass(var childField1: String = "child", var childField2: Int) :
    parentClass(childField1, childField2), someInterface {

    override fun interfaceFun() {
        val string1: String = childField1;
        println(string1)
    }

}