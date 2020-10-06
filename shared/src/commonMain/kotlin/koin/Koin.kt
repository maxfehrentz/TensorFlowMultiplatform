package koin

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(appModule, platformModule, coreModule)
    }
    return koinApplication
}

private val coreModule = module {

}

expect val platformModule: Module
