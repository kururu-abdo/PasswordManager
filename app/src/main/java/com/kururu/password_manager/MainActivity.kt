package com.kururu.password_manager

import android.Manifest
import android.app.Application
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.hardware.biometrics.BiometricPrompt.*
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.kururu.password_manager.data.database.AppDatabase
import com.kururu.password_manager.data.models.Account
import com.kururu.password_manager.data.models.AccountType
import com.kururu.password_manager.data.repos.AccountRepoistory
import com.kururu.password_manager.data.repos.AccountTypeRepository
import com.kururu.password_manager.ui.theme.Password_ManagerTheme
import com.kururu.password_manager.ui.theme.textFieldColor
import com.kururu.password_manager.viewmodels.MainViewModel
import com.kururu.password_manager.views.*

class MainActivity : ComponentActivity() {
  var cancellationSignal:CancellationSignal ?= null
    val uri = "https://passwordsmanager.com"
    lateinit var  repository: AccountRepoistory


    @OptIn(ExperimentalAnimationApi::class)
    @RequiresApi(Build.VERSION_CODES.M)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val myDb = AppDatabase.getInstance(LocalContext.current)
            val accountDao = myDb.accountDao()
            repository = AccountRepoistory(accountDao)

            val accountTypeDao = myDb.accountTypeDao()
         var   typesDao = AccountTypeRepository(accountTypeDao)
   var brands :MutableList<AccountType> = ArrayList()

            brands.add(
                AccountType(1,"Facebook",
            R.drawable.facebook
                )
            )

            brands.add(AccountType(2,"Github",
                R.drawable.github
                ) )
            brands.add(AccountType(3,"Gmail",
                R.drawable.gmail
            ) )
            brands.add(AccountType(4,"Heroku",
                R.drawable.heroku
            ) )

            brands.add(AccountType(5,"Instagram",
                R.drawable.instagram
            ) )
            brands.add(AccountType(6,"Twitter",
                R.drawable.twitter
            ) )
            brands.add(AccountType(7,"Yahoo",
                R.drawable.yahoo
            ) )
            brands.add(AccountType(8,"Other",
                R.drawable.other
            ) )

brands.forEach {
    typesDao.insertAccountType(it)
}



          var  navController = rememberNavController()




            Password_ManagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                   // Greeting("Android")


                    NavHost(navController = navController, startDestination = "/splash") {
                        composable("/" ,

                            deepLinks = listOf(navDeepLink { uriPattern = "$uri" })

                        ) {
                           Home(navController , { laucnFingerPrint(
                               navController, password="" ,email=""
                           ) })


                           // LockScreen()
                        }
composable("/splash" ){
    SplashScreen(navController = navController)
}

                        composable("/dev" ){
                            MyProfile(navController = navController)
                        }
                        composable("/new" ,

                            deepLinks = listOf(navDeepLink { uriPattern = "$uri/new" })

                        ) { NewPassword(navController) }


                        composable("/details?password={password}&email={email}"){
                                backStackEntry ->

                        var password=  backStackEntry.arguments?.getString("password")
                        var email =    backStackEntry.arguments?.getString("email")
                            AccountDetails(email = email!!, password = password!! , navController = navController)
                        }


                        composable("/search?query={query}"){
                                backStackEntry ->

                            var query=  backStackEntry.arguments?.getString("query")



                            SearchResultContainer(navController = navController, query =query!!,
                                { laucnFingerPrint(navController , password="" ,email="") }
                                )
                        }
                    }

                }
            }
        }
    }


private  val  authenticationCallback: AuthenticationCallback
=
    @RequiresApi(Build.VERSION_CODES.P)
    object : AuthenticationCallback(){
        override fun onAuthenticationSucceeded(result: AuthenticationResult?) {
            super.onAuthenticationSucceeded(result)

            Toast.makeText(this@MainActivity ,"Success" ,Toast.LENGTH_LONG).show()

        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Toast.makeText(this@MainActivity ,"Authentication Failed" ,Toast.LENGTH_LONG).show()

        }

        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
            super.onAuthenticationHelp(helpCode, helpString)
        }
}

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkBiometicSupport():Boolean{
var keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (keyguardManager.isDeviceSecure){
            return true
        }
        if (ActivityCompat.checkSelfPermission(this , Manifest.permission.USE_BIOMETRIC)!= PackageManager.PERMISSION_GRANTED){
            return false
        }
return packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun  laucnFingerPrint(

        navController: NavController,
         password:String ="" ,email:String=""){
        if (checkBiometicSupport())else {
            navController.navigate("/details?password=${password}&email=${email}")

        }
    }

    private  fun  getCanellationSignal():CancellationSignal{
        cancellationSignal = CancellationSignal()
        cancellationSignal!!.setOnCancelListener {
            Toast.makeText(this@MainActivity ,"Canceld" ,Toast.LENGTH_LONG).show()
        }
        return  cancellationSignal as CancellationSignal
    }
}

