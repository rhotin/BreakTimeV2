package com.rhappdeveloper.breaktime.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(innerPadding: PaddingValues) {

    var pickedTime by remember {
        mutableStateOf(LocalTime.now())
    }
    var pickedBreakTime by remember {
        mutableIntStateOf(30)
    }
    var finishedBreakTime by remember {
        mutableStateOf(LocalTime.now().plusMinutes(pickedBreakTime.toLong()))
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }
    val formattedFinishedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(finishedBreakTime)
        }
    }

    val timeDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            timeDialogState.show()
        }) {
            Text(text = "Pick Time")
        }
        Text(text = formattedTime)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = pickedBreakTime.toString(),
            onValueChange = {
                pickedBreakTime = if (it.isNotBlank() && it.isDigitsOnly()) {
                    it.toInt()
                } else {
                    0
                }
                finishedBreakTime = pickedTime.plusMinutes(pickedBreakTime.toLong())
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Break Amount") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = formattedFinishedTime,
            fontSize = 40.sp
        )
    }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "OK")
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.now(),
            title = "Pick a Time"
        ) {
            pickedTime = it
            finishedBreakTime = pickedTime.plusMinutes(pickedBreakTime.toLong())
        }
    }
}