package com.jakubrzeznicki.apartmentmanager.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jakubrzeznicki.apartmentmanager.R
import com.jakubrzeznicki.apartmentmanager.home.model.HomeUiState
import com.jakubrzeznicki.apartmentmanager.home.viewmodel.HomeViewModel
import com.jakubrzeznicki.apartmentmanager.ui.theme.ApartmentManagerTheme
import com.jakubrzeznicki.apartmentmanager.ui.theme.Typography

/**
 * Created by jrzeznicki on 14/04/2023.
 */
@Composable
fun HomeRoute(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val pinListState = rememberLazyListState()
    //LaunchedEffect(key1 = "SETUP_HOME_KEY") { viewModel.() }
    HomeScreen(
        uiState = uiState,
        listState = pinListState,
        onCreateNewPinClick = { },
        onDeletePinClick = { viewModel.deletePin(it) },
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    listState: LazyListState,
    onCreateNewPinClick: () -> Unit,
    onDeletePinClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(onCreateNewPinClick = onCreateNewPinClick)
        },
        floatingActionButton = {
            FloatingActionButton(onCreateNewPinClick = onCreateNewPinClick)
        },
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = listState
        ) {
            when (uiState) {
                is HomeUiState.HasPins -> {
                    items(items = uiState.pins) {
                        PinCard(name = it.name, code = it.code, onDeletePinClick = onDeletePinClick)
                    }
                }
                is HomeUiState.NoData -> {
                    item {
                        EmptyState(
                            modifier = Modifier.fillMaxWidth(),
                            textId = R.string.lack_of_pins
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(128.dp)
                                    .padding(8.dp),
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(id = R.string.lack_of_pins),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onCreateNewPinClick: () -> Unit) {
    AppTopBar(
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
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        actions = actions,
        windowInsets = windowInsets,
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun FloatingActionButton(onCreateNewPinClick: () -> Unit) {
    FloatingActionButton(
        onClick = onCreateNewPinClick,
        containerColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(16.dp),
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(id = R.string.create),
            tint = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Composable
private fun EmptyState(
    modifier: Modifier,
    textId: Int,
    content: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content?.invoke()
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = textId),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PinCard(name: String, code: String, onDeletePinClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
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
                tint = MaterialTheme.colorScheme.onSecondary
            )
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                PinItemColumn(modifier = Modifier, value = name)
                Spacer(modifier = Modifier.size(8.dp))
                PinItemColumn(modifier = Modifier, value = code)
            }
        }
    }
}

@Composable
private fun PinItemColumn(modifier: Modifier, value: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.name),
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSecondary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = value,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun TestPinCard() {
    ApartmentManagerTheme {
        PinCard(name = "Alarm do drzwi", code = "17381943", onDeletePinClick = {})
    }
}