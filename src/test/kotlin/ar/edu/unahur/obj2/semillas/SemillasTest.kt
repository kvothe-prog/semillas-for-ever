package ar.edu.unahur.obj2.semillas

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class SemillasTest : DescribeSpec ({
    describe("Creación de las plantas") {
        val menta = Menta(2021, 1.0)
        val mentita = Menta(2021, 0.3)
        val soja = Soja(2009, 0.6)

        it("probamos los atributos altura  y anioSemilla") {
            menta.altura.shouldBe(1.0)
            menta.anioObtencionSemilla.shouldBe(2021)
        }

        it("verificar si da semillas") {
            menta.daSemillas().shouldBeTrue()
            mentita.daSemillas().shouldBeFalse()
            soja.daSemillas().shouldBeFalse()
        }

        it("es fuerte") {
            menta.esFuerte().shouldBeFalse()
            soja.esFuerte().shouldBeFalse()
        }

        it("espacio") {
            menta.espacio().shouldBe(2.0)
            mentita.espacio().shouldBe(1.3)
            soja.espacio().shouldBe(0.3)
        }

        it("verifico la suma de varias") {
            val superficie = mutableListOf(
                soja.espacio(),
                menta.espacio(),
                mentita.espacio()
            ).sum()
            Math.ceil(superficie).shouldBe(4)
        }
    }

    describe("Semilla de menta"){
        val menta1 = Menta(2022, 0.1)
        val menta2 = Menta(2022, 0.4)
        val menta3 = Menta(2022, 0.7)

        it("Espacio que ocupa"){
            menta1.espacio().shouldBe(1.1)
            menta2.espacio().shouldBe(1.4)
            menta3.espacio().shouldBe(1.7)
        }
        it("Da semillas o no"){
            menta1.daSemillas().shouldBe(false)
            menta2.daSemillas().shouldBe(false)
            menta3.daSemillas().shouldBe(true)
        }
    }
    describe("Semilla de soja") {
        val soja1 = Soja(2009, 0.6)
        val soja2 = Soja(2008, 0.74)
        val soja3 = Soja(2008, 0.75)
        val soja4 = Soja(2007, 1.1)
        val soja5 = Soja(2010, 0.5)
        val soja6 = Soja(2010, 0.4)
        val soja7 = Soja(2007, 0.75)

        it("Espacio que ocupa") {
            soja1.espacio().shouldBe(0.3)
            soja2.espacio().shouldBe(0.37)
            soja3.espacio().shouldBe(0.375)
            soja4.espacio().shouldBe(0.55)
            soja5.espacio().shouldBe(0.25)
            soja6.espacio().shouldBe(0.2)
            soja7.espacio().shouldBe(0.375)
        }
        it("Tolerancia al sol"){
            soja1.horasDeSolQueTolera().shouldBe(8)
            soja2.horasDeSolQueTolera().shouldBe(8)
            soja3.horasDeSolQueTolera().shouldBe(8)
            soja4.horasDeSolQueTolera().shouldBe(12)
            soja5.horasDeSolQueTolera().shouldBe(8)
            soja6.horasDeSolQueTolera().shouldBe(6)
            soja7.horasDeSolQueTolera().shouldBe(8)
        }
        it("Da semillas o no"){
            soja1.daSemillas().shouldBe(false)
            soja2.daSemillas().shouldBe(false)
            soja3.daSemillas().shouldBe(true)
            soja4.daSemillas().shouldBe(true)
            soja5.daSemillas().shouldBe(false)
            soja6.daSemillas().shouldBe(false)
            soja7.daSemillas().shouldBe(false)
        }
    }

    describe("Quinoa"){
        val quinoa1 = Quinoa(2010, 0.5, 0.2)
        val quinoa2 = Quinoa(2006, 0.5, 0.9)
        val quinoa3 = Quinoa(2000, 0.5, 0.3)

        it("Tolerancia al sol"){
            quinoa1.horasDeSolQueTolera().shouldBe(10)
            quinoa2.horasDeSolQueTolera().shouldBe(7)
            quinoa3.horasDeSolQueTolera().shouldBe(7)
        }
        it("Da semillas o no"){
            quinoa1.daSemillas().shouldBe(true)
            quinoa2.daSemillas().shouldBe(true)
            quinoa3.daSemillas().shouldBe(false)
        }
    }

    describe("Variedades"){
        val sojaTransgenica1 = SojaTransgenica(2022, 2.0)
        val sojaTransgenica2 = SojaTransgenica(2001, 0.5)
        val peperina1 = Peperina(2022, 0.4)
        val peperina2 = Peperina(2022, 0.7)

        it("Da semillas la soja transgénica?"){
            sojaTransgenica1.daSemillas().shouldBe(false)
            sojaTransgenica2.daSemillas().shouldBe(false)
        }

        it("Espacio que ocupa la peperina"){
            peperina1.espacio().shouldBe(2.8)
            peperina2.espacio().shouldBe(3.4)
        }

    }
})