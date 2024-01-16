package com.example.shoppinglistapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class ShoppingClass(
    var id : Int,
    var item : String,
    var quantity : Int
)

@Composable
fun ShoppingList() {

    var stateItem by remember { mutableStateOf(listOf<ShoppingClass>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    )
    {
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        {
            Text(text = "Add item")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
        {
            items(stateItem){
                ShoppingListItem(item = it,
                    onEditClick = {},
                    onDeleteClick = {})
            }
        }
    }

    if(showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Button(onClick = {
                        if (itemName.isNotBlank()){
                            val itemList = ShoppingClass(
                                id = stateItem.size+1,
                                item = itemName,
                                quantity = itemQuantity.toInt()
                            )
                            stateItem = stateItem + itemList
                            showDialog = false
                            itemName = ""
                            itemQuantity = ""
                        }
                    })
                    {
                        Text(text = "Add")
                    }

                    Button(onClick = { showDialog = false })
                    {
                        Text(text = "Cancel")
                    }
                }
            },
            title ={ Text(text = "Add Shopping Item")},
            text = {
                Column {
                    OutlinedTextField(value = itemName,
                        onValueChange = { itemName = it},
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    OutlinedTextField(value = itemQuantity,
                        onValueChange = { itemQuantity = it},
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        )
    }
}

@Composable
fun ShoppingListItem(
    item : ShoppingClass,
    onEditClick : () -> Unit,
    onDeleteClick : () -> Unit
){
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, color = Color(0xFFD0BCFF)),
                shape = RoundedCornerShape(20)
            )
    ) {
        Text(text = item.item, modifier = Modifier.padding(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShoppingList(){
    ShoppingList()
}