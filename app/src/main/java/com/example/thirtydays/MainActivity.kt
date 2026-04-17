package com.example.thirtydays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thirtydays.model.Place
import com.example.thirtydays.model.PlacesRepository
import com.example.thirtydays.ui.theme.ThirtyDaysTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThirtyDaysTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ThirtyDaysApp(PlacesRepository.places)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirtyDaysApp(places: List<Place>, modifier: Modifier = Modifier) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge
                )},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { it ->
        LazyColumn(contentPadding = it) {
            items(places.size) { index ->
                PlaceItem(
                    place = places[index],
                    modifier = Modifier.padding(8.dp)
                    )
            }
        }
    }

}

@Composable
fun PlaceItem(place: Place, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "arrow_rotation"
    )

    Card(
        modifier = modifier
            .clickable { expanded = !expanded }
            .animateContentSize()
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
            ) {
            Text(
                text = stringResource(id = place.nameRes),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.displayMedium
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Expand button",
                modifier = Modifier.rotate(rotation)
            )
        }
        Image(
            painter = painterResource(id = place.imageRes),
            contentDescription = null,
            modifier = Modifier.padding(12.dp))
        if (expanded) {
            Text(
                text = stringResource(id = place.descriptionRes),
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
@Preview
fun PlaceItemPreview() {
    ThirtyDaysTheme {
        PlaceItem(PlacesRepository.places[0])
    }
}
@Composable
@Preview
fun AppPreview() {
    ThirtyDaysTheme {
        ThirtyDaysApp(PlacesRepository.places)
    }
}