@Composable
fun Greeting(name: String) {
   Box( contentAlignment = Alignment.Center) {
      Column (
          horizontalAlignment = Alignment.CenterHorizontally
              ){
          IconButton(

              onClick = {

var dialog =androidx.biometric.BiometricPrompt.PromptInfo.Builder()
    .setTitle("Hello")
    .setSubtitle("Hello Mate")
    .setDescription("we need to use your finger print to secure your data")
    .setNegativeButtonText("Cancel")
    .setConfirmationRequired(true)
    .build()



              }) {
              Icon(
                  painter = painterResource(id = R.drawable.ic_fingerprint),
                  contentDescription = "Favorite",
                  modifier = Modifier.size(60.dp)
              )


          }
          Spacer(Modifier.size(ButtonDefaults.IconSpacing))
          Text("FingerPrint For Auth")
      }
   }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Password_ManagerTheme {
        Greeting("Android")
    }
}

@Composable
fun  NewPassword(navController: NavController,

                 viewModel: MainViewModel =
                     MainViewModel(LocalContext.current.applicationContext as Application)

){
    val allTypes by viewModel.allTypes.observeAsState(listOf())

    val materialBlue700= Color.Transparent
    val scaffoldState = rememberScaffoldState()
    val textState = remember { mutableStateOf(TextFieldValue()) }
    var passwordHasError by remember { mutableStateOf(false) }
    var passwordLabel by remember { mutableStateOf("Enter password") }

    val commentTextState = remember { mutableStateOf(TextFieldValue()) }
    var  commentHasError by remember { mutableStateOf(false) }
    var commentLabel by remember { mutableStateOf("Comment") }


    val emailTextState = remember { mutableStateOf(TextFieldValue()) }
    var emailHasError by remember { mutableStateOf(false) }
    var emailLabel by remember { mutableStateOf("Email or Phone") }

    var obsecured by remember {
        mutableStateOf(false)
    }
    var expanded by remember { mutableStateOf(false) }

//    val items = listOf("A", "B", "C", "D",                                   "E", "F")
//
//    val disabledValue = "B"
    var selectedIndex by remember { mutableStateOf(0) }

    val mContext = LocalContext.current
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {Text("New Password" ,

            )},
            backgroundColor = materialBlue700 ,
            elevation = 0.0.dp ,
            navigationIcon = {
                IconButton(onClick = {navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )



                }
            }
            )  },
        floatingActionButtonPosition = FabPosition.End,


        content = {

           Column(
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier.fillMaxSize(),
               content = {
               Box(modifier = Modifier
                   .size(
                       250.dp, 60.dp
                   )

                   .wrapContentSize(Alignment.Center)) {


//allTypes[selectedIndex].accountTypeName!!
                   Text(
                      if (allTypes.isEmpty())
                          ""
                       else
                          allTypes[selectedIndex].accountTypeName!!
                       ,modifier = Modifier
                           .fillMaxWidth()
                           .clickable(onClick = { expanded = true })
                           .background(
                               Color.White
                           ))
                   DropdownMenu(
                       expanded = expanded,
                       onDismissRequest = { expanded = false },
                       modifier = Modifier
                           .size(
                               250.dp
                           )
                           .background(
                               Color(0xFFEFEFEF)
                           )
                   ) {
                       allTypes.forEachIndexed { index, s ->
                           DropdownMenuItem(onClick = {
                               selectedIndex = index
                               expanded = false
                           }) {
                               Image(
                                   painterResource(s.icon!!),
                                   contentDescription = "",
                                   contentScale = ContentScale.Crop,
                                   modifier = Modifier.size(20.dp ,20.dp)
                               )
                               Spacer(modifier = Modifier.width(8.dp))
                               Text(text = s.accountTypeName!!)
                           }
                       }
                   }
               }

                   Box(modifier = Modifier
                       .size(
                           250.dp, 60.dp
                       )
                       .wrapContentSize(Alignment.Center)){
                     TextField(
                           value = emailTextState.value,
                           onValueChange = { emailTextState.value = it }
                           ,
                           label = { Text(text = emailLabel)} ,
                           isError = emailHasError,
                         shape = RoundedCornerShape(8.dp),
                         singleLine = true,


                         colors = TextFieldDefaults.textFieldColors(
                             backgroundColor =  textFieldColor,
                             cursorColor = Color.Black,
                             disabledLabelColor =  textFieldColor,
                             focusedIndicatorColor = Color.Transparent,
                             unfocusedIndicatorColor = Color.Transparent
                         ),
                       )


                   }
               Box(modifier = Modifier
                   .size(
                       250.dp, 60.dp
                   )
                   .wrapContentSize(Alignment.Center)){
               TextField(

                   value = textState.value,
                   label = {Text(passwordLabel)},
                       isError = passwordHasError,
                   shape = RoundedCornerShape(8.dp),
                   singleLine = true,


                   colors = TextFieldDefaults.textFieldColors(
                       backgroundColor =  textFieldColor,
                       cursorColor = Color.Black,
                       disabledLabelColor =  textFieldColor,
                       focusedIndicatorColor = Color.Transparent,
                       unfocusedIndicatorColor = Color.Transparent
                   ),
                   onValueChange = { textState.value = it } ,

                   visualTransformation = if (obsecured) VisualTransformation.None else PasswordVisualTransformation(),
                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                   trailingIcon = {
                       val image = if (obsecured)
                           Icon(
                               painter = painterResource(id = R.drawable.ic_visibility_on),
                               contentDescription = "visibilty_on",

                           )
                       else   Icon(
                           painter = painterResource(id = R.drawable.ic_visibility_off),
                           contentDescription = "visibilty_off",

                       )

                       // Please provide localized description for accessibility services
                       val description = if (obsecured) "Hide password" else "Show password"

                       IconButton(onClick = {obsecured = !obsecured}){
                           image
                       }
                   }

               )}



                       Box(modifier = Modifier
                           .size(
                               250.dp, 60.dp
                           )
                           .wrapContentSize(Alignment.Center)){
                           TextField(
                               value = commentTextState.value,
                               isError = commentHasError,
                               shape = RoundedCornerShape(8.dp),
                               singleLine = true,


                               colors = TextFieldDefaults.textFieldColors(
                                   backgroundColor = textFieldColor,
                                   cursorColor = Color.Black,
                                   disabledLabelColor =  textFieldColor,
                                   focusedIndicatorColor = Color.Transparent,
                                   unfocusedIndicatorColor = Color.Transparent
                               ),
                               onValueChange = { commentTextState.value = it }
                            ,
                               label = { Text(text =commentLabel)}
                           )


                       }

                   Spacer(modifier = Modifier.height(100.dp))
                   Box(
                       modifier = Modifier
                           .size(250.dp, 60.dp)

                           .wrapContentSize(Alignment.Center)) {



                       Button(

                           modifier = Modifier.size(
                                250.dp , 60.dp
                           ),
                           shape = RoundedCornerShape(10.dp),
                           onClick = {
when {
    textState.value.text.isEmpty() -> {
        passwordHasError = true
        passwordLabel = "password cannot be empty"
    }
    commentTextState.value.text.isEmpty()  -> {
        commentHasError = true
        commentLabel = "please  , leave comment about password"
    }

    emailTextState.value.text.isEmpty()  -> {
        emailHasError = true
        emailLabel = " email or Phone  ar required"
    }
    else -> {
        //save to database
        var account =Account( null  ,
        allTypes[selectedIndex!!].uid!! ,
            emailTextState.value.text ,
            commentTextState.value.text ,
            textState.value.text
            )
viewModel.inserAccount(account)
  Toast.makeText(mContext ,"Account Added" ,Toast.LENGTH_LONG).show()
    }
}
                       },
                           colors = ButtonDefaults.textButtonColors(
                           backgroundColor = Color(0xFF3E92CC)

                       )) {
                           Text("Save" ,
                               style = TextStyle(color = Color.White)

                               )
                       }
                   }
           })




                  },
    )

}