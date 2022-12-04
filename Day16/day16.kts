import java.io.File
import java.math.BigInteger

val numbers = File("input.txt").readText().map { hextobinary(it) }
data class Packet(val versionid : Int , val typeid : Int , val literalvalue: Long? = null, val packets: List<Packet> = emptyList())

fun Packet.Operate():Long
{
    val p = packets.map { it.Operate()}
    val res = when (typeid) {
        0 -> p.sum()
        1 -> p.fold(1L) { acc, l ->  acc * l}
        2 -> p.minOf { it }
        3 -> p.maxOf { it }
        4 -> literalvalue!!
        5 -> (if (p[0] > p[1]) 1L else 0L)
        7 -> (if (p[0] == p[1]) 1L else 0L)
        6 -> (if (p[0] < p[1]) 1L else 0L)
        else -> return 0L
    }
    return res
}
//it was unwieldy getting next values using a for loop and constructing
//the data with that - some utility func for char iterators will help
fun Iterator<Char>.nextChunk(i: Int) = (1..i).map { next() }.joinToString("")
fun Iterator<Char>.nextValue(i: Int = 1) = nextChunk(i).toInt(2)
fun Iterator<Char>.parseSubPackets() = when (nextValue()) {
    1 -> getPackets(nextValue(11))
    else -> nextChunk(nextValue(15)).iterator().getPackets(Int.MAX_VALUE)
}
fun Iterator<Char>.getPackets(ni: Int = 1): List<Packet> = mutableListOf<Packet>().apply {
    for (c in 1..ni) {
        add(parsePackets())
        if (!hasNext())
            break
    }
}

fun Iterator<Char>.Operate() = parsePackets().Operate()

fun Iterator<Char>.literalValue():Long {
    var bits = ""
    do
    {
        val keepreading = nextValue() == 1
        bits += nextChunk(4)
    } while (keepreading)
    return bits.toLong(2)
}

fun Iterator<Char>.parsePackets(): Packet {
    val v = nextValue(3)
    return when (val typeID = nextValue(3)) {
        4 -> Packet(v, typeID, literalValue())
        else -> Packet(v, typeID, packets = parseSubPackets())
    }
}

fun Iterator<Char>.sumOfVersions() = parsePackets().packetAndSubPackets().sumOf { it.versionid }

fun Packet.packetAndSubPackets(): List<Packet> = listOf(this) + packets.flatMap { it.packetAndSubPackets() }

fun hextobinary(hex: Char) = String.format("%4s", BigInteger(hex.toString(), 16).toString(2)!!).replace(' ', '0')

fun parsepacket(binary: String) {
    println("-----PART1-------")
    println("${binary.iterator().sumOfVersions()}")

    println("-----PART2-------")
    println("${binary.iterator().Operate()}")
}

parsepacket(numbers.joinToString(""))