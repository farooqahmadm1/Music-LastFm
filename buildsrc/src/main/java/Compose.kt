object Compose {
    private const val activityComposeVersion = "1.3.0-rc01"
    const val activity = "androidx.activity:activity-compose:$activityComposeVersion"

    /*
     * Compose Version
     * https://developer.android.com/jetpack/androidx/releases/compose
     */
    const val composeVersion = "1.2.1"
    const val ui = "androidx.compose.ui:ui:$composeVersion"
    const val material = "androidx.compose.material:material:$composeVersion"
    const val tooling = "androidx.compose.ui:ui-tooling:$composeVersion"

    //Icons
    const val icons = "androidx.compose.material:material-icons-core:$composeVersion"
    const val iconsExtended = "androidx.compose.material:material-icons-extended:$composeVersion"

    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    const val foundation = "androidx.compose.foundation:foundation:$composeVersion"

    // LiveData
    const val liveData = "androidx.compose.runtime:runtime-livedata:$composeVersion"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"

    private const val hiltNavigationComposeVersion = "1.0.0-alpha03"
    const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"
}

object ComposeTest {
    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Compose.composeVersion}"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Compose.composeVersion}"
}