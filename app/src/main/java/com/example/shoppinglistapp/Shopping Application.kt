package com.example.shoppinglistapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import java.util.UUID

data class ShoppingClass(
    var id : UUID,
    var item : String,
    var quantity : Int,
    var isEditable: Boolean = false
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
                    items ->
                if (items.isEditable){
                    ShoppingItemEditor(item = items,
                        onEditComplete = {
                                editedName, editedQuantity ->
                            stateItem.map { it.copy(isEditable = false) }
                            stateItem = stateItem.map { it.copy(isEditable = false) }
                            val editedItem = stateItem.find { it.id == items.id }
                            editedItem?.let {
                                it.item = editedName
                                it.quantity = editedQuantity
                            }
                        }
                    )
                }
                else{
                    ShoppingListItem(item = items,
                        onEditClick = {
                            stateItem = stateItem.map {it.copy(isEditable = it.id == items.id)} },
                        onDeleteClick = {
                            stateItem = stateItem - items}
                    )
                }
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
                                id = UUID.randomUUID(),
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
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.item, modifier = Modifier.padding(16.dp))
        Text(text = item.quantity.toString(), modifier = Modifier.padding(16.dp))

        Row(
            modifier = Modifier.padding(8.dp),
        ) {
            IconButton(onClick = onEditClick )
            {
                Icon(imageVector =Icons.Default.Edit ,
                    contentDescription = "Edit Button")
            }

            IconButton(onClick = onDeleteClick )
            {
                Icon(imageVector = Icons.Default.Delete ,
                    contentDescription = "Delete Button")
            }
        }
    }
}
@Composable
fun ShoppingItemEditor(
    item: ShoppingClass,
    onEditComplete: (String, Int) -> Unit
) {
    var onEditItem by remember { mutableStateOf(item.item) }
    var onEditQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditable by remember { mutableStateOf(item.isEditable) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            BasicTextField(value = onEditItem,
                onValueChange = {onEditItem = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )

            BasicTextField(value = onEditQuantity,
                onValueChange = {onEditQuantity = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
        }

        Button(onClick = {
            isEditable = false
            onEditComplete(onEditItem, onEditQuantity.toIntOrNull() ?: 1)
        })
        {
            Text(text = "Save")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShoppingList(){
    ShoppingList()
}