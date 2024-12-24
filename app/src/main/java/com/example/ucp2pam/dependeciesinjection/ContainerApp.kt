package com.example.ucp2pam.dependeciesinjection

package com.example.ucp2pam.dependeciesinjection

import android.content.Context
import com.example.ucp2pam.data.database.KlinikDatabase
import com.example.ucp2pam.repository.LocalRepositoryDr
import com.example.ucp2pam.repository.LocalRepositoryJdw
import com.example.ucp2pam.repository.RepositoryDr
import com.example.ucp2pam.repository.RepositoryJdw


interface InterfaceContainerApp {
    val repositoryDr: RepositoryDr
    val repositoryJdw: RepositoryJdw
}

class ContainerApp(private val context: Context): InterfaceContainerApp {
    override val repositoryDr: RepositoryDr by lazy {
        LocalRepositoryDr(KlinikDatabase.getDatabase(context).dokterDao())
    }
    override val repositoryJdw: RepositoryJdw by lazy {
        LocalRepositoryJdw(KlinikDatabase.getDatabase(context).jadwalDao())
    }
}
