package ar.edu.unahur.obj2.semillas

object INTA {
    val parcelas = mutableListOf<Parcela>()
    fun promedioPlantas(): Int{
        val plantas =  parcelas.sumBy { parcela -> parcela.cantidadPlantas() }
        val parcelas = parcelas.size
        return if (parcelas == 0){
            0
        } else{
            plantas / parcelas
        }
    }

    fun parcelaMasAutosustentable(): Parcela{
        val parcelasCandidatas = parcelas.filter { parcela -> parcela.cantidadPlantas() > 4 }
        var laParcela: Parcela

        if (parcelasCandidatas.size == 0){
            throw Exception("No hay parcelas con mas de 4 plantas.")
        }
        else {
            laParcela = parcelasCandidatas.maxByOrNull { parcela -> parcela.plantasBienAsociadas().size / parcela.plantas.size }!!
        }
        return laParcela
    }
}