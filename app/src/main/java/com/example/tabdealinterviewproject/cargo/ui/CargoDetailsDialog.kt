package com.example.tabdealinterviewproject.cargo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.domain.model.CargoRepoModel
import com.example.tabdealinterviewproject.cargo.CargoViewModel

@Composable
fun CargoDetailsDialog(
    cargoItem: CargoRepoModel?,
    viewModel: CargoViewModel,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }

                    Text(
                        text = "جزئیات بار",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    DetailRow(label = "مبدا:", value = cargoItem?.origin ?: "")
                    DetailRow(label = "مقصد:", value = cargoItem?.destination ?: "")
                    DetailRow(label = "وزن:", value = "${cargoItem?.weight ?: ""} تن")
                    DetailRow(label = "بار:", value = cargoItem?.type ?: "")
                    DetailRow(label = "بسته‌بندی:", value = cargoItem?.packaging ?: "")
                    DetailRow(label = "تاریخ بارگیری:", value = cargoItem?.loadingDate ?: "")

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            cargoItem?.let {
                                viewModel.selectCargo(it)
                            }
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("با ${cargoItem?.price?:-1} میلیون تومان کرایه می‌برم")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, textAlign = TextAlign.Start)
    }
}