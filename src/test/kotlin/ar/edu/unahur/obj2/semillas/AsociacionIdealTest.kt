package ar.edu.unahur.obj2.semillas

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class AsociacionIdealTest: DescribeSpec ({

    val menta = Menta(2002, 1.5) // Tolera 7 horas de sol
    val quinoa = Quinoa(2002, 0.6, 0.2) // Tolera 10 horas de sol
    val sojaComun1 = Soja(2002, 1.6) // Tolera 12 horas de sol
    val sojaComun2 = Soja(2002, 0.5) // Tolera 8 horas de sol
    val sojaComun3 = Soja(2003, 0.3) // Tolera 6 horas de sol

    val parcelaEcologicaSoleada = ParcelaEcologica(5,5,12)
    val parcelaEcologicaTranqui = ParcelaEcologica(5,5,7)
    val parcelaIndustrial = ParcelaIndustrial(10,10,8)
    val parcelaIndustrialSolInsoportable = ParcelaIndustrial(10,10,15)

    describe("Asociación ideal parcelas ecológicas"){

        parcelaEcologicaSoleada.seAsociaBienCon(sojaComun1).shouldBe(true)
        // Porque la parcela no tiene complicaciones y soja 1 tolera cant exacta de sol

        parcelaEcologicaTranqui.seAsociaBienCon(sojaComun2).shouldBe(false)
        // Porque la parcela no tiene condiciones ideales para soja 2

        parcelaEcologicaSoleada.plantar(quinoa)
        parcelaEcologicaSoleada.seAsociaBienCon(sojaComun1).shouldBe(false)
        // Porque ahora la parcela tiene complicaciones
        parcelaEcologicaSoleada.seAsociaBienCon(menta).shouldBe(false)
        // Porque la menta se quemaría, si bién la parcela tiene mucha superficie

        parcelaEcologicaSoleada.plantas.clear()
    }

    describe("Asociación ideal parcelas industriales"){
        parcelaIndustrial.seAsociaBienCon(menta).shouldBe(false) // Tolera 7 horas, no es fuerte
        parcelaIndustrial.seAsociaBienCon(sojaComun1).shouldBe(true) // Tolera 12, es fuerte

        parcelaIndustrial.plantar(menta)
        parcelaIndustrial.plantar(sojaComun2)

        parcelaIndustrial.seAsociaBienCon(sojaComun1).shouldBe(true) // Tiene solo 2 plantas, el máximo

        parcelaIndustrial.plantar(sojaComun3)

        parcelaIndustrial.seAsociaBienCon(sojaComun1).shouldBe(false) // Tiene 3 plantas, ya no se asocia bien

        parcelaIndustrialSolInsoportable.seAsociaBienCon(sojaComun1).shouldBe(false)
        // La soja es fuerte y la parcela no tiene plantas, pero nada soporta esa cantidad de sol, no se puede plantar
    }

})