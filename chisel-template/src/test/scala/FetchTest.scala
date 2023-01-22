package fetch

import chisel3._
import org.scalatest.flatspec.AnyFlatSpec
import chiseltest._

/*
    type FlatSpec in package scalatest is deprecated (since 3.1.0):
    The org.scalatest.FlatSpec trait has been moved and renamed.
    Please use org.scalatest.flatspec.AnyFlatSpec instead.
*/
class HexTest extends /*FlatSpec*/ AnyFlatSpec with ChiselScalatestTester {
    "mycpu" should "work through hex" in {
        test(new Top) { c =>
            while (!c.io.exit.peekBoolean()) {
                c.clock.step(1)
            }
        }
    }
}