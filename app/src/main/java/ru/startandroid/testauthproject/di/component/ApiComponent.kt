package ru.startandroid.testauthproject.di.component

import dagger.Component
import ru.startandroid.testauthproject.di.module.ApiModule
import ru.startandroid.testauthproject.di.scope.ApiScope
import ru.startandroid.testauthproject.server.ServerCommunicator

@ApiScope
@Component(modules = [ApiModule::class])
interface ApiComponent {
    val serverCommunicator: ServerCommunicator
}