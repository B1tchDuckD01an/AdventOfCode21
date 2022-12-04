import java.io.File

val numbers = File("test.txt").readText()

class Target(val xrange:IntRange,val yrange:IntRange)

fun Target.IsInArea(x:Int,y:Int):Boolean {
    if (x in xrange && y in yrange)
        return true
    else
        return false
}