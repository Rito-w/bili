package blbl.cat3399.feature.player

internal data class PlayerRenderGeometry(
    val width: Int,
    val height: Int,
) {
    val aspectRatio: Float
        get() = width.toFloat() / height.toFloat()
}

internal fun resolvePlayerRenderGeometry(
    width: Int,
    height: Int,
    rotate: Int,
): PlayerRenderGeometry? {
    if (width <= 0 || height <= 0) return null
    return if (rotate == 1) {
        PlayerRenderGeometry(width = height, height = width)
    } else {
        PlayerRenderGeometry(width = width, height = height)
    }
}
