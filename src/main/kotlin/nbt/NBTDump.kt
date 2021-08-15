package nbt

import nbt.datatypes.Tag
import nbt.datatypes.TagCompound
import nbt.datatypes.TagList

class NBTDump {

    fun dump(tag: Tag, level: Int = -1) {
        when(tag) {
            is TagCompound -> {
                for(i in 0..level) print("\t")
                println("TagCompound('${tag.name}'): ${tag.getTags().size} entries")
                for(i in 0..level) print("\t")
                println("{")
                tag.getTags().forEach {
                    dump(it, level + 1)
                }
                for(i in 0..level) print("\t")
                println("}")
            }
            is TagList<*> -> {
                for(i in 0..level) print("\t")
                println("TagList('${tag.name}'): ${tag.getList().size} entries")
                for(i in 0..level) print("\t")
                println("{")
                tag.getList().forEach {
                    dump(it, level + 1)
                }
                for(i in 0..level) print("\t")
                println("}")
            }
            else -> {
                for(i in 0..level) print("\t")
                println("${tag::class.java.simpleName}('${tag.name}'): ${tag.value}")
            }
        }
    }
}