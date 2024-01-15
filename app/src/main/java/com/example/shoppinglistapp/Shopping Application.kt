package com.example.shoppinglistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class ShoppingClass(
    var id : Int,
    var item : String,
    var quantity : Int,
    var isEditable : Boolean
)

@Composable
fun ShoppingList(){

    var stateItem by remember { mutableStateOf(listOf<ShoppingClass>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("")}
     var itemQuantity by remember { mutableStateOf("")}
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    )
    {
        Button(onClick = {showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally))
        {
            Text(text = "Add item")
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    )
    {
        item(stateItem){

        }
    }

    if(showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {},
            title ={ Text(text = "Add Shopping Item")},
            text = {
                Column {
                    OutlinedTextField(value = itemName,
                        onValueChange = { itemName = it},
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                        )

                    OutlinedTextField(value = itemQuantity,
                        onValueChange = { itemQuantity = it},
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShoppingList(){
    ShoppingList()
}