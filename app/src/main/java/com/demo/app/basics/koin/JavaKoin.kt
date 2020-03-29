package com.hg.basics.koin

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import org.koin.core.KoinApplication
import org.koin.core.definition.DefinitionFactory
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.TypeQualifier
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeID
import org.koin.ext.getFullName
import org.koin.java.KoinJavaComponent.getKoin

object JavaKoin {

    @JvmStatic
    fun createScope(context: Context): Scope {
        return getKoin().createScope(scopeId(context), nameOf(context))
    }

    @JvmStatic
    fun createScopeWithContext(context: Context): Scope {
        val result = createScope(context)
        setScopeContext(result, context)
        return result
    }

    @JvmStatic
    fun scopeOf(context: Context): Scope {
        return getKoin().getScope(scopeId(context))
    }

    private fun nameOf(instance: Any): Qualifier {
        return TypeQualifier(instance::class)
    }

    private fun scopeId(instance: Any) : ScopeID {
        return instance::class.getFullName() + "@" + System.identityHashCode(instance)
    }

    @JvmStatic
    fun setAndroidContext(koinApp: KoinApplication, androidContext: Context): KoinApplication {
        val registry = koinApp.koin.rootScope.beanRegistry
        registry.saveDefinition(DefinitionFactory.createSingle { androidContext })
        if (androidContext is Application)
            registry.saveDefinition(DefinitionFactory.createSingle<Application> { androidContext })
        return koinApp
    }

    @JvmStatic
    fun setScopeContext(scope: Scope, androidContext: Context) {
        val registry = scope.beanRegistry
        registry.saveDefinition(DefinitionFactory.createSingle { androidContext })
        if (androidContext is AppCompatActivity)
            registry.saveDefinition(DefinitionFactory.createSingle<AppCompatActivity> { androidContext })
    }

}