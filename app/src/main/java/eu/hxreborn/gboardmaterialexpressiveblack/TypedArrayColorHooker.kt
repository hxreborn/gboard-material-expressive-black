package eu.hxreborn.gboardmaterialexpressiveblack

import android.content.res.Configuration
import android.content.res.TypedArray
import eu.hxreborn.gboardmaterialexpressiveblack.GboardAmoledModule.Companion.TAG
import io.github.libxposed.api.XposedModule
import java.lang.reflect.Method

object TypedArrayColorHooker {
    private const val AMOLED_BLACK = 0xFF000000.toInt()
    private const val SURFACE_CONTAINER_PREFIX = "system_surface_container"
    private const val HIGH_VARIANT_MARKER = "high"

    fun hook(
        module: XposedModule,
        method: Method,
    ) {
        module.hook(method).intercept { chain ->
            val result = chain.proceed()
            val typedArray = chain.thisObject as? TypedArray ?: return@intercept result
            val config = typedArray.resources?.configuration ?: return@intercept result

            val nightMode = config.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (nightMode != Configuration.UI_MODE_NIGHT_YES) return@intercept result

            val index = chain.getArg(0) as? Int ?: return@intercept result
            val colorId = typedArray.getResourceId(index, 0)
            if (colorId == 0) return@intercept result

            val name =
                runCatching { typedArray.resources.getResourceEntryName(colorId) }
                    .getOrNull() ?: return@intercept result

            if (name.startsWith(SURFACE_CONTAINER_PREFIX) && !name.contains(HIGH_VARIANT_MARKER)) {
                AMOLED_BLACK
            } else {
                result
            }
        }
    }
}
