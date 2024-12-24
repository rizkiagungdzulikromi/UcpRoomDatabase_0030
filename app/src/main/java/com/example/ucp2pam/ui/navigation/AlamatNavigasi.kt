package com.example.ucp2pam.ui.navigation

AlamatNavigasi

package com.example.ucp2pam.ui.navigation


interface AlamatNavigasi {
    val route: String
}

object DestinasiInsertDr: AlamatNavigasi {
    override val route: String = "insert_dokter"
}

object DestinasiInsertJd: AlamatNavigasi {
    override val route: String = "insert_Jd"
}

object DestinasiHome: AlamatNavigasi {
    override val route: String = "home"
}

object DestinasiDokter: AlamatNavigasi {
    override val route: String = "dokter"
}

object DestinasiJadwal: AlamatNavigasi {
    override val route: String = "jadwal"
}

object DestinasiUpdate: AlamatNavigasi {
    override val route: String = "update_jadwal"
    const val idAntrian = "idAntrian"
    val routeWithArg = "$route/{$idAntrian}"
}

object DestinasiDetail: AlamatNavigasi {
    override val route: String = "detail_Jadwal"
    const val idAntrian = "idAntrian"
    val routeWithArg = "$route/{$idAntrian}"
}





