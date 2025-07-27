package com.example.shiftracker.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shiftracker.data.Shift

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(vm: ShiftViewModel) {
    val uiState by vm.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ShiftForm(
                onAddShift = { shift -> vm.addShift(shift) },
                modifier = Modifier
                    .weight(1f)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large)
                    .padding(28.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large)
                    .padding(20.dp)
            ) {
                TotalEarningsBox(uiState.totalEarnings)
                Spacer(Modifier.height(10.dp))
                AnimatedContent(
                    targetState = uiState.shifts,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(400)) with fadeOut(animationSpec = tween(400))
                    }
                ) { shifts ->
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        items(shifts, key = { it.id }) { shift ->
                            ShiftCard(
                                shift = shift,
                                onDelete = { vm.deleteShift(it) },
                                calcPay = vm::calcPay
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShiftForm(
    onAddShift: (Shift) -> Unit,
    modifier: Modifier = Modifier
) {
    // Sample form UI: replace with your actual fields and logic
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            Text("Add Shift", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Date") },
                leadingIcon = { Icon(Icons.Default.Event, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp),
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )
            // ...Repeat for other fields: Time, Wage, etc. Use appropriate icons

            Button(
                onClick = { /* onAddShift(...) */ },
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Add Shift", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ShiftCard(shift: Shift, onDelete: (Shift) -> Unit, calcPay: (Shift) -> Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Event, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "${shift.date}: ${shift.startTime} - ${shift.endTime}",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.width(6.dp))
                    Text("Break: ${shift.breakMinutes} min (${if (shift.paidBreak) "Paid" else "Unpaid"})", color = MaterialTheme.colorScheme.onSurface)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.width(6.dp))
                    Text("Pay: ${calcPay(shift)} ${shift.currency}", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                }
                if (!shift.notes.isNullOrEmpty()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Notes, contentDescription = null, tint = Color.Gray)
                        Spacer(Modifier.width(6.dp))
                        Text("Notes: ${shift.notes}", color = Color.Gray, fontWeight = FontWeight.Light)
                    }
                }
            }
            IconButton(
                onClick = { onDelete(shift) },
                modifier = Modifier
                    .size(48.dp) // Large touch target
                    .clip(MaterialTheme.shapes.extraSmall)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
        }
    }
}

@Composable
fun TotalEarningsBox(total: Double) {
    Card(
        modifier = Modifier
            .padding(bottom = 24.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
            Spacer(Modifier.width(10.dp))
            Text(
                "Total Earnings: $total",
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}