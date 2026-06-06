package eu.hxreborn.gboardmaterialexpressiveblack

import android.content.res.TypedArray
import android.util.Log
import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam
import io.github.libxposed.api.XposedModuleInterface.PackageReadyParam

// Top-level handle to the loaded module. Assigned in onModuleLoaded before any hook
// fires so TypedArrayColorHook can call module.hook(...) and module.log(...) without
// a companion-object reach-back.
@PublishedApi
internal lateinit var module: GboardAmoledModule
    private set

internal const val TAG = "GboardAmoled"
private const val TARGET_PACKAGE = "com.google.android.inputmethod.latin"

class GboardAmoledModule : XposedModule() {
    override fun onModuleLoaded(param: ModuleLoadedParam) {
        module = this
        log(Log.INFO, TAG, "loaded version=${BuildConfig.VERSION_NAME}")
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
                log(Log.ERROR, TAG, "resolve getColor failed pkg=$TARGET_PACKAGE", it)
                return
            }

        runCatching {
            TypedArrayColorHook.hook(method)
        }.onSuccess {
            log(Log.INFO, TAG, "hooked typed-array-color pkg=$TARGET_PACKAGE")
        }.onFailure {
            log(Log.ERROR, TAG, "hook typed-array-color failed pkg=$TARGET_PACKAGE", it)
        }
    }
}
