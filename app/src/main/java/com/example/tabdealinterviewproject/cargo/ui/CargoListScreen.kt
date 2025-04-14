package com.example.tabdealinterviewproject.cargo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.CargoRepoModel
import com.example.tabdealinterviewproject.R
import com.example.tabdealinterviewproject.cargo.CargoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CargoListScreen(
    viewModel: CargoViewModel
) {
    val cargoItems = viewModel.cargoItems.collectAsState(initial = emptyList())
    val selectedLoad = viewModel.selectedItem.collectAsState(initial = null)
    var showDialog by remember { mutableStateOf(false) }
    var itemSelected by remember { mutableStateOf<CargoRepoModel?>(null) }


    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(
                    text = "لیست بارها",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        cargoItems.value.let { cargoItems ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cargoItems) { cargoItem ->
                    CargoCard(
                        cargoItem = cargoItem,
                        isSelected = selectedLoad.value?.id == cargoItem.id,
                        viewModel = viewModel,
                        onClick = {
                            if (!viewModel.lockedItems) {
                                showDialog = true
                                itemSelected = cargoItem
                            }
                        }
                    )
                }
            }
        }

        if (showDialog) {
            CargoDetailsDialog(
                viewModel = viewModel,
                cargoItem = itemSelected,
                onDismiss = { showDialog = false }
            )
        }
    }
}

@Composable
fun CargoCard(
    cargoItem: CargoRepoModel,
    isSelected: Boolean,
    viewModel : CargoViewModel,
    onClick: () -> Unit
) {

    val selectedItem by remember { mutableStateOf(viewModel.selectedItem) }

    Card(
        modifier = Modifier
            .fillMaxWidth()

            .clickable(enabled = !isSelected) { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        if (selectedItem.collectAsState(null).value?.id == cargoItem.id) {
            Box(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "بار ${cargoItem.origin} به ${cargoItem.origin} انتخاب شده است",
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.End,
                    )

                    Text(
                        text = "لغو بار",
                        color = Color.Red,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.clickable {
                            viewModel.clearSelectedCargo()
                        }
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_rectangle),
                    contentDescription = "orgin",
                    tint = Color.Gray,
                )

                Text(
                    text = "${cargoItem.origin} (${cargoItem.origin})",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.weight(1f).padding(start = 4.dp, top = 0.dp, end = 4.dp, bottom = 0.dp)
                )

                if (viewModel.lockedItems) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Selected",
                        tint = Color.Gray
                    )
                }
            }

            Box (modifier = Modifier.width(9.dp).height(24.dp).padding(horizontal = 4.dp, vertical = 0.dp).background(color = Color.Gray)){}

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_circle),
                    contentDescription = "des",
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${cargoItem.destination} (${cargoItem.destination})",
                    textAlign = TextAlign.Right,
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            Box (modifier = Modifier.height(9.dp).fillMaxWidth().padding(horizontal = 4.dp, vertical = 4.dp).background(color = colorResource(R.color.gray_01))){}

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${cargoItem.price}",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${cargoItem.weight}",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}