# Keep LSPosed module entry point (required for module discovery)
-keep class eu.hxreborn.gboardmaterialexpressiveblack.GboardAmoledModule { *; }

# Keep all hooker classes (required for @XposedHooker annotation processing)
-keep @io.github.libxposed.api.annotations.XposedHooker class * { *; }
-keep class eu.hxreborn.gboardmaterialexpressiveblack.TypedArrayColorHooker { *; }

# Keep all module classes (prevents stripping of hook targets)
-keep class eu.hxreborn.gboardmaterialexpressiveblack.** { *; }

# Keep libxposed API annotations (required for hook registration)
-keep class io.github.libxposed.api.annotations.** { *; }
-keepattributes *Annotation*
-keepattributes RuntimeVisibleAnnotations

# Prevent R8 from removing seemingly unused hook methods
-keepclassmembers class * {
    @io.github.libxposed.api.annotations.BeforeInvocation <methods>;
    @io.github.libxposed.api.annotations.AfterInvocation <methods>;
}
