import java.io.File
import java.math.BigInteger

val numbers = File("test2.txt").readText()

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
    val packetv = binary.slice(0..2)
    val id = binary.slice(3..5)
    if( id.toInt(2)== 4) {
        println("literal")
        println("packet version: $packetv, id: $id")
        println(parseliteralpacket(binary.slice(6..binary.length-1)))

    }
    else{
        //operator
        if(binary[6].digitToInt() == 0)
        {
            //15 bit length denoting subpackets
            val subpacketcount = binary.slice(7..22)
        }
        else
        {
            val subpacketcount = binary.slice(7..18)

            //11 bit length denoting sub packets
        }
    }
}
parsepacket(hextobinary(numbers))