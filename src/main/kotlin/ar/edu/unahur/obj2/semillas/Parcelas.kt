package ar.edu.unahur.obj2.semillas

abstract class Parcela(val ancho: Int, val largo: Int, val horasSolPorDia: Int) {
    val plantas = mutableListOf<Planta>()

    abstract fun seAsociaBienCon(planta: Planta): Boolean

    fun cantidadPlantas(): Int {
        return plantas.size
    }

    fun superficie() = ancho * largo

    fun cantidadMaximaPlantas() =
        if (ancho > largo) this.superficie() / 5
        else this.superficie() / 3 + largo

    fun espacioDisponible() = this.cantidadMaximaPlantas() - this.cantidadPlantas()

    fun estaAlMaximo() = espacioDisponible() == 0

    fun parcelaTieneComplicaciones(): Boolean {
        return this.plantas.any { it.horasDeSolQueTolera() < this.horasSolPorDia }
    }

    fun plantaNoToleraSolParcela(planta: Planta): Boolean {
        return planta.horasDeSolQueTolera() < this.horasSolPorDia - 2
    }

    fun plantar(planta: Planta) {
        if (planta.horasDeSolQueTolera() < this.horasSolPorDia - 2 ) {
            throw Exception("La planta se va quemar.")
        }
        else if (this.estaAlMaximo()) {
            throw Exception("No queda mas lugar.")
        }
        else {
            plantas.add(planta)
        }
    }
    fun plantasBienAsociadas(): List<Planta> {
        return plantas.filter { planta -> seAsociaBienCon(planta) }
    }
}

class ParcelaEcologica(ancho: Int, largo: Int, horasSolPorDia: Int): Parcela(ancho, largo, horasSolPorDia){
    override fun seAsociaBienCon(planta: Planta): Boolean {
        return !this.parcelaTieneComplicaciones() && planta.parcelaIdeal(this)
                && !plantaNoToleraSolParcela(planta) && !this.estaAlMaximo()
    }
}

class ParcelaIndustrial(ancho: Int, largo: Int, horasSolPorDia: Int): Parcela(ancho, largo, horasSolPorDia){
    override fun seAsociaBienCon(planta: Planta): Boolean {
        return this.cantidadPlantas() <= 2 && planta.esFuerte() && !plantaNoToleraSolParcela(planta) && !this.estaAlMaximo()
    }
}