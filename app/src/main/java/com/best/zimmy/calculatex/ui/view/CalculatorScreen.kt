package com.best.zimmy.calculatex.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.best.zimmy.calculatex.data.CalculatorRepository
import com.best.zimmy.calculatex.ui.theme.CalculateXTheme
import com.best.zimmy.calculatex.ui.viewmodel.CalculatorViewModel


@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "Compute",
            fontSize = 24.sp,
            fontWeight = FontWeight.Thin,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Result",
            fontSize = 48.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(16.dp)
        )
        CalculatorButtonGrid(viewModel)
    }
}

@Composable
fun CalculatorButtonGrid(viewModel: CalculatorViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(viewModel.gridItems.size) { index ->
            CalculatorButton(
                viewModel.gridItems[index],
                calculate = { value -> viewModel.onButtonPressed(value) })
        }
    }
}

@Composable
fun CalculatorButton(text: String, calculate: (String) -> Unit) {
    Button(
        onClick = { calculate(text) },
        shape = RoundedCornerShape(24.dp), // Rounded corners similar to the image
        colors = ButtonDefaults.buttonColors(contentColor = Color(0xFF3B82F6)), // Blue color for the button background
        modifier = Modifier
            .width(84.dp) // Fixed width for the button
            .height(84.dp) // Fixed height for the button
    ) {
        Text(
            text = text, // Display "+" as text
            color = Color.White, // White text color
            fontSize = 24.sp, // Font size for the plus sign
            fontWeight = FontWeight.Bold // Bold style to make it stand out
        )
    }
}

@Preview
@Composable
fun CalculatorScreenPreview() {
    CalculateXTheme {
        CalculatorScreen(CalculatorViewModel(CalculatorRepository()))
    }
}