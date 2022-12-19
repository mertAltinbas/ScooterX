package com.example.scooterx.presentation.screens.map.view

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dnm.presentation.screens.detail.DetailPage
import com.example.scooterx.R
import com.example.scooterx.core.OnError
import com.example.scooterx.core.OnSuccess
import com.example.scooterx.domain.entities.Scooter
import com.example.scooterx.presentation.components.SheetAppBarX
import com.example.scooterx.presentation.components.bitmapDescriptorFromVector
import com.example.scooterx.presentation.screens.map.view_model.MapRepo
import com.example.scooterx.presentation.screens.map.view_model.MapViewModel
import com.example.scooterx.presentation.screens.map.view_model.ViewModelFactory
import com.example.scooterx.presentation.screens.user_profile.UserProfile
import com.example.scooterx.presentation.ui.theme.ScooterXTheme
import com.example.scooterx.presentation.utils.PermissionState
import com.example.scooterx.presentation.utils.checkSelfPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.asStateFlow

@Suppress("NonAsciiCharacters", "UNCHECKED_CAST")
class MapScreenActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScooterXTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.isStatusBarVisible = false
                BackHandler(true) {
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val fineLocation = checkSelfPermissionState(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    ScooterList(fineLocation)
                }
            }
        }
    }

    @Composable
    fun ScooterList(
        fineLocation: PermissionState,
        modelsViewModel: MapViewModel = viewModel(
            factory = ViewModelFactory(
                MapRepo()
            )
        )
    ) {
        when (val modelsList =
            modelsViewModel.modelsStateFlow.asStateFlow().collectAsState().value) {
            is OnError -> {
                Text(text = "Please try again")
            }
            is OnSuccess -> {
                val hasLocationPermission by fineLocation.hasPermission.collectAsState()
                if (hasLocationPermission) {
                    val listofScooter = modelsList.querySnapshot?.toObjects(Scooter::class.java)
                    listofScooter?.let {
                        GoogleMapView(it)
                    }
                } else {
                    fineLocation.launchPermissionRequest()
                }
            }
            else -> {}
        }
    }

    @Composable
    fun GoogleMapView(data: List<Scooter>) {
        val context = LocalContext.current
        val location = LatLng(38.450337690826714, 27.211078336118597)
        val cameraPosition = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(location, 13f)
        }
        Box {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPosition,
            ) {
                data.forEach { stops ->
                    val scooterLocation =
                        LatLng(stops.latitude.toDouble(), stops.longitude.toDouble())
                    Marker(
                        state = MarkerState(scooterLocation),
                        title = stops.plaka,
                        snippet = "Battery Level " + stops.battery + "%",
                        onInfoWindowClick = {
                            context.startActivity(DetailPage.newIntent(context, stops))
                        },
                        icon = bitmapDescriptorFromVector(
                            context,
                            R.drawable.scootericon,
                            R.color.black,
                        )
                    )
                }
                Marker(
                    title = "Your Location",
                    state = MarkerState(location),
                    icon = bitmapDescriptorFromVector(
                        context,
                        R.drawable.locationicon
                    )
                ) {
                }
            }
            SheetAppBarX(onTap = { context.startActivity(Intent(context, UserProfile::class.java)) })
        }
    }
}
