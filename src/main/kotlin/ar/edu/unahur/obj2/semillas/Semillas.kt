package ar.edu.unahur.obj2.semillas

abstract class Planta(val anioObtencionSemilla: Int, var altura: Double) {
    fun esFuerte() = this.horasDeSolQueTolera() > 9
    abstract fun espacio(): Double
    open fun horasDeSolQueTolera() = 7
    open fun daSemillas(): Boolean {
        return this.esFuerte()
    }
    abstract fun parcelaIdeal(parcela: Parcela): Boolean

}

open class Menta(anioObtencionSemilla: Int, altura: Double) : Planta(anioObtencionSemilla, altura) {
    override fun daSemillas(): Boolean{
        return (altura > 0.4) || super.daSemillas()
    }
    override fun espacio(): Double{
        return altura + 1
    }

    override fun parcelaIdeal(parcela: Parcela): Boolean {
        return parcela.superficie() > 6
    }
}

open class Soja(anioObtencionSemilla: Int, altura: Double) : Planta(anioObtencionSemilla, altura) {

    override fun horasDeSolQueTolera(): Int  {
        return  when {
            altura < 0.5  -> 6
            altura < 1    -> 8
            else          -> 12
        }
    }
    override fun daSemillas(): Boolean  {
        return super.daSemillas() || (this.anioObtencionSemilla > 2007 && ( this.altura in 0.75..0.9 ) )
    }
    override fun espacio(): Double {
        return this.altura / 2
    }

    override fun parcelaIdeal(parcela: Parcela): Boolean {
        return parcela.horasSolPorDia == this.horasDeSolQueTolera()
    }
}

class Quinoa(anioObtencionSemilla: Int, altura: Double, val espacio: Double): Planta(anioObtencionSemilla, altura){
    override fun espacio(): Double {
        return this.espacio
    }

    override fun horasDeSolQueTolera(): Int {
        return when {
            espacio < 0.3 -> 10
            else         -> super.horasDeSolQueTolera()
        }
    }

    override fun daSemillas(): Boolean {
        return super.daSemillas() || (this.anioObtencionSemilla in 2001..2008)
    }

    override fun parcelaIdeal(parcela: Parcela): Boolean {
        return parcela.plantas.all { planta -> planta.altura <= 1.5 }
    }
}

class SojaTransgenica(anioObtencionSemilla: Int, altura: Double): Soja(anioObtencionSemilla, altura){
    override fun daSemillas(): Boolean {
        return false
    }

    override fun parcelaIdeal(parcela: Parcela): Boolean {
        return parcela.cantidadMaximaPlantas() == 1
    }
}

class Peperina(anioObtencionSemilla: Int, altura: Double): Menta(anioObtencionSemilla, altura){
    override fun espacio(): Double {
        return super.espacio() * 2
    }
}
