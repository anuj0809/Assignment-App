package com.assignments.kaagaz.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoDispatchers


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultDispatchers


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainDispatchers