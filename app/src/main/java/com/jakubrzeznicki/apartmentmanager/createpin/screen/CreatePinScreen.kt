package com.jakubrzeznicki.apartmentmanager.createpin.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jakubrzeznicki.apartmentmanager.R
import com.jakubrzeznicki.apartmentmanager.createpin.model.CreatePinStatus
import com.jakubrzeznicki.apartmentmanager.createpin.model.CreatePinUiState
import com.jakubrzeznicki.apartmentmanager.createpin.viewmodel.CreatePinViewModel
import com.jakubrzeznicki.apartmentmanager.ui.theme.DarkBlue
import com.jakubrzeznicki.apartmentmanager.ui.theme.LighterGrey
import com.jakubrzeznicki.apartmentmanager.ui.theme.Typography

/**
 * Created by jrzeznicki on 15/04/2023.
 */
@Composable
fun CreatePinRoute(
    viewModel: CreatePinViewModel = hiltViewModel(),
    showSnackbar: (String, SnackbarDuration) -> Unit,
    onNavigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CreatePinScreen(
        uiState = uiState,
        onNavigateUp = onNavigateUp,
        onNameChange = { viewModel.onNameChange(it) },
        showSnackbar = showSnackbar,
        onCreatePinClick = { viewModel.createPin() },
        resetStatus = { viewModel.resetStatus() }
    )
}

@Composable
private fun CreatePinScreen(
    uiState: CreatePinUiState,
    onNavigateUp: () -> Unit,
    onNameChange: (String) -> Unit,
    showSnackbar: (String, SnackbarDuration) -> Unit,
    onCreatePinClick: () -> Unit,
    resetStatus: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(onNavigateUp = onNavigateUp)
        },
        containerColor = LighterGrey,
        contentColor = DarkBlue
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.name,
                labelId = R.string.name,
                onValueChange = onNameChange
            )
            Spacer(modifier = Modifier.size(16.dp))
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.code,
                labelId = R.string.code,
                readOnly = true
            )
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = { onCreatePinClick() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    text = stringResource(id = R.string.create_pin)
                )
            }
        }
        CustomSnackbar(
            uiState = uiState,
            showSnackbar = showSnackbar,
            onNavigateUp = onNavigateUp,
            resetStatus = resetStatus
        )
    }
}

@Composable
private fun CustomTextField(
    modifier: Modifier,
    value: String,
    @StringRes labelId: Int,
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    )
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        readOnly = readOnly,
        textStyle = MaterialTheme.typography.bodyMedium,
        label = {
            Text(
                text = stringResource(id = labelId),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = DarkBlue,
            focusedTextColor = DarkBlue,
            focusedBorderColor = DarkBlue,
            focusedLabelColor = DarkBlue,
            unfocusedTextColor = DarkBlue.copy(0.8F),
            unfocusedLabelColor = DarkBlue.copy(0.8F),
            unfocusedBorderColor = DarkBlue.copy(0.8F),
        ),
        shape = RoundedCornerShape(16.dp),
        keyboardOptions = keyboardOptions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onNavigateUp: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.create_pin),
                color = MaterialTheme.colorScheme.onPrimary,
                style = Typography.headlineSmall
            )
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .size(32.dp),
                onClick = { onNavigateUp() }
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = stringResource(id = R.string.navigate_up),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBlue)
    )
}

@Composable
private fun CustomSnackbar(
    uiState: CreatePinUiState,
    showSnackbar: (String, SnackbarDuration) -> Unit,
    onNavigateUp: () -> Unit,
    resetStatus: () -> Unit
) {
    val pinCreateMessage = stringResource(id = R.string.pin_created_successfully)
    val emptyNameMessage = stringResource(id = R.string.empty_name_error)
    LaunchedEffect(uiState.status) {
        when (uiState.status) {
            CreatePinStatus.PinCreated -> {
                showSnackbar(pinCreateMessage, SnackbarDuration.Short)
                onNavigateUp()
            }
            CreatePinStatus.EmptyName -> {
                showSnackbar(emptyNameMessage, SnackbarDuration.Short)
                resetStatus()
            }
            CreatePinStatus.NameIsNotUnique -> {
                showSnackbar("Namess", SnackbarDuration.Short)
                resetStatus()
            }
            CreatePinStatus.NoStatus -> Unit
        }
    }
}