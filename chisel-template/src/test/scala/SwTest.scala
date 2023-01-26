package `04_sw`

import chisel3._
import org.scalatest.flatspec.AnyFlatSpec
import chiseltest._

/*
    type FlatSpec in package scalatest is deprecated (since 3.1.0):
    The org.scalatest.FlatSpec trait has been moved and renamed.
    Please use org.scalatest.flatspec.AnyFlatSpec instead.
*/
class HexTest extends /*FlatSpec*/ AnyFlatSpec with ChiselScalatestTester {
    "mycpu" should "perform `sw rs2(= 6), memory[offset(= 0) + imm_s(= 16)]`" in {
        test(new CPU) { c =>
            while (!c.io.exit.peekBoolean()) {
                c.clock.step(1)
            }
        }
    }
}
