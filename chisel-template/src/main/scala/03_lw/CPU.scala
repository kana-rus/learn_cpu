package `03_lw`

import chisel3._
import chisel3.util._
import chisel3.internal.chiselRuntimeDeprecated
import chisel3.stage.ChiselStage

class CPU extends Module {
    val io = IO(new Bundle {
        val exit = Output(Bool())
    })

    val core = Module(new Core())
    val memory = Module(new Memory())

    core.io.imem <> memory.io.imem
    core.io.dmem <> memory.io.dmem // +

    io.exit := core.io.exit
}

object CPU extends App {
    (new ChiselStage).emitVerilog(
        new CPU,
        Array("--target-dir", "out/")
    )
}
