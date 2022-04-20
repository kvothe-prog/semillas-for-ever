package ar.edu.unahur.obj2.semillas

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

class ParcelasTest : DescribeSpec ({

    describe("Parcela"){
        val parcelaEcologica1 = ParcelaEcologica(5,10,6)
        val parcelaEcologica2 = ParcelaEcologica(10,9,10)
        val parcelaIndustrial1 = ParcelaIndustrial(5,10,8)
        val parcelaIndustrial2 = ParcelaIndustrial(10,9,10)
        val parcelaIndustrialChiquita = ParcelaIndustrial(3,2,6)
        val parcelaIndustrialInviable = ParcelaIndustrial(2,2,6)

        val menta = Menta(2000,0.5) // Tolera 7 horas de sol
        val soja = Soja(2000,1.1) // Tolera 12 horas de sol

        it("Superficie"){
            parcelaEcologica1.superficie().shouldBe(50)
            parcelaEcologica2.superficie().shouldBe(90)
            parcelaIndustrial1.superficie().shouldBe(50)
            parcelaIndustrial2.superficie().shouldBe(90)
            parcelaIndustrialChiquita.superficie().shouldBe(6)
            parcelaIndustrialInviable.superficie().shouldBe(4)
        }

        it("Cantidad máxima plantas"){
            parcelaEcologica1.cantidadMaximaPlantas().shouldBe(26)
            parcelaEcologica2.cantidadMaximaPlantas().shouldBe(18)
            parcelaIndustrial1.cantidadMaximaPlantas().shouldBe(26)
            parcelaIndustrial2.cantidadMaximaPlantas().shouldBe(18)
            parcelaIndustrialChiquita.cantidadMaximaPlantas().shouldBe(1)
            parcelaIndustrialInviable.cantidadMaximaPlantas().shouldBe(0)
        }

        it("Plantar planta que puede ser plantada"){
            shouldThrowAny { parcelaEcologica2.plantar(menta) } // Debería levantar excepción por horas de sol.
            shouldThrowAny { parcelaIndustrial2.plantar(menta) }
            parcelaIndustrialChiquita.plantar(soja)
            shouldThrowAny { parcelaIndustrialChiquita.plantar(menta) } // Debería levantar excepción porque esta llena la parcela.
            shouldThrowAny { parcelaIndustrialInviable.plantar(menta) }

            parcelaIndustrialChiquita.plantas.clear()
        }

        it("Plantar planta en condiciones"){
            parcelaEcologica1.plantar(soja)
            parcelaEcologica1.plantar(menta)

            parcelaEcologica2.plantar(soja)
            shouldThrowAny { parcelaEcologica2.plantar(menta) } // No tolera tantas horas de sol

            parcelaEcologica1.plantas.shouldContainExactly(soja, menta)
            parcelaEcologica2.plantas.shouldContainExactly(soja)

            parcelaEcologica1.plantas.clear()
            parcelaEcologica2.plantas.clear()
        }

        it("Parcela con complicaciones"){
            parcelaIndustrial1.plantar(menta)
            parcelaIndustrial1.parcelaTieneComplicaciones().shouldBe(true)

            parcelaIndustrial1.plantas.clear()
        }
    }
})