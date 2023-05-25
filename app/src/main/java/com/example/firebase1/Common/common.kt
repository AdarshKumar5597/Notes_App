package com.example.firebase1.Common

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun CommonDialogue(){
    Dialog(onDismissRequest = {}) {
        CircularProgressIndicator()
    }
}