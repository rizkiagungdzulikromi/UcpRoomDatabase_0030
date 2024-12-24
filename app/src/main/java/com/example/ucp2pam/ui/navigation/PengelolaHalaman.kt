package com.example.ucp2pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2pam.ui.view.dokter.DetailJadwalView
import com.example.ucp2pam.ui.view.dokter.DokterView
import com.example.ucp2pam.ui.view.dokter.HomeView
import com.example.ucp2pam.ui.view.dokter.InsertDokterView
import com.example.ucp2pam.ui.view.dokter.InsertJadwalView
import com.example.ucp2pam.ui.view.dokter.JadwalView
import com.example.ucp2pam.ui.view.dokter.UpdateJadwalView


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = DestinasiHome.route) {
        composable(route = DestinasiHome.route) {
            HomeView(
                onNavigateDokter = {
                    navController.navigate(DestinasiDokter.route)
                },
                onNavigateJaddwal = {
                    navController.navigate(DestinasiJadwal.route)
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiDokter.route) {
            DokterView(
                onBack = {
                    navController.popBackStack()
                },
                onAddDokter = {
                    navController.navigate(DestinasiInsertDr.route)
                },
                onNavigateDokter = {
                    navController.navigate(DestinasiDokter.route)
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiJadwal.route) {
            JadwalView(
                onBack = {
                    navController.popBackStack()
                },
                onAddJadwal = {
                    navController.navigate(DestinasiInsertJd.route)
                },
                onDetail = { idAntrian ->
                    navController.navigate("${DestinasiDetail.route}/$idAntrian")
                },
                onNavigateJadwal = {
                    navController.navigate(DestinasiJadwal.route)
                },
                onClick = { },
                modifier = modifier
            )
        }

        composable(route = DestinasiInsertDr.route) {
            InsertDokterView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateDokter = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiInsertJd.route) {
            InsertJadwalView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateJadwal = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiDetail.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.idAntrian) {
                    type = NavType.StringType
                }
            )
        ) {
            val idAntrian = it.arguments?.getString(DestinasiDetail.idAntrian)
            idAntrian?.let { idAntrianValue ->
                DetailJadwalView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = { kode ->
                        navController.navigate("${DestinasiUpdate.route}/$kode")
                    },
                    onDeleteClick = {
                        navController.popBackStack()
                    },
                    idAntrian = idAntrianValue,
                    modifier = modifier
                )
            }
        }

        composable(
            route = DestinasiUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.idAntrian) {
                    type = NavType.StringType
                }
            )
        ) {
            val idAntrian = it.arguments?.getString(DestinasiUpdate.idAntrian)
            idAntrian?.let { kode ->
                UpdateJadwalView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigateJadwal = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }
    }
}