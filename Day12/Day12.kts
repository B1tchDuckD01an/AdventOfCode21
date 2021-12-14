import java.io.File


val numbers = File("test1.txt").readLines()
val testpaths = File("test1paths.txt").readLines()

class Cave(val name:String)
{
    override fun toString(): String {
        return "$name"
        //big: ${isBig()} start: ${isStart()} end: ${isEnd()} connections ${connections.count()}"
    }
    var connections = mutableListOf<Cave>()
    var visited = false
    var visitcount = 0
    fun isBig() = name.filter { it.isUpperCase()}.count() > 0
    fun isStart() = name=="start"
    fun isEnd() = name=="end"


    fun addConnection(connected:Cave) {
        if(!connections.contains(connected)) {
            connections.add(connected)
            connected.addConnection(this)
        }
    }
}

class CaveSystem(val caves:MutableList<Cave>) {

    fun traverse(cave:Cave, currentpath:MutableList<String>){
        if(cave.isEnd()) {
            println(currentpath)
            paths.add(currentpath.toString())
            return
        }

        var nomoresmallcaves = false
        val smallcaves = currentpath.filter { it.filter { it.isLowerCase() }.count() > 0 }
        println("small caves $smallcaves")
        if(smallcaves.distinct() != smallcaves)
            nomoresmallcaves = true


        cave.connections.filter { ((!it.isBig() && !nomoresmallcaves) || (it.isEnd() || it.isBig() || !it.visited))
                && !it.isStart() }.forEach { connection ->
                val inpath = currentpath.toMutableList()
                inpath.add(connection.name)
                traverse(connection, inpath)
        }
       cave.visited = false
    }

    var paths = mutableListOf<String>()
}

fun makecavelist(list:List<String>):CaveSystem
{
    var cavesystem = CaveSystem(mutableListOf<Cave>())
    for(string in list)
    {
        var splitstring = string.split("-")

        for(s in splitstring)
            if(cavesystem.caves.filter { it.name == s}.count() == 0)
                cavesystem.caves.add(Cave(s))

        val cave1 = cavesystem.caves.filter { it.name == splitstring[0]}.first()
        val cave2 = cavesystem.caves.filter { it.name == splitstring[1]}.first()
        cave1.addConnection(cave2)

    }
    return cavesystem
}

fun part1()
{

    var cavesystem = makecavelist(numbers)
    println(cavesystem.traverse(cavesystem.caves.filter { it.isStart()}.first(), mutableListOf("start")))
    println("---- PART1 -----")
  //  println(cavesystem.paths)
    println(cavesystem.paths.size)
    var otherpaths = cavesystem.paths.map { it.replace("[","").replace("]","").replace(" ","") }
   // println(testpaths)

    println(testpaths.filter { !otherpaths.contains(it)})

}

part1()