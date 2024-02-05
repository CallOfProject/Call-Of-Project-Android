package callofproject.dev.mobile.android.view

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import callofproject.dev.mobile.android.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun RegisterScreen(navController : NavController)
{
    var mFirstName by remember { mutableStateOf("") }
    var mMiddleName by remember { mutableStateOf("") }
    var mLastName by remember { mutableStateOf("") }
    var mUsername by remember { mutableStateOf("") }
    var mEmail by remember { mutableStateOf("") }
    var mPassword by remember { mutableStateOf("") }
    var mConfirmPassword by remember { mutableStateOf("") }
    var mBirthDate by remember { mutableStateOf("") }
    val context = LocalContext.current
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())) {
            Image(painter = painterResource(id = R.drawable.cop_logo), contentDescription = "Logo", modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally), contentScale = ContentScale.FillWidth)

            OutlinedTextField(value = mFirstName, onValueChange = {
                mFirstName = it
            }, label = { Text("First Name", color = Color.Gray) }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF295a8c), unfocusedBorderColor = Color.Gray))


            OutlinedTextField(value = mMiddleName, onValueChange = {
                mMiddleName = it
            }, label = { Text("Middle Name", color = Color.Gray) }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF295a8c), unfocusedBorderColor = Color.Gray))


            OutlinedTextField(value = mLastName, onValueChange = {
                mLastName = it
            }, label = { Text("Last Name", color = Color.Gray) }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF295a8c), unfocusedBorderColor = Color.Gray))


            OutlinedTextField(value = mUsername, onValueChange = {
                mUsername = it
            }, label = { Text("username", color = Color.Gray) }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF295a8c), unfocusedBorderColor = Color.Gray))

            OutlinedTextField(value = mEmail, onValueChange = {
                mEmail = it
            }, label = { Text("email", color = Color.Gray) }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF295a8c), unfocusedBorderColor = Color.Gray))

            OutlinedTextField(value = mPassword, onValueChange = {
                mPassword = it
            }, label = { Text("password", color = Color.Gray) }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF295a8c), unfocusedBorderColor = Color.Gray))

            OutlinedTextField(value = mConfirmPassword, onValueChange = {
                mConfirmPassword = it
            }, label = { Text("Confirm Password", color = Color.Gray) }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF295a8c), unfocusedBorderColor = Color.Gray))

            Row {

                Text(text = "Birth Date: ", modifier = Modifier.padding(8.dp), fontSize = 17.sp)
                Spacer(modifier = Modifier.padding(2.dp))
                Button(onClick = {
                    showDatePicker(context) { year, month, day ->
                        run {
                            val pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                            mBirthDate = LocalDate.of(year, month + 1, day).format(pattern)
                        }
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295a8c))) {
                    Text(mBirthDate.ifEmpty { "Select Birth Date" })
                }
            }

            Button(onClick = {
                Toast.makeText(context, "First Name: ${mFirstName}\n" + "Middle Name: ${mMiddleName}\n" + "Last Name: ${mLastName}\n" + "Username: ${mUsername}\n" + "Email: ${mEmail}\n" + "Password: ${mPassword}\n" + "Confirm Password: ${mConfirmPassword}\n" + "Birth Date: ${mBirthDate}\n", Toast.LENGTH_LONG)
                    .show()
            }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295a8c))) {
                Text("Register")
            }
        }
    }
}

fun registerUser(navController : NavController)
{
    TODO("Not yet implemented")
}


fun showDatePicker(context : Context,
                   onDateSelected : (year : Int, month : Int, dayOfMonth : Int) -> Unit)
{
    val calendar = Calendar.getInstance()
    DatePickerDialog(context, { _, year, month, dayOfMonth ->
        onDateSelected(year, month, dayOfMonth)
    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
}