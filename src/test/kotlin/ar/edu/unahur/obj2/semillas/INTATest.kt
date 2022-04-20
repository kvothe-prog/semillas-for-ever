package ar.edu.unahur.obj2.semillas

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class INTATest: DescribeSpec ({

    fun crearPlanta(anioObtencionSemilla: Int, altura: Double, tipoPlanta: Int): Planta{ //1=menta 2=soja 3=quinoa 4=sojaTransgenica 5=peperina
        return when{
            tipoPlanta == 1 -> Menta(anioObtencionSemilla, altura)
            tipoPlanta == 2 -> Soja(anioObtencionSemilla, altura)
            tipoPlanta == 3 -> Quinoa(anioObtencionSemilla, altura, 0.2)
            tipoPlanta == 4 -> SojaTransgenica(anioObtencionSemilla, altura)
            tipoPlanta == 5 -> Peperina(anioObtencionSemilla, altura)
            else -> {
                throw Exception("No se puede.")
            }
        }
    }

    val parcelaEcologica1 = ParcelaEcologica(10,10,5)
    val parcelaEcologica2 = ParcelaEcologica(8,8,9)
    val parcelaIndustrial1 = ParcelaIndustrial(10,10,9)
    val parcelaIndustrial2 = ParcelaIndustrial(5,5,8)

    describe("Promedio de plantas"){
        repeat(6){
            parcelaEcologica1.plantar(crearPlanta(2002,0.5,1))
        }
        repeat(5){
            parcelaEcologica2.plantar(crearPlanta(2002,0.5,1))
        }
        repeat(3){
            parcelaIndustrial1.plantar(crearPlanta(2002,0.5,1))
        }
        INTA.parcelas.add(parcelaEcologica1)
        INTA.parcelas.add(parcelaEcologica2)
        INTA.parcelas.add(parcelaIndustrial1)

        INTA.promedioPlantas().shouldBe(4) // 6 + 5 + 3 / 3 = 4.666

        INTA.parcelas.clear()

        INTA.promedioPlantas().shouldBe(0) // Promedio sin parcelas

        INTA.parcelas.add(parcelaIndustrial2)

        INTA.promedioPlantas().shouldBe(0) // Promedio con una parcela sin plantas

        INTA.parcelas.clear()
        parcelaEcologica1.plantas.clear()
        parcelaEcologica2.plantas.clear()
        parcelaIndustrial1.plantas.clear()

    }

    describe("Parcela mas autosustentable"){
        it("INTA sin parcelas"){
            shouldThrowAny { //Debería arrojar error porque no hay parcelas en INTA
                INTA.parcelaMasAutosustentable()
            }
        }

        it("INTA con una parcela con 4 plantas"){
            repeat(4){
                parcelaEcologica1.plantar(crearPlanta(2002,0.1,1))
            }

            INTA.parcelas.add(parcelaEcologica1)

            shouldThrowAny { //Debería arrojar error porque no hay parcelas con mas de 4 plantas
                INTA.parcelaMasAutosustentable()
            }

            INTA.parcelas.clear()
            parcelaEcologica1.plantas.clear()
        }

        it("INTA con mas de una parcela pero ninguna con mas de 4 plantas"){
            repeat(4){
                parcelaEcologica1.plantar(crearPlanta(2001,1.1,1))
            }
            repeat(3){
                parcelaEcologica2.plantar(crearPlanta(2001,1.1,1))
            }
            INTA.parcelas.add(parcelaEcologica1)
            INTA.parcelas.add(parcelaEcologica2)

            shouldThrowAny {
                INTA.parcelaMasAutosustentable()
            }

            INTA.parcelas.clear()
            parcelaEcologica1.plantas.clear()
            parcelaEcologica2.plantas.clear()
        }

        it("INTA con una parcela con mas de 4 plantas"){
            repeat(5){
                parcelaEcologica1.plantar(crearPlanta(2002,1.0,1))
            }
            INTA.parcelas.add(parcelaEcologica1)
            INTA.parcelaMasAutosustentable().shouldBe(parcelaEcologica1)

            INTA.parcelas.clear()
            parcelaEcologica1.plantas.clear()
        }

        it("INTA con mas de 1 parcela con mas de 4 plantas, parcelas ecologicas"){
            // Para parcela ecologica, las condiciones asociacion son: parcela sin complicaciones, ideal para la planta.
            // Parcela ecolgica 1 tiene 100m2 de superficie y 5 horas de sol por día
            // La menta tiene como condicion ideal que la parcela tenga mas de 6m2 de superficie
            // La menta aguanta 7 horas de sol por dia
            // La soja tiene como condicion ideal que la parcela de exactamente las horas de sol que tolera
            // Una soja de 0.5 m tolera 8 horas de sol por dia
            repeat(5){
                parcelaEcologica1.plantar(crearPlanta(2000,1.0,1)) // Plantas menta
            }
            parcelaEcologica1.plantar(crearPlanta(2000,0.5,2)) // Planta soja 0.5m

            repeat(5){
                parcelaEcologica2.plantar(crearPlanta(2000,0.5,2)) // Plantas soja 0.5m
            }
            parcelaEcologica2.plantar(crearPlanta(2000,1.0,1)) // Planta menta

            INTA.parcelas.add(parcelaEcologica1)
            INTA.parcelas.add(parcelaEcologica2)
            INTA.parcelas.add(parcelaIndustrial1) // Esta no tiene plantas

            INTA.parcelaMasAutosustentable().shouldBe(parcelaEcologica1)

            INTA.parcelas.clear()
            parcelaEcologica1.plantas.clear()
            parcelaEcologica2.plantas.clear()
        }

        it("INTA con mas de 1 parcela con mas de 4 plantas, parcelas industriales"){
            // Para parcela industrial, las condiciones asociacion son: parcela con máximo 2 plantas, planta fuerte.
            // La planta es fuerte si tolera mas de 9 horas de sol
            // La menta siempre tolera 7 horas
            // La soja: menor a 0.5 metros: 6 horas, entre 0.5 y 1 metro: 8 horas, más de 1 metro: 12 horas.
            // Parcela Industrial 1 da 9 horas de sol y parcela industrial 2 da 8

            repeat(5){
                parcelaIndustrial1.plantar(crearPlanta(2001,1.1,2)) // Plantas de soja de 1.1m
            }
            repeat(5){
                parcelaIndustrial2.plantar(crearPlanta(2001,0.5,2)) // Plantas de soja de 0.5m
            }
                parcelaIndustrial2.plantar(crearPlanta(2001,0.2,2)) // Planta de soja de 0.2m
                parcelaEcologica1.plantar(crearPlanta(2001,0.3,1)) // Planta de menta de 0.3

            INTA.parcelas.add(parcelaIndustrial1)
            INTA.parcelas.add(parcelaIndustrial2)
            INTA.parcelas.add(parcelaEcologica1)

            INTA.parcelaMasAutosustentable().shouldBe(parcelaIndustrial1)

        }

    }

})