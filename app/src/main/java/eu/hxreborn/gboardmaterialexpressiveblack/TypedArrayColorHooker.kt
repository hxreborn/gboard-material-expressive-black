package eu.hxreborn.gboardmaterialexpressiveblack

import android.content.res.Configuration
import android.content.res.TypedArray
import io.github.libxposed.api.XposedInterface
import io.github.libxposed.api.XposedInterface.AfterHookCallback
import io.github.libxposed.api.annotations.AfterInvocation
import io.github.libxposed.api.annotations.XposedHooker

@XposedHooker
class TypedArrayColorHooker : XposedInterface.Hooker {

    companion object {
        private const val AMOLED_BLACK = 0xFF000000.toInt()
        private const val SURFACE_CONTAINER_PREFIX = "system_surface_container"
        private const val HIGH_VARIANT_MARKER = "high"

        @JvmStatic
        @AfterInvocation
        fun afterGetColor(callback: AfterHookCallback) {
            val context = HookContext.from(callback) ?: return
            if (!context.isDarkMode) return

            val resourceName = context.resolveResourceName() ?: return

            // Preserve borders and accent colors
            if (resourceName.startsWith(SURFACE_CONTAINER_PREFIX)
                && !resourceName.contains(HIGH_VARIANT_MARKER)
            ) {
                callback.result = AMOLED_BLACK
            }
        }
    }

    private class HookContext private constructor(
        private val typedArray: TypedArray,
        private val index: Int,
    ) {
        val isDarkMode: Boolean
            get() = (typedArray.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

        fun resolveResourceName(): String? {
            val colorId = typedArray.getResourceId(index, 0)
            if (colorId == 0) return null

            return runCatching {
                typedArray.resources.getResourceEntryName(colorId)
            }.getOrNull()
        }

        companion object {
            fun from(callback: AfterHookCallback): HookContext? {
                val typedArray = callback.thisObject as? TypedArray ?: return null
                val args = callback.args
                val index = args.getOrNull(0) as? Int ?: return null
                typedArray.resources ?: return null

                return HookContext(typedArray, index)
            }
        }
    }
}
