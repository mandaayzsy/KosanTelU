package com.manda0101.kosantelu.ui.screen

import androidx.lifecycle.ViewModel
import com.manda0101.kosantelu.model.Kosan

class MainViewModel : ViewModel() {
    val data = listOf(
        Kosan(1, "Kosan Mawar", "Jl. Sukabirus No.12", "Rp 750.000"),
        Kosan(2, "Kosan Melati", "Jl. Telekomunikasi No.5", "Rp 1.000.000"),
        Kosan(3, "Kosan Sakura", "Jl. Cibiru Hilir No.7", "Rp 900.000"),
        Kosan(4, "Kosan Bougenville", "Jl. Cisitu Lama", "Rp 850.000"),
        Kosan(5, "Kosan Matahari", "Jl. Margacinta No.10", "Rp 920.000"),
        Kosan(6, "Kosan Anggrek", "Jl. Sukabirus No.15", "Rp 700.000"),
        Kosan(7, "Kosan Exlusif", "Jl. Sukapura No.3", "Rp 1.500.000"),
    )
}