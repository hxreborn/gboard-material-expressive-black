package eu.hxreborn.gboardmaterialexpressiveblack

import android.content.res.TypedArray
import android.util.Log
import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam
import io.github.libxposed.api.XposedModuleInterface.PackageReadyParam

class GboardAmoledModule : XposedModule() {
    override fun onModuleLoaded(param: ModuleLoadedParam) {
        log(Log.INFO, TAG, "GboardAmoled v${BuildConfig.VERSION_NAME} loaded")
    }

    override fun onPackageReady(param: PackageReadyParam) {
        if (param.packageName != TARGET_PACKAGE || !param.isFirstPackage) return

        val method =
            runCatching {
                TypedArray::class.java.getDeclaredMethod(
                    "getColor",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType,
                )
            }.getOrElse {
                log(Log.ERROR, TAG, "Method not found: ${it.message}")
                return
            }

        runCatching {
            TypedArrayColorHooker.hook(this, method)
        }.onSuccess {
            log(Log.INFO, TAG, "Hooked TypedArray.getColor")
        }.onFailure {
            log(Log.ERROR, TAG, "Hook failed: ${it.message}")
        }
    }

    companion object {
        const val TAG = "GboardAmoled"
        private const val TARGET_PACKAGE = "com.google.android.inputmethod.latin"
    }
}
