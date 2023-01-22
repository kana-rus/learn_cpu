package `01_fetch`

import chisel3._
import chisel3.util._
import chisel3.internal.chiselRuntimeDeprecated
import chisel3.stage.ChiselStage
import fetch.Core
import fetch.Memory

class CPU extends Module {
    val io = IO(new Bundle {
        val exit = Output(Bool())
    })

    val core = Module(new Core())
    val memory = Module(new Memory())

    core.io.imem <> memory.io.imem
    io.exit := core.io.exit
}

object CPU extends App {
    (new ChiselStage).emitVerilog(
        new CPU,
        Array("--target-dir", "out/")
    )
}
