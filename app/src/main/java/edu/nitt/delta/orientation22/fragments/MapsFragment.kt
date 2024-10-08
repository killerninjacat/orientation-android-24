package edu.nitt.delta.orientation22.fragments

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import edu.nitt.delta.orientation22.compose.markerImages
import edu.nitt.delta.orientation22.compose.screens.MapScreen
import edu.nitt.delta.orientation22.di.viewModel.actions.MapAction
import edu.nitt.delta.orientation22.di.viewModel.actions.TeamAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.MapStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.TeamStateViewModel
import edu.nitt.delta.orientation22.models.MarkerModel
import edu.nitt.delta.orientation22.ui.theme.black

@Composable
fun MapFragment(
    teamStateViewModel: TeamStateViewModel,
    mapviewModel: MapStateViewModel,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = black
    ){
        mapviewModel.doAction(MapAction.GetRoute)
        var routeList = mapviewModel.routeListData.value
        var markerList : List<MarkerModel> = listOf()
        var currentClue = ""
        var currentClueLocation = LatLng(10.7614246, 78.8139187)
        var currentglbUrl = "miyawaki.glb"
        var currentanchorHash="hello"
        var currentScale = 1.0
        mapviewModel.doAction(MapAction.GetCurrentLevel)
        var currentLevel = mapviewModel.currentState.value
        Log.d("MapScreen",routeList.toString())
        routeList.forEach {marker->
            val markerModel = MarkerModel(
                markerState = MarkerState(LatLng(marker.latitude,marker.longitude)),
                markerDescription = marker.clue,
                markerTitle = marker.name,
                markerImage = markerImages[marker.position]!!,
                isVisible = marker.position < currentLevel
            )
            markerList = markerList+markerModel
            if (marker.position == currentLevel){
                currentClue = marker.clue
                currentClueLocation = LatLng(marker.latitude, marker.longitude)
                currentglbUrl = marker.glbUrl
                currentanchorHash = marker.anchorHash
                currentScale = marker.scale
            }
        }
        teamStateViewModel.doAction(TeamAction.GetTeam)
        val currentPoints = teamStateViewModel.teamData.value.points
        MapScreen(markerList, currentClue, currentClueLocation,currentglbUrl,currentanchorHash, currentScale, currentLevel, currentPoints)
    }
}
