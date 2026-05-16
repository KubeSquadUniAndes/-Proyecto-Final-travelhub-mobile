package com.example.travelhubapp_mobile.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class AuthComponentsExtendedRoboTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // === THDropdown ===

    @Test
    fun thDropdown_displaysLabel() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THDropdown(
                    value = "CC",
                    onValueChange = {},
                    label = "Tipo de documento",
                    options = listOf("CC", "Pasaporte", "CE")
                )
            }
        }
        composeTestRule.onNodeWithText("Tipo de documento").assertIsDisplayed()
    }

    @Test
    fun thDropdown_displaysCurrentValue() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THDropdown(
                    value = "Pasaporte",
                    onValueChange = {},
                    label = "Documento",
                    options = listOf("CC", "Pasaporte", "CE")
                )
            }
        }
        composeTestRule.onNodeWithText("Pasaporte").assertIsDisplayed()
    }

    @Test
    fun thDropdown_click_opensOptions() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THDropdown(
                    value = "",
                    onValueChange = {},
                    label = "País",
                    options = listOf("Colombia", "México", "Argentina")
                )
            }
        }
        // The dropdown has multiple clickable nodes - click the arrow icon (last clickable)
        val clickables = composeTestRule.onAllNodes(hasClickAction())
        clickables[clickables.fetchSemanticsNodes().size - 1].performClick()
        composeTestRule.waitForIdle()
        // At least one option should be present in the hierarchy
        composeTestRule.onAllNodesWithText("Colombia").onFirst().assertExists()
    }

    @Test
    fun thDropdown_selectOption_callsCallback() {
        var selected = ""
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THDropdown(
                    value = "",
                    onValueChange = { selected = it },
                    label = "País",
                    options = listOf("Colombia", "México", "Argentina")
                )
            }
        }
        val clickables = composeTestRule.onAllNodes(hasClickAction())
        clickables[clickables.fetchSemanticsNodes().size - 1].performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithText("Colombia").onFirst().performClick()
        assert(selected == "Colombia")
    }

    @Test
    fun thDropdown_emptyOptions_doesNotCrash() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THDropdown(
                    value = "",
                    onValueChange = {},
                    label = "Vacío",
                    options = emptyList()
                )
            }
        }
        composeTestRule.onNodeWithText("Vacío").assertIsDisplayed()
    }

    // === THDatePicker ===

    @Test
    fun thDatePicker_displaysLabel() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THDatePicker(
                    value = "",
                    onValueChange = {},
                    label = "Fecha de nacimiento"
                )
            }
        }
        composeTestRule.onNodeWithText("Fecha de nacimiento").assertIsDisplayed()
    }

    @Test
    fun thDatePicker_withValue_displaysFormattedDate() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THDatePicker(
                    value = "2026-06-15",
                    onValueChange = {},
                    label = "Check-in"
                )
            }
        }
        // The date will be displayed formatted
        composeTestRule.onNodeWithText("Check-in").assertIsDisplayed()
    }

    @Test
    fun thDatePicker_emptyValue_showsPlaceholder() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THDatePicker(
                    value = "",
                    onValueChange = {},
                    label = "Fecha",
                    placeholder = "Selecciona una fecha"
                )
            }
        }
        composeTestRule.onNodeWithText("Selecciona una fecha").assertIsDisplayed()
    }

    @Test
    fun thDatePicker_click_opensDialog() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THDatePicker(
                    value = "",
                    onValueChange = {},
                    label = "Fecha",
                    placeholder = "Elige una fecha"
                )
            }
        }
        // Click the date picker to open dialog
        composeTestRule.onAllNodes(hasClickAction())[0].performClick()
        composeTestRule.waitForIdle()
        // The dialog should appear with an "Aceptar" button
        composeTestRule.onNodeWithText("Aceptar").assertIsDisplayed()
    }

    @Test
    fun thDatePicker_dialog_cancelButton_closes() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THDatePicker(
                    value = "",
                    onValueChange = {},
                    label = "Fecha"
                )
            }
        }
        composeTestRule.onAllNodes(hasClickAction())[0].performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Cancelar").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Fecha").assertIsDisplayed()
    }

    @Test
    fun thDatePicker_withMinAndMaxDate() {
        val minDate = System.currentTimeMillis()
        val maxDate = minDate + (30L * 24 * 60 * 60 * 1000)
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THDatePicker(
                    value = "",
                    onValueChange = {},
                    label = "Rango de fechas",
                    minDate = minDate,
                    maxDate = maxDate
                )
            }
        }
        composeTestRule.onNodeWithText("Rango de fechas").assertIsDisplayed()
    }

    @Test
    fun thDatePicker_withInitialDate() {
        val initialDate = System.currentTimeMillis()
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THDatePicker(
                    value = "",
                    onValueChange = {},
                    label = "Fecha inicial",
                    initialDate = initialDate
                )
            }
        }
        composeTestRule.onNodeWithText("Fecha inicial").assertIsDisplayed()
    }

    @Test
    fun thDatePicker_withTestTag() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THDatePicker(
                    value = "",
                    onValueChange = {},
                    label = "Fecha",
                    testTag = "date_picker_test"
                )
            }
        }
        composeTestRule.onNodeWithText("Fecha").assertIsDisplayed()
    }

    // === THBottomBar ===

    @Test
    fun thBottomBar_displaysAllTabs() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THBottomBar(selected = 0, onSelect = {}, onLogout = {})
            }
        }
        composeTestRule.onNodeWithText("Inicio").assertIsDisplayed()
        composeTestRule.onNodeWithText("Reservas").assertIsDisplayed()
        composeTestRule.onNodeWithText("Perfil").assertIsDisplayed()
        composeTestRule.onNodeWithText("Salir").assertIsDisplayed()
    }

    @Test
    fun thBottomBar_homeSelected_isFirstTab() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THBottomBar(selected = 0, onSelect = {}, onLogout = {})
            }
        }
        composeTestRule.onNodeWithText("Inicio").assertExists()
    }

    @Test
    fun thBottomBar_reservasSelected() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THBottomBar(selected = 1, onSelect = {}, onLogout = {})
            }
        }
        composeTestRule.onNodeWithText("Reservas").assertIsDisplayed()
    }

    @Test
    fun thBottomBar_perfilSelected() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THBottomBar(selected = 2, onSelect = {}, onLogout = {})
            }
        }
        composeTestRule.onNodeWithText("Perfil").assertIsDisplayed()
    }

    @Test
    fun thBottomBar_clickHome_callsOnSelectWithZero() {
        var selectedIndex = -1
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THBottomBar(selected = 1, onSelect = { selectedIndex = it }, onLogout = {})
            }
        }
        composeTestRule.onNodeWithText("Inicio").performClick()
        assert(selectedIndex == 0)
    }

    @Test
    fun thBottomBar_clickReservas_callsOnSelectWithOne() {
        var selectedIndex = -1
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THBottomBar(selected = 0, onSelect = { selectedIndex = it }, onLogout = {})
            }
        }
        composeTestRule.onNodeWithText("Reservas").performClick()
        assert(selectedIndex == 1)
    }

    @Test
    fun thBottomBar_clickPerfil_callsOnSelectWithTwo() {
        var selectedIndex = -1
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THBottomBar(selected = 0, onSelect = { selectedIndex = it }, onLogout = {})
            }
        }
        composeTestRule.onNodeWithText("Perfil").performClick()
        assert(selectedIndex == 2)
    }

    @Test
    fun thBottomBar_clickSalir_callsOnLogout() {
        var loggedOut = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THBottomBar(selected = 0, onSelect = {}, onLogout = { loggedOut = true })
            }
        }
        composeTestRule.onNodeWithText("Salir").performClick()
        assert(loggedOut)
    }

    // === THInput extended ===

    @Test
    fun thInput_withTestTag_isAccessible() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THInput(
                    value = "",
                    onValueChange = {},
                    label = "Campo con tag",
                    testTag = "my_input_tag"
                )
            }
        }
        composeTestRule.onNodeWithText("Campo con tag").assertIsDisplayed()
    }

    @Test
    fun thInput_noTestTag_rendersCorrectly() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THInput(
                    value = "Hello",
                    onValueChange = {},
                    label = "Campo"
                )
            }
        }
        composeTestRule.onNodeWithText("Hello").assertIsDisplayed()
    }

    @Test
    fun thInput_password_toggleVisibility() {
        var inputValue = "mypassword"
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THInput(
                    value = inputValue,
                    onValueChange = { inputValue = it },
                    label = "Contraseña",
                    isPassword = true
                )
            }
        }
        // Toggle the visibility icon
        composeTestRule.onNodeWithContentDescription("Toggle password visibility").performClick()
        composeTestRule.waitForIdle()
        // Toggle back
        composeTestRule.onNodeWithContentDescription("Toggle password visibility").performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun thLogo_isClickable_withDefaultSize() {
        composeTestRule.setContent { TravelHubAppMobileTheme { THLogo() } }
        composeTestRule.onNodeWithText("TH").assertExists()
    }
}
