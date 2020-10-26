package com.mp.cubit_architecture.foundation

/**
 * This represents a navigation action.
 *
 * Feel free to create your own navigation actions inheriting from this one.
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */
abstract class NavigationAction

/**
 * This is an action for back navigation.
 */
object NavigateBack : NavigationAction()