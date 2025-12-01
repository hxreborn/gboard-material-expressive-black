package eu.hxreborn.gboardmaterialexpressiveblack

import android.content.res.TypedArray
import io.github.libxposed.api.XposedInterface
import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam
import io.github.libxposed.api.XposedModuleInterface.PackageLoadedParam

internal lateinit var module: GboardAmoledModule

class GboardAmoledModule(base: XposedInterface, param: ModuleLoadedParam) : XposedModule(base, param) {

    init {
        module = this
        module.log("$TAG Module initialized")
    }

    override fun onPackageLoaded(param: PackageLoadedParam) {
        if (param.packageName != TARGET_PACKAGE || !param.isFirstPackage) {
            return
        }

        module.log("$TAG Loaded package: ${param.packageName}")

        val method = runCatching {
            TypedArray::class.java.getDeclaredMethod(
                "getColor",
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
        }.getOrElse {
            module.log("$TAG Error: ${it.message}")
            return
        }

        runCatching {
            module.hook(method, TypedArrayColorHooker::class.java)
            module.log("$TAG Successfully hooked TypedArray.getColor")
        }.onFailure {
            module.log("$TAG Hook failed: ${it.message}")
        }
    }

    companion object {
        const val TAG = "GboardAmoledModule"
        private const val TARGET_PACKAGE = "com.google.android.inputmethod.latin"
    }
}
