package com.app.upmworker.di


import android.content.Context
import com.consoli.githubrepository.di.qualifier.Base
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.io.InputStream


@Excludes(com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule::class)
@GlideModule
class OkHttpLibraryGlideModule : AppGlideModule() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GlideEntryComponent {
        @Base
        fun okHttpClient(): OkHttpClient
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        val my: GlideEntryComponent = EntryPoints.get(context, GlideEntryComponent::class.java)
        my.okHttpClient().let {
            registry.replace(
                GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(
                    it
                )
            )
        }
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}