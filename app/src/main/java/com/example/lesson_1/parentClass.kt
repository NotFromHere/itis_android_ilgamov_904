open class parentClass constructor(var field1: String = "someString", var field2: Int = 101){
    init {
        println("1. field1 = $field1")
        println("2. field2 = $field2")
    }

    fun someFun() = 123
}