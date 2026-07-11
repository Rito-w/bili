package blbl.cat3399.feature.player.engine

internal fun ijkPluginReleaseUrl(abi: String): String {
    val safeAbi = abi.trim()
    return "https://github.com/Rito-w/bili/releases/latest/download/libijkplayer-$safeAbi.zip"
}
