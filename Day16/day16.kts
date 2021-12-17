import java.io.File
import java.math.BigInteger

val numbers = File("test1.txt").readText()
data class Packet(val binary:String)
{
    val version = binary.slice(0..2).toInt(2)
    val id = binary.slice(3..5).toInt(2)
    fun literalpacket() = (id == 4)

    fun literalvalue():Int {
        if(literalpacket())
        {
            val numberbinary = binary.slice(6..binary.length-1)
            val sb = StringBuilder()
            for(number in numberbinary.windowed(5,5)) {
                sb.append(number.slice(1..4))
                if(number[0] == '0')
                    return sb.toString().toInt(2)
            }
        }
        error("fuck")
    }

}


fun hextobinary(hex:String) = BigInteger(hex,16).toString(2)

fun parseliteralpacket(binary:String):Int
{
    println("parse bin $binary")
    println(binary.length)
    val sb = StringBuilder()
    for(number in binary.windowed(5,5)) {
        sb.append(number.slice(1..4))
        if(number[0] == '0')
            return sb.toString().toInt(2)
    }
    error("fuck")
}

fun parsepacket(binary:String){
    var packet = Packet(binary)

}
parsepacket(hextobinary(numbers))