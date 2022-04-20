package ar.edu.unahur.obj2.semillas

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ParcelasIdealesTest: DescribeSpec({
    val menta = Menta(2002, 1.5)
    val peperina = Peperina(2002, 0.4)
    val quinoa = Quinoa(2002, 0.6, 3.0)
    val sojaComun1 = Soja(2002, 1.6) // Tolera 12 horas de sol
    val sojaComun2 = Soja(2002, 0.5) // Tolera 8 horas de sol
    val sojaTransgenica = SojaTransgenica(2002, 0.5)

    val parcelaIndustrialExtensa = ParcelaIndustrial(5,5, 6)
    val parcelaIndustrialChiquita = ParcelaIndustrial(2,2,6)
    val parcelaIndustrialIntermedia = ParcelaIndustrial(2,3,6)
    val parcelaIndustrialIdealSoja1 = ParcelaIndustrial(5,5,12)
    val parcelaIndustrialIdealSojaTransgenica = ParcelaIndustrial(3,2,6)

    describe("Menta parcela ideal y no ideal"){
        menta.parcelaIdeal(parcelaIndustrialChiquita).shouldBe(false)
        menta.parcelaIdeal(parcelaIndustrialExtensa).shouldBe(true)
        menta.parcelaIdeal(parcelaIndustrialIntermedia).shouldBe(false)

        peperina.parcelaIdeal(parcelaIndustrialChiquita).shouldBe(false)
        peperina.parcelaIdeal(parcelaIndustrialExtensa).shouldBe(true)
        peperina.parcelaIdeal(parcelaIndustrialIntermedia).shouldBe(false)
    }

    describe("Quinoa parcela ideal y no ideal"){
        parcelaIndustrialIntermedia.plantar(menta) // Menta mide 1.5
        parcelaIndustrialIntermedia.plantar(peperina) // Peperina mide 0.4

        parcelaIndustrialExtensa.plantar(sojaComun1) // Soja 1 mide 1.6
        parcelaIndustrialExtensa.plantar(sojaComun2) // Soja 2 mide 0.5

        quinoa.parcelaIdeal(parcelaIndustrialIntermedia).shouldBe(true)
        quinoa.parcelaIdeal(parcelaIndustrialExtensa).shouldBe(false)

        parcelaIndustrialIntermedia.plantas.clear()
        parcelaIndustrialExtensa.plantas.clear()
    }

    describe("Soja común ideal y no ideal"){
        sojaComun1.parcelaIdeal(parcelaIndustrialIdealSoja1).shouldBe(true) // Tolera cantidad exacta horas parcela
        sojaComun2.parcelaIdeal(parcelaIndustrialIdealSoja1).shouldBe(false)
    }

    describe("Soja transgénica ideal y no ideal"){
        sojaTransgenica.parcelaIdeal(parcelaIndustrialIdealSojaTransgenica).shouldBe(true) // Cantidad max plantas parcela = 1
        sojaTransgenica.parcelaIdeal(parcelaIndustrialExtensa).shouldBe(false) // Cantidad max plantas parcela =/ 1
    }

})