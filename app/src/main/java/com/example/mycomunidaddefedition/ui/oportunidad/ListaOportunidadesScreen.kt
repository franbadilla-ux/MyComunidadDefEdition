package com.example.mycomunidaddefedition.ui.oportunidad

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaOportunidadesScreen(
    viewModel: OportunidadViewModel,
    onAdd: () -> Unit,
    onEdit: (Long) -> Unit
) {
    val opportunities by viewModel.opportunities.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Lista de oportunidades")
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Open the form for a new opportunity.
            Button(
                onClick = onAdd,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar oportunidad")
            }

            if (opportunities.isEmpty()) {

                Text(
                    text = "Todavía no hay oportunidades registradas.",
                    style = MaterialTheme.typography.bodyLarge
                )

            } else {

                Text(
                    text = "Oportunidades registradas: ${opportunities.size}",
                    style = MaterialTheme.typography.titleMedium
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = opportunities,
                        key = { opportunity -> opportunity.id }
                    ) { opportunity ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onEdit(opportunity.id)
                                }
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement =
                                    Arrangement.spacedBy(6.dp)
                            ) {

                                Text(
                                    text = opportunity.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text(
                                    text = "Tipo: ${opportunity.tipo}"
                                )

                                Text(
                                    text = "Prioridad: ${opportunity.prioridad}"
                                )

                                Text(
                                    text = "Estado: ${opportunity.estado}"
                                )

                                Text(
                                    text = "Potencial: ${opportunity.potencial}"
                                )

                                Text(
                                    text = "Toca para editar",
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}