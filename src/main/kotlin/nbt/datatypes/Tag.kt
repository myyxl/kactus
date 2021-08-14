package nbt.datatypes

abstract class Tag(
    open val typeId: Int,
    val name: String,
    val value: Any) {
    abstract fun serialize(): ByteArray
}
