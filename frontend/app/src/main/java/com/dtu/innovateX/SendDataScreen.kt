package com.example.bluetoothapp.ui

//
//@Composable
//fun SendDataScreen() {
//    val bluetoothManager: BluetoothManager = viewModel()
//    var message by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        TextField(
//            value = message,
//            onValueChange = { message = it },
//            label = { Text("Message") }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = { bluetoothManager.sendMessage(message) }) {
//            Text("Send")
//        }
//    }
//}
