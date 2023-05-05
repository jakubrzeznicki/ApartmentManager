package com.jakubrzeznicki.apartmentmanager.di

import com.jakubrzeznicki.apartmentmanager.createpin.CreatePinComponent
import com.jakubrzeznicki.apartmentmanager.home.HomeComponent
import dagger.Module

/**
 * Created by jrzeznicki on 05/05/2023.
 */
@Module(subcomponents = [HomeComponent::class, CreatePinComponent::class])
class AppSubcomponents