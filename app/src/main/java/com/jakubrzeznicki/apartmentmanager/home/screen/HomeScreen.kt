package com.jakubrzeznicki.apartmentmanager.home.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jakubrzeznicki.apartmentmanager.R
import com.jakubrzeznicki.apartmentmanager.home.model.DeletePinData
import com.jakubrzeznicki.apartmentmanager.home.model.HomeStatus
import com.jakubrzeznicki.apartmentmanager.home.model.HomeUiState
import com.jakubrzeznicki.apartmentmanager.home.viewmodel.HomeViewModel
import com.jakubrzeznicki.apartmentmanager.ui.theme.DarkBlue
import com.jakubrzeznicki.apartmentmanager.ui.theme.LighterGrey
import com.jakubrzeznicki.apartmentmanager.ui.theme.Typography

/**
 * Created by jrzeznicki on 14/04/2023.
 */
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    showSnackbar: (String, SnackbarDuration) -> Unit,
    onCreatePinClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pinListState = rememberLazyListState()
    LaunchedEffect(key1 = "PLAYERS_KEY") { viewModel.getPins() }
    HomeScreen(
        uiState = uiState,
        listState = pinListState,
        onCreateNewPinClick = onCreatePinClick,
        onDeletePinClick = { viewModel.deletePin(it) },
        showConfirmDeleteDialog = { viewModel.showConfirmDeleteDialog(it) },
        resetStatus = { viewModel.resetStatus() },
        showSnackbar = showSnackbar
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    listState: LazyListState,
    onCreateNewPinClick: () -> Unit,
    onDeletePinClick: (String) -> Unit,
    showConfirmDeleteDialog: (DeletePinData) -> Unit,
    resetStatus: () -> Unit,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(onCreateNewPinClick = onCreateNewPinClick)
        },
        floatingActionButton = {
            FloatingActionButton(onCreateNewPinClick = onCreateNewPinClick)
        },
        containerColor = LighterGrey,
        contentColor = DarkBlue
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(bottom = 40.dp),
            state = listState
        ) {
            when (uiState) {
                is HomeUiState.HasPins -> {
                    items(items = uiState.pins) {
                        PinCard(
                            name = it.name,
                            code = it.code,
                            deletePinData = uiState.deletePinData,
                            onDeletePinClick = onDeletePinClick,
                            showConfirmDeleteDialog = showConfirmDeleteDialog
                        )
                    }
                }
                is HomeUiState.NoData -> {
                    item {
                        EmptyState(
                            modifier = Modifier.fillMaxSize(),
                            textId = R.string.lack_of_pins
                        )
                    }
                }
            }
        }
        CustomSnackbar(uiState = uiState, showSnackbar = showSnackbar, resetStatus = resetStatus)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onCreateNewPinClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.apartment_pins),
                color = MaterialTheme.colorScheme.onPrimary,
                style = Typography.headlineSmall
            )
        },
        actions = {
            IconButton(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .size(24.dp),
                onClick = { onCreateNewPinClick() }
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = stringResource(id = R.string.create),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBlue)
    )
}

@Composable
private fun FloatingActionButton(onCreateNewPinClick: () -> Unit) {
    FloatingActionButton(
        onClick = onCreateNewPinClick,
        containerColor = MaterialTheme.colorScheme.tertiary,
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(id = R.string.create),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun EmptyState(modifier: Modifier, textId: Int) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(128.dp)
                .padding(8.dp),
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = textId),
        )
        Text(
            text = stringResource(id = textId),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun PinCard(
    name: String,
    code: String,
    deletePinData: DeletePinData,
    onDeletePinClick: (String) -> Unit,
    showConfirmDeleteDialog: (DeletePinData) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkBlue),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 16.dp),
                imageVector = Icons.Filled.Lock,
                contentDescription = stringResource(id = R.string.lock),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                PinItemColumn(value = name, labelId = R.string.name)
                Spacer(modifier = Modifier.size(8.dp))
                PinItemRow(value = code, labelId = R.string.code)
            }
            DeletePinButton(
                deletePinData = deletePinData,
                code = code,
                onDeletePinClick = onDeletePinClick,
                showConfirmDeleteDialog = showConfirmDeleteDialog
            )
        }
    }
}

@Composable
private fun PinItemColumn(modifier: Modifier = Modifier, value: String, labelId: Int) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = labelId),
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Text(
            text = value,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}

@Composable
private fun PinItemRow(modifier: Modifier = Modifier, value: String, labelId: Int) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "${stringResource(id = labelId)}:",
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = value,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
private fun DeletePinButton(
    deletePinData: DeletePinData,
    code: String,
    onDeletePinClick: (String) -> Unit,
    showConfirmDeleteDialog: (DeletePinData) -> Unit
) {
    IconButton(
        modifier = Modifier
            .size(50.dp)
            .padding(horizontal = 16.dp),
        onClick = { showConfirmDeleteDialog(DeletePinData(code, true)) }
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
    if (deletePinData.shouldShowConfirmDialog && code == deletePinData.code) {
        InformationDialog(
            titleId = R.string.delete_pin_title,
            textId = R.string.delete_pin_description,
            code = deletePinData.code,
            confirmButtonTextId = R.string.delete,
            confirmButtonAction = onDeletePinClick,
            dismissAction = showConfirmDeleteDialog
        )
    }
}

@Composable
private fun InformationDialog(
    titleId: Int,
    textId: Int,
    code: String,
    confirmButtonTextId: Int,
    confirmButtonAction: (String) -> Unit,
    dismissAction: (DeletePinData) -> Unit
) {
    val deletePinData = remember { mutableStateOf(DeletePinData(code, false)) }
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.background,
        title = { Text(stringResource(titleId)) },
        text = { Text(stringResource(textId)) },
        dismissButton = {
            Button(
                onClick = { dismissAction(deletePinData.value) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue.copy(alpha = 0.6F),
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    confirmButtonAction(code)
                    dismissAction(deletePinData.value)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = stringResource(confirmButtonTextId))
            }
        },
        onDismissRequest = { dismissAction(deletePinData.value) }
    )
}

@Composable
private fun CustomSnackbar(
    uiState: HomeUiState,
    showSnackbar: (String, SnackbarDuration) -> Unit,
    resetStatus: () -> Unit
) {
    val pinDeletedMessage = stringResource(id = R.string.pin_deleted_successfully)
    LaunchedEffect(uiState.status) {
        when (uiState.status) {
            HomeStatus.PinDeleted -> {
                showSnackbar(pinDeletedMessage, SnackbarDuration.Short)
                resetStatus()
            }
            is HomeStatus.PinDeleteError -> {
                showSnackbar(
                    (uiState.status as HomeStatus.PinDeleteError).errorMessage,
                    SnackbarDuration.Short
                )
                resetStatus()
            }
            HomeStatus.NoStatus -> Unit
        }
    }
}